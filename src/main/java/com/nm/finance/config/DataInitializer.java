package com.nm.finance.config;

import com.nm.finance.entity.Role;
import com.nm.finance.entity.Status;
import com.nm.finance.entity.User;
import com.nm.finance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {

        log.info("Checking if default admin user exists...");

        if (userRepository.count() == 0) {

            log.info("No users found. Creating default admin user...");

            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword("admin123");
            admin.setRole(Role.ROLE_ADMIN);
            admin.setStatus(Status.ACTIVE);

            userRepository.save(admin);

            log.info("Default admin created successfully with email: {}", admin.getEmail());

        } else {
            log.info("Users already exist. Skipping admin initialization.");
        }
    }
}