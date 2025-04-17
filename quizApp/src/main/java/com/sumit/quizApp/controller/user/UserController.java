package com.sumit.quizApp.controller.user;

import com.sumit.quizApp.model.quiz.QuestionWrapper;
import com.sumit.quizApp.model.quiz.Response;
import com.sumit.quizApp.model.user.User;
import com.sumit.quizApp.service.quiz.QuizService;
import com.sumit.quizApp.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* User API
*  /v1/user
*           Signup           input  <User>
*                            output <ResponseEntity<String>>
*           quiz/create      input  <String,int,String>       http://localhost:8080/quiz/create?category=Java&numQ=5&title=JQuiz
*                            output <ResponseEntity<String>>
*           quiz/get/{id}    input  <int>                     get quiz for particular id
*                            output <ResponseEntity<List<QuestionWrapper>>>
*           quiz/submit/{id} input  <int,List<Response>>
*                            output <ResponseEntity<Integer>>
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger= LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    public QuizService quizService;

    @PostMapping("signup")
    public ResponseEntity<String> signup(@RequestBody User user)
    {
        logger.info("user signup controller is called");
        return userService.save(user);
    }

    @PostMapping("quiz/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int numQ,@RequestParam String title)
    {
        logger.info("user quiz create controller is called");
        return quizService.createQuiz(category,numQ,title);
    }

    @GetMapping("quiz/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id)
    {
        logger.info("user get question by id controller is called");
        return quizService.getQuizQuestion(id);
    }

    @PostMapping("quiz/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id,@RequestBody List<Response> responses)
    {
        logger.info("user submit quiz controller is called");
        return quizService.calculateResult(id,responses);
    }
}
