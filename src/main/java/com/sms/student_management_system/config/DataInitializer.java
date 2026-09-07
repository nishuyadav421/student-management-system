package com.sms.student_management_system.config;

import com.sms.student_management_system.entity.Course;
import com.sms.student_management_system.entity.User;
import com.sms.student_management_system.repository.CourseRepository;
import com.sms.student_management_system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, 
                           CourseRepository courseRepository, 
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Admin create logic
        if (!userRepository.existsByUsername("admin@sms.com")) {
            User admin = new User();
            admin.setFullName("Super Admin");
            admin.setUsername("admin@sms.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
        }

        // Default Courses auto-create logic
        if (courseRepository.count() == 0) {
            courseRepository.save(new Course("Full Stack Java", "6 Months", 25000.0));
            courseRepository.save(new Course("Python Full Stack", "5 Months", 20000.0));
            courseRepository.save(new Course("Spring Boot & Microservices", "3 Months", 15000.0));
            courseRepository.save(new Course("React & Frontend", "3 Months", 12000.0));
        }
    }
}