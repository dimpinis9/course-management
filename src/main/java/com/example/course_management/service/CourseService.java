package com.example.course_management.service;

import com.example.course_management.dto.CourseDTO;
import com.example.course_management.model.Course;
import com.example.course_management.model.HostUniversity;
import com.example.course_management.repository.CourseRepository;
import com.example.course_management.repository.HostUniversityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

    private final CourseRepository courseRepository;
    private final HostUniversityRepository hostUniversityRepository;

    /**
     * Δημιουργία νέου μαθήματος
     */
    @Transactional
    public Course createCourse(CourseDTO courseDTO, Integer userId) {
        log.info("Creating course: {} for userId: {}", courseDTO.getCourseName(), userId);

        // Βρίσκουμε το hostId από το userId
        HostUniversity host = hostUniversityRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Host University not found for user: " + userId));

        // Ελέγχουμε αν υπάρχει ήδη μάθημα με τον ίδιο κωδικό
        if (courseDTO.getCode() != null && !courseDTO.getCode().isEmpty()) {
            if (courseRepository.existsByCodeAndHostId(courseDTO.getCode(), host.getHostId())) {
                throw new RuntimeException("Course with code " + courseDTO.getCode() + " already exists");
            }
        }

        // Δημιουργούμε το Course entity
        Course course = new Course();
        course.setHostId(host.getHostId());
        course.setDept(courseDTO.getDept());
        course.setCourseName(courseDTO.getCourseName());
        course.setCode(courseDTO.getCode());
        course.setEcts(courseDTO.getEcts());
        course.setSemester(courseDTO.getSemester());

        Course savedCourse = courseRepository.save(course);
        log.info("Course created successfully with ID: {}", savedCourse.getCourseId());

        return savedCourse;
    }

    /**
     * Ανάκτηση όλων των μαθημάτων για έναν Host
     */
    public List<CourseDTO> getCoursesByUserId(Integer userId) {
        log.info("Fetching courses for userId: {}", userId);

        HostUniversity host = hostUniversityRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Host University not found for user: " + userId));

        List<Course> courses = courseRepository.findByHostId(host.getHostId());

        return courses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ανάκτηση όλων των μαθημάτων
     */
    public List<CourseDTO> getAllCourses() {
        log.info("Fetching all courses");
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ανάκτηση μαθήματος με ID
     */
    public Optional<CourseDTO> getCourseById(Integer courseId) {
        log.info("Fetching course with ID: {}", courseId);
        return courseRepository.findById(courseId)
                .map(this::convertToDTO);
    }

    /**
     * Ενημέρωση μαθήματος
     */
    @Transactional
    public Course updateCourse(Integer courseId, CourseDTO courseDTO, Integer userId) {
        log.info("Updating course ID: {} by userId: {}", courseId, userId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseId));

        // Έλεγχος ότι το μάθημα ανήκει στον host
        HostUniversity host = hostUniversityRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Host University not found"));

        if (!course.getHostId().equals(host.getHostId())) {
            throw new RuntimeException("Unauthorized: Course does not belong to this host");
        }

        // Update fields
        course.setDept(courseDTO.getDept());
        course.setCourseName(courseDTO.getCourseName());
        course.setCode(courseDTO.getCode());
        course.setEcts(courseDTO.getEcts());
        course.setSemester(courseDTO.getSemester());

        Course updatedCourse = courseRepository.save(course);
        log.info("Course updated successfully");

        return updatedCourse;
    }

    /**
     * Διαγραφή μαθήματος
     */
    @Transactional
    public boolean deleteCourse(Integer courseId, Integer userId) {
        log.info("Deleting course ID: {} by userId: {}", courseId, userId);

        HostUniversity host = hostUniversityRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Host University not found"));

        int deleted = courseRepository.deleteByCourseIdAndHostId(courseId, host.getHostId());

        if (deleted > 0) {
            log.info("Course deleted successfully");
            return true;
        } else {
            log.warn("Course not found or unauthorized deletion attempt");
            return false;
        }
    }

    /**
     * Μετατροπή Course entity σε CourseDTO
     */
    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setDept(course.getDept());
        dto.setCourseName(course.getCourseName());
        dto.setCode(course.getCode());
        dto.setEcts(course.getEcts());
        dto.setSemester(course.getSemester());
        dto.setHostId(course.getHostId());
        return dto;
    }

    /**
     * Μέτρηση μαθημάτων για έναν host
     */
    public long countCoursesByUserId(Integer userId) {
        HostUniversity host = hostUniversityRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Host University not found"));
        return courseRepository.countByHostId(host.getHostId());
    }
}

