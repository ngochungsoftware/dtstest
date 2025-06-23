package com.example.dts.config;

import com.example.dts.entity.User;
import com.example.dts.entity.UserRole;
import com.example.dts.entity.UserStatus;
import com.example.dts.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsernameAndDeletedFalse("admin")) {
                User admin = new User();
                admin.setName("Administrator");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@example.com");
                admin.setPhone("+1234567890");
                admin.setAvatar("https://via.placeholder.com/150");
                admin.setStatus(UserStatus.ACTIVE);
                admin.setRole(UserRole.ADMIN);
                admin.setDeleted(false);
                userRepository.save(admin);
            }
        };
    }
} 