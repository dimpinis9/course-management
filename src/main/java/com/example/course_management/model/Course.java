package com.example.course_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courseID")
    private Integer courseId;

    @Column(name = "hostID", nullable = false)
    private Integer hostId;

    @NotBlank(message = "Department is required")
    @Size(min = 2, max = 255, message = "Department must be between 2 and 255 characters")
    @Column(nullable = false, length = 255)
    private String dept;

    @NotBlank(message = "Course name is required")
    @Size(min = 2, max = 255, message = "Course name must be between 2 and 255 characters")
    @Column(name = "courseName", nullable = false, length = 255)
    private String courseName;

    @Column(length = 50)
    private String code;

    @NotNull(message = "ECTS is required")
    @Min(value = 1, message = "ECTS must be at least 1")
    @Max(value = 10, message = "ECTS must be at most 10")
    @Column(nullable = false)
    private Integer ects;

    @Column(length = 50)
    private String semester; // "Fall" or "Spring"

    // Relationship with HostUniversity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostID", insertable = false, updatable = false)
    private HostUniversity hostUniversity;
}

