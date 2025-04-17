package com.sumit.quizApp.service.user;

import com.sumit.quizApp.model.user.User;
import com.sumit.quizApp.model.user.UserPrincipal;
import com.sumit.quizApp.repository.user.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/*
 * here we implements the UserDetailsService class
 *                   loadUserByUsername:-get AdminDetails from database and pass to AdminPrincipal to authenticate it
 */
@Service
public class MyUserDetailsService implements UserDetailsService
{
    private static final Logger logger= LogManager.getLogger(MyUserDetailsService.class);
    private final UserDao userDao;

    public MyUserDetailsService(UserDao userDao)
    {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        logger.info("loadUserByUsername service is called");
        User user=userDao.findByUsername(username).orElse(null);
        if(user==null)
        {
            logger.debug("{} not found for user login",username);
            throw new UsernameNotFoundException(username);
        }
        logger.debug("user found");
        return new UserPrincipal(user);
//            return new org.springframework.security.core.userdetails.User(
//                    user.getUsername(),
//                    user.getPassword(),
//                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRole()))
//            );
    }
}
