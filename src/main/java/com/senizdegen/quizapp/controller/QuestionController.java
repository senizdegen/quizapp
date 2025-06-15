package com.senizdegen.quizapp.controller;

import com.senizdegen.quizapp.domain.model.Question;
import com.senizdegen.quizapp.service.QuestionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @RequestMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @GetMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer id) {
        return questionService.getQuestionById(id);
    }

    @PutMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    //@PreAuthorize("@quizService.isOwnerByQuestionId(#id, authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question, @PathVariable Integer id) {
        return questionService.updateQuestion(question, id);
    }

    @DeleteMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    //@PreAuthorize("@quizService.isOwnerByQuestionId(#id, authentication.name) or hasRole('ADMIN')")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }

}
