package com.example.course_management.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Home Controller - Χειρίζεται την αρχική σελίδα και το login
 * (Simplified version για testing)
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    /**
     * Simple login για testing
     * Στην πραγματική εφαρμογή θα γίνεται έλεγχος στη βάση
     */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                       @RequestParam String password,
                       HttpSession session,
                       Model model) {

        // Mock authentication - Για testing purposes
        // Στην πραγματικότητα θα ελέγχεις τη βάση
        if ("host".equals(username) && "host123".equals(password)) {
            // Simulate logged in host user
            session.setAttribute("userId", 1); // Mock userId
            session.setAttribute("username", username);
            session.setAttribute("usertype", "host_university");

            return "redirect:/dashboard";
        } else {
            model.addAttribute("errorMessage", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);

        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

