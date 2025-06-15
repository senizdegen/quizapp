package com.senizdegen.quizapp.service;

import com.senizdegen.quizapp.dao.QuestionDao;
import com.senizdegen.quizapp.dao.QuizDao;
import com.senizdegen.quizapp.domain.dto.QuestionWrapper;
import com.senizdegen.quizapp.domain.dto.QuizCreationRequest;
import com.senizdegen.quizapp.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    private UserService userService;


    public ResponseEntity<Quiz> createQuiz(QuizCreationRequest request) {

        // Преобразуем DTO в сущности Question
        List<Question> questions = request.getQuestions().stream().map(q -> {
            Question question = new Question();
            question.setQuestionTitle(q.getQuestionTitle());
            question.setOption1(q.getOption1());
            question.setOption2(q.getOption2());
            question.setOption3(q.getOption3());
            question.setOption4(q.getOption4());
            question.setRightAnswer(q.getRightAnswer());
            question.setDifficultyLevel(q.getDifficultyLevel());
            question.setCategory(q.getCategory());

            // Если есть ID, значит вопрос уже существует, просто получаем из базы
            if (q.getId() != null) {
                Optional<Question> existing = questionDao.findById(q.getId());
                return existing.orElse(question); // Если найден — вернем, иначе создадим новый
            }

            // Новый вопрос — сохраняем
            return questionDao.save(question);
        }).toList();

        User currentUser = userService.getCurrentUser();

        // Создаем квиз
        Quiz quiz = new Quiz();
        quiz.setTitle(request.getTitle());
        quiz.setQuestions(questions);// используем сохраненные или найденные вопросы
        quiz.setUser(currentUser);



        return new ResponseEntity<>(quizDao.save(quiz), HttpStatus.OK);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();

        List<QuestionWrapper> questionForUser = new ArrayList<>();
        for (Question q : questionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(
                    q.getId(),
                    q.getQuestionTitle(),
                    q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionForUser.add(qw);
        }
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();

        int count = 0;
        int i = 0;
        for (Response response : responses) {
            if(response.getResponse().equals(questions.get(i).getRightAnswer())) {
                count++;
            }
            i++;
        }
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteQuiz(Integer id) {
        try {
            if (quizDao.existsById(id)) {
                quizDao.deleteById(id);
                return new ResponseEntity<>("Quiz deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error while deleting quiz", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateQuiz(Integer id, QuizCreationRequest request) {
        Optional<Quiz> optionalQuiz = quizDao.findById(id);
        if (optionalQuiz.isEmpty()) {
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
        }

        Quiz quiz = optionalQuiz.get();
        quiz.setTitle(request.getTitle());

        List<Question> updatedQuestions = request.getQuestions().stream()
                .map(q -> {
                    Question question = new Question();
                    question.setQuestionTitle(q.getQuestionTitle());
                    question.setOption1(q.getOption1());
                    question.setOption2(q.getOption2());
                    question.setOption3(q.getOption3());
                    question.setOption4(q.getOption4());
                    question.setRightAnswer(q.getRightAnswer());
                    question.setDifficultyLevel(q.getDifficultyLevel());
                    question.setCategory(q.getCategory());

                    if (q.getId() != null) {
                        return questionDao.findById(q.getId()).orElse(question);
                    }
                    return questionDao.save(question);
                })
                .collect(Collectors.toCollection(ArrayList::new)); // ✅ Mutable list

        quiz.setQuestions(updatedQuestions);
        return new ResponseEntity<>("Quiz updated successfully", HttpStatus.OK);
    }

    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        try {
            List<Quiz> quizzes = quizDao.findAll();
            return new ResponseEntity<>(quizzes, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean isOwnerByQuizId(Integer quizId, String username) {
        Quiz quiz = quizDao.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        return quiz.getUser().getUsername().equals(username);
    }


}
