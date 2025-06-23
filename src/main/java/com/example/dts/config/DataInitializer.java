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
            if (!userRepository.existsByUsernameAndDeletedFalse("user")) {
                User user = new User();
                user.setName("Regular User");
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setEmail("user@example.com");
                user.setPhone("+1234567892");
                user.setAvatar("https://via.placeholder.com/150");
                user.setStatus(UserStatus.ACTIVE);
                user.setRole(UserRole.USER);
                user.setDeleted(false);
                userRepository.save(user);
            }
            if (!userRepository.existsByUsernameAndDeletedFalse("moderator")) {
                User moderator = new User();
                moderator.setName("Moderator User");
                moderator.setUsername("moderator");
                moderator.setPassword(passwordEncoder.encode("moderator123"));
                moderator.setEmail("moderator@example.com");
                moderator.setPhone("+1234567891");
                moderator.setAvatar("https://via.placeholder.com/150");
                moderator.setStatus(UserStatus.ACTIVE);
                moderator.setRole(UserRole.MODERATOR);
                moderator.setDeleted(false);
                userRepository.save(moderator);
            }
        };
    }
} 