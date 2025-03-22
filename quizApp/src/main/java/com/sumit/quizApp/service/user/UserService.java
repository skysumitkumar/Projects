package com.sumit.quizApp.service.user;

import com.sumit.quizApp.model.user.User;
import com.sumit.quizApp.repository.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
/*
*
 */
@Service
public class UserService
{
    @Autowired
    UserDao userDao;

    public ResponseEntity<String> save(User user)
    {
        try
        {
            userDao.save(user);
            return new ResponseEntity<>("User Registered successfully",HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>("User Register Failed please try again", HttpStatus.OK);
        }
    }
}
