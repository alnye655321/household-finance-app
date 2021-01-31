package com.finance.finance.services;

import com.finance.finance.models.AuthUser;
import com.finance.finance.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Called from SecurityConfigurer auth.userDetailsService(myUserDetailsService);
     * Checking if we can create a user and return it
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

//        List<com.finance.finance.entities.User> myUser = userRepository.findAll();

        com.finance.finance.entities.User myUser = userRepository.findByName(s);
        //TODO should be checking database here and providing my own custom extended security.core.userdetails.User with additional props
        return new AuthUser(myUser.getName(), myUser.getHashedPassword(), new ArrayList<>(), myUser.getUserId());
    }


}
