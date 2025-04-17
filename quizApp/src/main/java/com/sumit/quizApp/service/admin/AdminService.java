package com.sumit.quizApp.service.admin;

import com.sumit.quizApp.constants.ResponseMessages;
import com.sumit.quizApp.model.admin.Admin;
import com.sumit.quizApp.repository.admin.AdminDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/*
* admin service
*               addAdmin input <admin>
*                        output <ResponseEntity<String>>
 */
@Service
public class AdminService {
    private static final Logger logger= LogManager.getLogger(AdminService.class);

    @Autowired
    AdminDao adminDao;

    public ResponseEntity<String> addAdmin(Admin admin)
    {
        logger.info("addAdmin service is called");
        try
        {
            adminDao.save(admin);
            logger.debug("admin save successfully");
            return new ResponseEntity<>(ResponseMessages.adminRegSuccess,HttpStatus.OK);
        }
        catch(Exception e)
        {
            logger.error("admin failed to save");
            return new ResponseEntity<>(ResponseMessages.adminRegFailed,HttpStatus.BAD_REQUEST);
        }
    }
}
