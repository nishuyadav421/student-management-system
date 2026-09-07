package com.sms.student_management_system.controller;

import com.sms.student_management_system.entity.Enrollment;
import com.sms.student_management_system.entity.User;
import com.sms.student_management_system.repository.CourseRepository;
import com.sms.student_management_system.repository.EnrollmentRepository;
import com.sms.student_management_system.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class EnrollmentController {

    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository; // 1. Field declare karein

    // 2. Constructor me CourseRepository inject karein
    public EnrollmentController(UserRepository userRepository, 
                                EnrollmentRepository enrollmentRepository, 
                                CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    // 1. Enroll Form Dikhane ke liye
    @GetMapping("/enroll")
    public String showEnrollForm(Model model) {
        model.addAttribute("students", userRepository.findByRole("ROLE_STUDENT"));
        model.addAttribute("courses", courseRepository.findAll()); // Ab error solve ho jayega
        return "enroll-course";
    }

    // 2. Course Enroll Submit karne ke liye
    @PostMapping("/enroll")
    public String saveEnrollment(@RequestParam("studentId") Long studentId,
                                 @RequestParam("courseName") String courseName,
                                 @RequestParam("fee") Double fee) {
        userRepository.findById(studentId).ifPresent(student -> {
            Enrollment enrollment = new Enrollment(student, courseName, fee);
            enrollmentRepository.save(enrollment);
        });
        return "redirect:/admin/dashboard?enrolled";
    }

    // 3. Enrolled Students List page
    @GetMapping("/enrolled-students")
    public String viewAllEnrollments(Model model) {
        model.addAttribute("enrollments", enrollmentRepository.findAll());
        return "enrolled-list";
    }
}