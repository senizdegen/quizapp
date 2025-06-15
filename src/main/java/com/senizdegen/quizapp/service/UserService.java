package com.senizdegen.quizapp.service;

import com.senizdegen.quizapp.dao.UserRepository;
import com.senizdegen.quizapp.domain.model.Role;
import com.senizdegen.quizapp.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    public User craete(User user){
        if(userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("User with this username already exists");
        }

        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("User with this username already exists");
        }
        return userRepository.save(user);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public UserDetailsService userDetailsService(){
        return this::getByUsername;
    }

    public User getCurrentUser(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
}
