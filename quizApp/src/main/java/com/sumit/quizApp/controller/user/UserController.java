package com.sumit.quizApp.controller.user;

import com.sumit.quizApp.model.question.QuestionWrapper;
import com.sumit.quizApp.model.quiz.Response;
import com.sumit.quizApp.model.user.User;
import com.sumit.quizApp.service.quiz.QuizService;
import com.sumit.quizApp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    public QuizService quizService;

    @GetMapping("/dashbord")
    public String greet()
    {
        return "Hello user";
    }
    @PostMapping("signup")
    public ResponseEntity<String> signup(User user)
    {
        return userService.save(user);
    }

    //http://localhost:8080/quiz/create?category=Java&numQ=5&title=JQuiz
    @PostMapping("quiz/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title) {
        return quizService.createQuiz(category,numQ,title);
    }

    //previously we set the id when we call the create API
    //http://localhost:8080/quiz/get/2
    @GetMapping("quiz/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id) {
        return quizService.getQuizQuestion(id);
    }

    @PostMapping("quiz/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id,@RequestBody List<Response> responses)
    {
        return quizService.calculateResult(id,responses);
    }
}
