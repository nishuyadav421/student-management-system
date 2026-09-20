package com.sms.student_management_system.repository;

import com.sms.student_management_system.entity.Enrollment;
import com.sms.student_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(User student);
    
 // Enrollments me se sabse common courseName nikalne ke liye
    @Query("SELECT e.courseName FROM Enrollment e GROUP BY e.courseName ORDER BY COUNT(e) DESC")
    List<String> findTopPerformingCourses();
}