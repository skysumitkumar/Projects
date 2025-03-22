package com.sumit.quizApp.controller.user;

import com.sumit.quizApp.model.quiz.QuestionWrapper;
import com.sumit.quizApp.model.quiz.Response;
import com.sumit.quizApp.model.user.User;
import com.sumit.quizApp.service.quiz.QuizService;
import com.sumit.quizApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* User API
*           Signup           input<User>
*           quiz/create      input<String,int,String>       http://localhost:8080/quiz/create?category=Java&numQ=5&title=JQuiz
*           quiz/get/{id}    input<int>                     get quiz for particular id
*           quiz/submit/{id} input<int,List<Response>>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    public QuizService quizService;

    @PostMapping("signup")
    public ResponseEntity<String> signup(@RequestBody User user)
    {

        return userService.save(user);
    }

    @PostMapping("quiz/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int numQ,@RequestParam String title)
    {
        return quizService.createQuiz(category,numQ,title);
    }

    @GetMapping("quiz/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id)
    {
        return quizService.getQuizQuestion(id);
    }

    @PostMapping("quiz/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id,@RequestBody List<Response> responses)
    {
        return quizService.calculateResult(id,responses);
    }
}
