package com.senizdegen.quizapp.controller;

import com.senizdegen.quizapp.domain.dto.QuestionWrapper;
import com.senizdegen.quizapp.domain.model.Quiz;
import com.senizdegen.quizapp.domain.dto.QuizCreationRequest;
import com.senizdegen.quizapp.domain.model.Response;
import com.senizdegen.quizapp.service.QuizService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

//    @PostMapping
//    public ResponseEntity<String> createQuiz(
//            @RequestParam String category,
//            @RequestParam int numberOfQuestions,
//            @RequestParam String title
//            ) {
//        return quizService.createQuiz(category, numberOfQuestions, title);
//    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Quiz> createQuiz(@RequestBody QuizCreationRequest request) {
        return quizService.createQuiz(request);
    }

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable Integer id) {
        return quizService.getQuiz(id);
    }

    @PostMapping("submit/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }

    @DeleteMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("@quizService.isOwnerByQuizId(#id, authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<String> deleteQuiz(@PathVariable Integer id) {
        return quizService.deleteQuiz(id);
    }

    @PutMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("@quizService.isOwnerByQuizId(#id, authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<String> updateQuiz(@PathVariable Integer id, @RequestBody QuizCreationRequest request) {
        return quizService.updateQuiz(id, request);
    }

}
