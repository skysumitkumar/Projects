package com.sumit.quizApp.constants;

import org.springframework.stereotype.Component;

@Component
public class ResponseMessages
{
    public static final String Success="Success";
    public static final String adminRegSuccess="Admin Registration Success";
    public static final String adminRegFailed="Fail to register admin please try again";
    public static final String questionAddSuccess="Question added successfully";
    public static final String questionAddFailed="Question not added please try again";
    public static final String userRegSuccess="User Registered successfully";
    public static final String userRegFailed="User Register Failed please try again";
    public ResponseMessages()
    {

    }
}
