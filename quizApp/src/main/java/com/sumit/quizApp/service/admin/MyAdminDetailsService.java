package com.sumit.quizApp.service.admin;

import com.sumit.quizApp.model.admin.Admin;
import com.sumit.quizApp.model.admin.AdminPrincipal;
import com.sumit.quizApp.repository.admin.AdminDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyAdminDetailsService implements UserDetailsService {

    private final AdminDao adminDao;

    public MyAdminDetailsService(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin=adminDao.findByUsername(username).orElse(null);
        if(admin==null)
        {
            System.out.println("user not found");
            throw new UsernameNotFoundException(username);
        }
        return new AdminPrincipal(admin);
//        return new org.springframework.security.core.userdetails.User(
//                admin.getUsername(),
//                admin.getPassword(),
//                Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+admin.getRole()))
//        );
    }
}
