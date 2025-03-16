package com.sumit.JobApp.service;



import com.sumit.JobApp.Dao.UserRepo;
import com.sumit.JobApp.model.User;
import com.sumit.JobApp.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user=repo.findByUsername(username);
            if(user==null)
            {
                System.out.println("user not found");
                throw new UsernameNotFoundException(username);
            }
            return new UserPrincipal(user);
    }
}
