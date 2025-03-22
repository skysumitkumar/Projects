package com.sumit.quizApp.service.user;

import com.sumit.quizApp.model.user.User;
import com.sumit.quizApp.model.user.UserPrincipal;
import com.sumit.quizApp.repository.user.UserDao;
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
    private final UserDao userDao;

    public MyUserDetailsService(UserDao userDao)
    {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
            User user=userDao.findByUsername(username).orElse(null);
            if(user==null)
            {
                System.out.println("user not found");
                throw new UsernameNotFoundException(username);
            }
            return new UserPrincipal(user);
//            return new org.springframework.security.core.userdetails.User(
//                    user.getUsername(),
//                    user.getPassword(),
//                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRole()))
//            );
    }
}
