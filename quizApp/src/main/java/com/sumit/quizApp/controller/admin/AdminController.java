package com.sumit.quizApp.controller.admin;

import com.sumit.quizApp.model.admin.Admin;
import com.sumit.quizApp.model.question.Question;
import com.sumit.quizApp.service.admin.AdminService;
import com.sumit.quizApp.service.admin.MyAdminDetailsService;
import com.sumit.quizApp.service.question.QuestionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* Admin API
*           signup                          input <Admin>
*                                           output <ResponseEntity<String>>
*           question/addQuestions           input<Question>
*                                           output <ResponseEntity<String>>
*           question/allQuestions           input<NULL>
*                                           output <ResponseEntity<List<Question>>>
*           question/category/{category}    input<String>
*                                           output <ResponseEntity<List<Question>>>
 */
@RestController
@RequestMapping("/admin")
public class AdminController
{
    private static final Logger logger= LogManager.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @Autowired
    QuestionService questionService;

    @PostMapping("signup")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin)
    {
        logger.info("admin signup controller is called");
        return adminService.addAdmin(admin);
    }


    @PostMapping("question/addQuestions")
    public ResponseEntity<String> addQuestion(@RequestBody Question question)
    {
        logger.info("admin addQuestion controller is called");
        return questionService.addQuestion(question);
    }

    @GetMapping("question/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions()
    {
        logger.info("admin allQuestions controller is called");
        return questionService.getAllQuestions();
    }

    @GetMapping("question/category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category)
    {
        logger.info("admin get questions by category controller is called");
        return questionService.getQuestionByCategory(category);
    }

}
