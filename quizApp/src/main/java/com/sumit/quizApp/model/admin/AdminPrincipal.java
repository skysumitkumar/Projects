package com.sumit.quizApp.model.admin;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
/*
* here we implements method of UserDetails to authenticate the user with checking these things
*       is admin have same role that we pass
*       getUsername
*       getPassword
*       isAccountNonExpired
*       isAccountNonLocked
*       isCredentialsNonExpired
*       isEnable
 */
public class AdminPrincipal implements UserDetails
{
    private final Admin admin;
    public AdminPrincipal(Admin admin)
    {
        this.admin=admin;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + admin.getRole()));
    }

    @Override
    public String getPassword()
    {
        return admin.getPassword();
    }

    @Override
    public String getUsername()
    {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled()
    {
        return UserDetails.super.isEnabled();
    }
}
