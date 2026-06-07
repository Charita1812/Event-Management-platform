package com.charita.event_platform.service;

import com.charita.event_platform.dto.LoginResponse;
import com.charita.event_platform.model.Role;
import com.charita.event_platform.model.User;
import com.charita.event_platform.repository.UserRepository;
import com.charita.event_platform.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService (UserRepository userRepository,BCryptPasswordEncoder passwordEncoder,JwtUtil jwtUtil){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User registerUser(User user){

        User existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser!=null){
            throw new RuntimeException("Email Already Exists");
        }

        String encodedPassword= passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.ATTENDEE);
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    public LoginResponse login(String email, String password){

        User user=userRepository.findByEmail(email);
        if(user==null)
            throw new RuntimeException("User Not Found");

        boolean match= passwordEncoder.matches(password,user.getPassword());
        if(!match)
            throw new RuntimeException("Invalid Password");

        return new LoginResponse(
                jwtUtil.generateToken(email)
        );
    }
}
