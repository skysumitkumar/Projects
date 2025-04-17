package com.sumit.quizApp.service.user;

import com.sumit.quizApp.constants.ResponseMessages;
import com.sumit.quizApp.model.user.User;
import com.sumit.quizApp.repository.user.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger= LogManager.getLogger(UserService.class);
    @Autowired
    UserDao userDao;

    public ResponseEntity<String> save(User user)
    {
        logger.info("save user service is called");
        try
        {
            userDao.save(user);
            logger.debug("user saved successfully");
            return new ResponseEntity<>(ResponseMessages.userRegSuccess,HttpStatus.OK);
        }
        catch(Exception e)
        {
            logger.error("save user service failed");
            return new ResponseEntity<>(ResponseMessages.userRegFailed, HttpStatus.OK);
        }
    }
}
