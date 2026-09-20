package com.sms.student_management_system.controller;

import com.sms.student_management_system.dto.RegisterDTO;
import com.sms.student_management_system.dto.StudentResponseDTO;
import com.sms.student_management_system.entity.Enrollment;
import com.sms.student_management_system.entity.User;
import com.sms.student_management_system.repository.CourseRepository;
import com.sms.student_management_system.repository.EnrollmentRepository;
import com.sms.student_management_system.repository.UserRepository;
import com.sms.student_management_system.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StudentService studentService;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder; // <-- Injected for password hashing

    public AdminController(StudentService studentService, 
                           UserRepository userRepository, 
                           EnrollmentRepository enrollmentRepository,
                           CourseRepository courseRepository,
                           PasswordEncoder passwordEncoder) { // <-- Added to constructor
        this.studentService = studentService;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model, Authentication authentication) {
        if (authentication != null) {
            model.addAttribute("adminUsername", authentication.getName());
        }

        List<User> students = studentService.getAllStudents();
        List<StudentResponseDTO> studentDTOs = new ArrayList<>();

        for (User student : students) {
            List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);
            int count = enrollments.size();
            double fee = enrollments.stream().mapToDouble(Enrollment::getFee).sum();
            
            studentDTOs.add(new StudentResponseDTO(
                student.getId(),
                student.getFullName(),
                student.getUsername(),
                count,
                fee
            ));
        }
        model.addAttribute("students", studentDTOs);
        model.addAttribute("totalStudents", students.size());
        
        // Dynamic Course count
        model.addAttribute("totalCourses", courseRepository.count());
        model.addAttribute("monthlyEnrolled", enrollmentRepository.count());

        // Dynamic Top Course logic
        List<String> topCourses = enrollmentRepository.findTopPerformingCourses();
        String topCourse = (topCourses != null && !topCourses.isEmpty()) 
                ? topCourses.get(0) 
                : "No Enrollments Yet";
        model.addAttribute("topCourse", topCourse);

        return "admin-dashboard";
    }

    @GetMapping("/students/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new RegisterDTO());
        return "add-student";
    }

    @PostMapping("/students/add")
    public String saveStudent(@ModelAttribute("student") RegisterDTO dto, Model model) {
        if (studentService.usernameExists(dto.getUsername())) {
            model.addAttribute("error", "Username/Email pehle se registered hai!");
            return "add-student";
        }
        studentService.registerStudent(dto);
        return "redirect:/admin/dashboard?added";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/admin/dashboard?deleted";
    }

    @GetMapping("/students/view/{id}")
    public String viewStudentDetails(@PathVariable("id") Long id, Model model) {
        userRepository.findById(id).ifPresent(student -> {
            model.addAttribute("student", student);
            List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);
            model.addAttribute("enrollments", enrollments);
            model.addAttribute("coursesCount", enrollments.size());
            model.addAttribute("totalFee", enrollments.stream().mapToDouble(Enrollment::getFee).sum());
        });
        return "admin-student-view";
    }
    
    // 1. Show Edit Student Page
    @GetMapping("/students/edit/{id}")
    public String showEditStudentForm(@PathVariable("id") Long id, Model model) {
        User student = userRepository.findById(id).orElse(null);
        if (student == null) {
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("student", student);
        return "edit-student";
    }

    // 2. Process Edit Student Form
    @PostMapping("/students/edit/{id}")
    public String updateStudent(
            @PathVariable("id") Long id,
            @RequestParam("fullName") String fullName,
            @RequestParam(value = "newPassword", required = false) String newPassword) {

        User student = userRepository.findById(id).orElse(null);
        if (student != null) {
            student.setFullName(fullName);

            // Agar naya password enter kiya gaya ho tabhi update karein
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                student.setPassword(passwordEncoder.encode(newPassword.trim()));
            }

            userRepository.save(student);
        }

        return "redirect:/admin/dashboard?updated";
    }
    
    
    
}