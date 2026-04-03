package com.ttk.taskflow.config;


import com.ttk.taskflow.model.User;
import com.ttk.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    Logger logger = Logger.getLogger(DataInitializer.class.getName());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0){
            User defaultUser = new User();
            defaultUser.setUsername("admin");
            defaultUser.setEmail("admin@admin.com");
            defaultUser.setPassword(passwordEncoder.encode("password"));

            userRepository.save(defaultUser);
            logger.info("Default user created: admin");

        }
    }
}
