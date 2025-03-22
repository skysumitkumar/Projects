package com.sumit.quizApp.controller.admin;

import com.sumit.quizApp.model.admin.Admin;
import com.sumit.quizApp.model.question.Question;
import com.sumit.quizApp.service.admin.AdminService;
import com.sumit.quizApp.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* Admin API
*           signup                          input<Admin>
*           question/addQuestions           input<Question>
*           question/allQuestions
*           question/category/{category}    input<String>
 */
@RestController
@RequestMapping("/admin")
public class AdminController
{

    @Autowired
    AdminService adminService;

    @Autowired
    QuestionService questionService;

    @PostMapping("signup")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin)
    {
        return adminService.addAdmin(admin);
    }

    @PostMapping("question/addQuestions")
    public ResponseEntity<String> addQuestion(@RequestBody Question question)
    {
        return questionService.addQuestion(question);
    }

    @GetMapping("question/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions()
    {
        return questionService.getAllQuestions();
    }

    @GetMapping("question/category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category)
    {
        return questionService.getQuestionByCategory(category);
    }

}
