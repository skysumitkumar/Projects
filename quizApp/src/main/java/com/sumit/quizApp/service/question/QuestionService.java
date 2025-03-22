package com.sumit.quizApp.service.question;

import com.sumit.quizApp.model.question.Question;
import com.sumit.quizApp.repository.question.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
/*
* addQuestions          input <Question>
* getAllQuestions
* getQuestionByCategory input<String>
 */
@Service
public class QuestionService
{
    @Autowired
    public QuestionDao repo;

    public ResponseEntity<List<Question>> getAllQuestions()
    {
        List<Question> questions=repo.findAll();
        if(questions.isEmpty())
        {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category)
    {
        List<Question> questions=repo.findByCategory(category);
        if(questions.isEmpty())
        {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question)
    {
        try
        {
            repo.save(question);
            return new ResponseEntity<>("Question added successfully",HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>("Question not added please try again",HttpStatus.CONFLICT);
        }
    }

}
