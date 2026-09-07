package com.sms.student_management_system.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private Double fee;

    private LocalDate enrollDate;

    public Enrollment() {
        this.enrollDate = LocalDate.now();
    }

    public Enrollment(User student, String courseName, Double fee) {
        this.student = student;
        this.courseName = courseName;
        this.fee = fee;
        this.enrollDate = LocalDate.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public Double getFee() { return fee; }
    public void setFee(Double fee) { this.fee = fee; }
    public LocalDate getEnrollDate() { return enrollDate; }
    public void setEnrollDate(LocalDate enrollDate) { this.enrollDate = enrollDate; }
}