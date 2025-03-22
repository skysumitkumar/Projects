package com.sumit.quizApp.service.quiz;

import com.sumit.quizApp.model.question.Question;
import com.sumit.quizApp.model.quiz.QuestionWrapper;
import com.sumit.quizApp.model.quiz.Quiz;
import com.sumit.quizApp.model.quiz.Response;
import com.sumit.quizApp.repository.question.QuestionDao;
import com.sumit.quizApp.repository.quiz.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
* createQuiz        input<String,int,String>
* getQuizQuestion   input<int>
* calculateResult   input<int,List<Response>
 */
@Service
public class QuizService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    QuizDao quizDao;

    public ResponseEntity<String> createQuiz(String category,int numQ,String title)
    {
        List<Question> questions=questionDao.findRandomQuestionsByCategory(category,numQ);
        if(questions.size()!=numQ)
        {
            return new ResponseEntity<>(numQ + " " + category +" questions are not present please contact to admin",HttpStatus.BAD_REQUEST);
        }
        if(questions.isEmpty())
        {
            return new ResponseEntity<>("No questions found for category: " + category,HttpStatus.BAD_REQUEST);
        }
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(int id)
    {
        Optional<Quiz> quiz=quizDao.findById(id);
        if(!(quiz.isPresent()))
        {
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
        }
        List<Question>questionsFromDb=quiz.get().getQuestions();
        List<QuestionWrapper>questionsForUser=new ArrayList<>();
        for(Question q:questionsFromDb)
        {
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(int id,List<Response> responses)
    {
        Quiz quiz=quizDao.findById(id).get();
        if(quiz==null)
        {
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
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
