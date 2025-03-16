package com.sumit.JobApp.service;

import com.sumit.JobApp.Dao.UserRepo;
import com.sumit.JobApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    //for register the user
    public User serviceUser(User user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        //System.out.println(user.getPassword());
        return repo.save(user);
    }
}
