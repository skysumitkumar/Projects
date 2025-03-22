package com.sumit.quizApp.service.admin;

import com.sumit.quizApp.model.admin.Admin;
import com.sumit.quizApp.repository.admin.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/*
* admin service
*               addAdmin input <admin>
 */
@Service
public class AdminService {

    @Autowired
    AdminDao adminDao;

    public ResponseEntity<String> addAdmin(Admin admin)
    {
        try
        {
            adminDao.save(admin);
            return new ResponseEntity<>("Admin Registration Success",HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>("Fail to register admin please try again",HttpStatus.BAD_REQUEST);
        }
    }
}
