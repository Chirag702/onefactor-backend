package com.onefactor.app.Service.ServiceImpl;

import java.util.ArrayList;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        // Load user from database or other source
        return new org.springframework.security.core.userdetails.User(phone, "", new ArrayList<>());
    }
}
