package com.sumit.quizApp.model.quiz;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWrapper
{
    private Integer id ;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}
