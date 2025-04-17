package com.sumit.quizApp.service.quiz;

import com.sumit.quizApp.constants.ResponseMessages;
import com.sumit.quizApp.model.question.Question;
import com.sumit.quizApp.model.quiz.QuestionWrapper;
import com.sumit.quizApp.model.quiz.Quiz;
import com.sumit.quizApp.model.quiz.Response;
import com.sumit.quizApp.repository.question.QuestionDao;
import com.sumit.quizApp.repository.quiz.QuizDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
* createQuiz        input   <String,int,String>
*                   output  <ResponseEntity<String>>
* getQuizQuestion   input   <int>
*                   output  <ResponseEntity<List<QuestionWrapper>>
* calculateResult   input   <int,List<Response>
*                   output  <ResponseEntity<Integer>>
 */
@Service
public class QuizService {
    private static final Logger logger= LogManager.getLogger(QuizService.class);

    @Autowired
    QuestionDao questionDao;

    @Autowired
    QuizDao quizDao;

    public ResponseEntity<String> createQuiz(String category,int numQ,String title)
    {
        logger.info("createQuiz service is called");
        List<Question> questions=questionDao.findRandomQuestionsByCategory(category,numQ);
        if(questions.size()!=numQ)
        {
            logger.debug("{} questions with {} category are not found in databasse",numQ,category);
            return new ResponseEntity<>(numQ + " " + category +" questions are not present please contact to admin",HttpStatus.BAD_REQUEST);
        }
        if(questions.isEmpty())
        {
            logger.debug("question not found with this {} category",category);
            return new ResponseEntity<>("No questions found for category: " + category,HttpStatus.BAD_REQUEST);
        }
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        logger.debug("Quiz with {} category is created",category);
        return new ResponseEntity<>(ResponseMessages.Success, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(int id)
    {
        logger.info("getQuizQuestion service is called");
        Optional<Quiz> quiz=quizDao.findById(id);
        if(!(quiz.isPresent()))
        {
            logger.debug("quiz not found with this {} id",id);
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
        }
        List<Question>questionsFromDb=quiz.get().getQuestions();
        List<QuestionWrapper>questionsForUser=new ArrayList<>();
        for(Question q:questionsFromDb)
        {
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsForUser.add(qw);
        }
        logger.debug("quiz with {} got successfully",id);
        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(int id,List<Response> responses)
    {
        logger.info("calculateResult service is called");
        Quiz quiz=quizDao.findById(id).get();
        if(quiz==null)
        {
            logger.debug("quiz not found with this {} id",id);
            return new ResponseEntity<>(-1,HttpStatus.NOT_FOUND);
        }
        List<Question>questions=quiz.getQuestions();
        int right=0;
        int i=0;
        for(Response response:responses)
        {
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
            {
                right++;
            }
            i++;
        }
        logger.debug("result calculated successfully");
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
