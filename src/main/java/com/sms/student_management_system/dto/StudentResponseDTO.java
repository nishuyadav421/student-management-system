package com.sms.student_management_system.dto;

public class StudentResponseDTO {
    private Long id;
    private String fullName;
    private String username;
    private int coursesCount;
    private Double totalFee;

    public StudentResponseDTO(Long id, String fullName, String username, int coursesCount, Double totalFee) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.coursesCount = coursesCount;
        this.totalFee = totalFee;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getUsername() { return username; }
    public int getCoursesCount() { return coursesCount; }
    public Double getTotalFee() { return totalFee; }
}