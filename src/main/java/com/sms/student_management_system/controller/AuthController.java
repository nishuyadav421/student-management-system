package com.sms.student_management_system.controller;

import com.sms.student_management_system.dto.RegisterDTO;
import com.sms.student_management_system.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final StudentService studentService;

    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Default root url student login par point kare
    @GetMapping("/")
    public String home() {
        return "redirect:/student/login";
    }

    // 1. Dedicated Admin Login Route
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin-login";
    }

    // 2. Dedicated Student Login Route
    @GetMapping("/student/login")
    public String studentLoginPage() {
        return "student-login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new RegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterDTO dto, Model model) {
        if (studentService.usernameExists(dto.getUsername())) {
            model.addAttribute("error", "Username already taken!");
            return "register";
        }

        studentService.registerStudent(dto);
        return "redirect:/student/login?registered";
    }
}