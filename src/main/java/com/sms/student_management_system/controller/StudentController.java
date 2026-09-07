package com.sms.student_management_system.controller;

import com.sms.student_management_system.entity.Enrollment;
import com.sms.student_management_system.entity.User;
import com.sms.student_management_system.repository.EnrollmentRepository;
import com.sms.student_management_system.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentController(UserRepository userRepository, EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @GetMapping("/dashboard")
    public String studentDashboard(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        User student = userRepository.findByUsername(username).orElse(null);

        if (student != null) {
            List<Enrollment> myEnrollments = enrollmentRepository.findByStudent(student);
            double totalFee = myEnrollments.stream().mapToDouble(Enrollment::getFee).sum();

            model.addAttribute("student", student);
            model.addAttribute("enrollments", myEnrollments);
            model.addAttribute("totalCourses", myEnrollments.size());
            model.addAttribute("totalFee", totalFee);
        }

        return "student-dashboard";
    }
 // Student Controller ke andar add karein:
    @GetMapping("/receipt/{enrollmentId}")
    public String viewReceipt(@PathVariable("enrollmentId") Long enrollmentId, 
                              Model model, 
                              Authentication authentication) {
        if (authentication == null) {
            return "redirect:/student/login";
        }

        String username = authentication.getName();
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElse(null);

        // Security Check: Make sure receipt belongs to the logged-in student
        if (enrollment == null || !enrollment.getStudent().getUsername().equals(username)) {
            return "redirect:/student/dashboard";
        }

        model.addAttribute("enrollment", enrollment);
        model.addAttribute("student", enrollment.getStudent());
        return "fee-receipt";
    }
}