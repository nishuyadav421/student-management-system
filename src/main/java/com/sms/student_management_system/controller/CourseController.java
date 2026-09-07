package com.sms.student_management_system.controller;

import com.sms.student_management_system.entity.Course;
import com.sms.student_management_system.repository.CourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Saare courses ki list dikhane ke liye
    @GetMapping("/courses")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "courses-list";
    }

    // Naya course save karne ke liye
    @PostMapping("/courses/add")
    public String addCourse(@RequestParam("courseName") String courseName,
                            @RequestParam("duration") String duration,
                            @RequestParam("standardFee") Double fee) {
        Course course = new Course(courseName, duration, fee);
        courseRepository.save(course);
        return "redirect:/admin/courses?added";
    }

    // Course delete karne ke liye
    @GetMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseRepository.deleteById(id);
        return "redirect:/admin/courses?deleted";
    }
}