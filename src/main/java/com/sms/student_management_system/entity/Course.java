package com.sms.student_management_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String courseName;

    @Column(nullable = false)
    private String duration; // e.g., "6 Months"

    @Column(nullable = false)
    private Double standardFee;

    public Course() {}

    public Course(String courseName, String duration, Double standardFee) {
        this.courseName = courseName;
        this.duration = duration;
        this.standardFee = standardFee;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public Double getStandardFee() { return standardFee; }
    public void setStandardFee(Double standardFee) { this.standardFee = standardFee; }
}