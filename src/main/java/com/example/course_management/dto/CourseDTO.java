package com.example.course_management.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Data Transfer Object για Course
 * Χρησιμοποιείται για form submission και validation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private Integer courseId;

    @NotBlank(message = "Department is required")
    @Size(min = 2, max = 255, message = "Department must be between 2 and 255 characters")
    private String dept;

    @NotBlank(message = "Course name is required")
    @Size(min = 2, max = 255, message = "Course name must be between 2 and 255 characters")
    private String courseName;

    private String code;

    @NotNull(message = "ECTS is required")
    @Min(value = 1, message = "ECTS must be at least 1")
    @Max(value = 10, message = "ECTS must be at most 10")
    private Integer ects;

    @NotBlank(message = "Semester is required")
    private String semester;

    // For display purposes
    private Integer hostId;
    private String universityName;
}

