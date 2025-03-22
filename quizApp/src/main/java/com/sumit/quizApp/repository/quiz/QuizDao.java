package com.sumit.quizApp.repository.quiz;

import com.sumit.quizApp.model.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizDao extends JpaRepository<Quiz,Integer> {
}
