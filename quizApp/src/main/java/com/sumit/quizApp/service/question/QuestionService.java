package com.sumit.quizApp.service.question;

import com.sumit.quizApp.constants.ResponseMessages;
import com.sumit.quizApp.model.question.Question;
import com.sumit.quizApp.repository.question.QuestionDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


/*
* addQuestions          input   <Question>
*                       output   <ResponseEntity<String>>
* getAllQuestions       input    <NULL>
*                       output   <ResponseEntity<List<Question>>>
* getQuestionByCategory input    <String>
*                       output   <ResponseEntity<List<Question>>>
 */
@Service
public class QuestionService
{
private static final Logger logger= LogManager.getLogger(QuestionService.class);
    @Autowired
    public QuestionDao repo;

    public ResponseEntity<List<Question>> getAllQuestions()
    {
        logger.info("getAllQuestion service is called");
        List<Question> questions=repo.findAll();
        if(questions.isEmpty())
        {
            logger.error("database is empty");
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NO_CONTENT);
        }
        logger.debug("question fetch from database successfully");
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category)
    {
        logger.info("getQuestionByCategory is called");
        List<Question> questions=repo.findByCategory(category);
        if(questions.isEmpty())
        {
            logger.error("question with {} category is not found",category);
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
        }
        logger.debug("question with category fetch from database successfully");
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(Question question)
    {
        logger.info("addQuestion service is called");
        try
        {
            repo.save(question);
            logger.debug("Question added successfully");
            return new ResponseEntity<>(ResponseMessages.questionAddSuccess,HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            logger.error("failed to add question");
            return new ResponseEntity<>(ResponseMessages.questionAddFailed,HttpStatus.CONFLICT);
        }
    }

}
