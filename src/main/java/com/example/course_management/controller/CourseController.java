package com.example.course_management.controller;

import com.example.course_management.dto.CourseDTO;
import com.example.course_management.model.Course;
import com.example.course_management.service.CourseService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller για Course Management
 * Χειρίζεται όλες τις λειτουργίες του Course Management use case
 */
@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    /**
     * Εμφάνιση menu επιλογών Course Management
     */
    @GetMapping("/menu")
    public String showMenu(HttpSession session, Model model) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return "redirect:/login";
        }

        // Μέτρηση μαθημάτων για εμφάνιση στο menu
        long courseCount = courseService.countCoursesByUserId(userId);
        model.addAttribute("courseCount", courseCount);

        return "course-management-menu";
    }

    /**
     * Εμφάνιση φόρμας προσθήκης νέου μαθήματος
     */
    @GetMapping("/add")
    public String showAddForm(HttpSession session, Model model) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("courseDTO", new CourseDTO());
        return "course-add";
    }

    /**
     * Υποβολή φόρμας προσθήκης μαθήματος
     */
    @PostMapping("/add")
    public String addCourse(@Valid @ModelAttribute("courseDTO") CourseDTO courseDTO,
                           BindingResult bindingResult,
                           HttpSession session,
                           RedirectAttributes redirectAttributes,
                           Model model) {

        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return "redirect:/login";
        }

        // Έλεγχος validation errors
        if (bindingResult.hasErrors()) {
            return "course-add";
        }

        try {
            Course createdCourse = courseService.createCourse(courseDTO, userId);
            log.info("Course added successfully: {}", createdCourse.getCourseId());

            redirectAttributes.addFlashAttribute("successMessage",
                "Course '" + courseDTO.getCourseName() + "' added successfully!");

            return "redirect:/courses/list";

        } catch (Exception e) {
            log.error("Error adding course", e);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "course-add";
        }
    }

    /**
     * Εμφάνιση λίστας όλων των μαθημάτων του host
     */
    @GetMapping("/list")
    public String listCourses(HttpSession session, Model model) {
        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            List<CourseDTO> courses = courseService.getCoursesByUserId(userId);
            model.addAttribute("courses", courses);

            log.info("Displaying {} courses", courses.size());

        } catch (Exception e) {
            log.error("Error fetching courses", e);
            model.addAttribute("errorMessage", "Error loading courses: " + e.getMessage());
        }

        return "course-list";
    }

    /**
     * Διαγραφή μαθήματος
     */
    @PostMapping("/delete/{courseId}")
    public String deleteCourse(@PathVariable Integer courseId,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {

        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            boolean deleted = courseService.deleteCourse(courseId, userId);

            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage",
                    "Course deleted successfully!");
                log.info("Course {} deleted by user {}", courseId, userId);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage",
                    "Failed to delete course. It may not exist or you don't have permission.");
                log.warn("Failed to delete course {} by user {}", courseId, userId);
            }

        } catch (Exception e) {
            log.error("Error deleting course", e);
            redirectAttributes.addFlashAttribute("errorMessage",
                "Error: " + e.getMessage());
        }

        return "redirect:/courses/list";
    }

    /**
     * Εμφάνιση φόρμας επεξεργασίας μαθήματος
     */
    @GetMapping("/edit/{courseId}")
    public String showEditForm(@PathVariable Integer courseId,
                              HttpSession session,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            CourseDTO courseDTO = courseService.getCourseById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            model.addAttribute("courseDTO", courseDTO);
            return "course-edit";

        } catch (Exception e) {
            log.error("Error loading course for edit", e);
            redirectAttributes.addFlashAttribute("errorMessage",
                "Error: " + e.getMessage());
            return "redirect:/courses/list";
        }
    }

    /**
     * Υποβολή φόρμας επεξεργασίας μαθήματος
     */
    @PostMapping("/edit/{courseId}")
    public String updateCourse(@PathVariable Integer courseId,
                              @Valid @ModelAttribute("courseDTO") CourseDTO courseDTO,
                              BindingResult bindingResult,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        Integer userId = getUserIdFromSession(session);
        if (userId == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            courseDTO.setCourseId(courseId);
            return "course-edit";
        }

        try {
            courseService.updateCourse(courseId, courseDTO, userId);
            redirectAttributes.addFlashAttribute("successMessage",
                "Course updated successfully!");

            return "redirect:/courses/list";

        } catch (Exception e) {
            log.error("Error updating course", e);
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            courseDTO.setCourseId(courseId);
            return "course-edit";
        }
    }

    /**
     * Helper method για ανάκτηση userId από session
     */
    private Integer getUserIdFromSession(HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj == null) {
            log.warn("No userId in session");
            return null;
        }
        return (Integer) userIdObj;
    }
}

