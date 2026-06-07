package com.charita.event_platform.config;

import com.charita.event_platform.model.Role;
import com.charita.event_platform.model.User;
import com.charita.event_platform.repository.UserRepository;
import com.charita.event_platform.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StartupRunner implements CommandLineRunner {
    private final UserService userService;

    public StartupRunner(UserService userService){
        this.userService=userService;
    }
    @Override
    public void run(String... args) throws Exception {

        //User user=new User();

        //user.setName("Charitha");
        //user.setEmail("charita881@gmail.com");
        //user.setPassword("test1");
        //user.setVerified(false);

        //userService.registerUser(user);

        //System.out.println("user saved successfully");
    }
}
