//package com.sumit.quizApp.controller.question;
//
//import com.sumit.quizApp.model.question.Question;
//import com.sumit.quizApp.service.question.QuestionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("question")
//public class QuestionController {
//
//    @Autowired
//    public QuestionService questionService;
//
//    @GetMapping("allQuestions")
//    public ResponseEntity<List<Question>> getAllQuestions()
//    {
//        return questionService.getAllQuestions();
//    }
//
//    @GetMapping("category/{category}")
//    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category)
//    {
//        return questionService.getQuestionByCategory(category);
//    }
//
//}
