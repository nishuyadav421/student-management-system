package com.sms.student_management_system.service;

import com.sms.student_management_system.dto.RegisterDTO;
import com.sms.student_management_system.entity.User;
import com.sms.student_management_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection
    public StudentService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Saare students lana
    public List<User> getAllStudents() {
        return userRepository.findByRole("ROLE_STUDENT");
    }

    // Student delete karna
    public void deleteStudent(Long id) {
        userRepository.deleteById(id);
    }

    // Username check karna
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // Naya student register/save karna
    public void registerStudent(RegisterDTO dto) {
        User student = new User(
                dto.getFullName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                "ROLE_STUDENT"
        );
        userRepository.save(student);
    }
}