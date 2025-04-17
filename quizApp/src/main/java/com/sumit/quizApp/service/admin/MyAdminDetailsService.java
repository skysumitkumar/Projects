package com.sumit.quizApp.service.admin;

import com.sumit.quizApp.model.admin.Admin;
import com.sumit.quizApp.model.admin.AdminPrincipal;
import com.sumit.quizApp.repository.admin.AdminDao;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/*
* here we implements the UserDetailsService class
*                   loadUserByUsername:-get AdminDetails from database and pass to AdminPrincipal to authenticate it
 */
@Service
public class MyAdminDetailsService implements UserDetailsService
{
    private static final Logger logger=LogManager.getLogger(MyAdminDetailsService.class);
    private final AdminDao adminDao;

    public MyAdminDetailsService(AdminDao adminDao)
    {
        this.adminDao=adminDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        logger.info("loadUserByUsername service is called");
        Admin admin=adminDao.findByUsername(username).orElse(null);
        if(admin==null)
        {
            logger.error("{} not found for Admin login",username);
            throw new UsernameNotFoundException(username);
        }
        logger.debug("Admin login username is found");
        return new AdminPrincipal(admin);
//        return new org.springframework.security.core.userdetails.User(
//                admin.getUsername(),
//                admin.getPassword(),
//                Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+admin.getRole()))
//        );
    }
}
