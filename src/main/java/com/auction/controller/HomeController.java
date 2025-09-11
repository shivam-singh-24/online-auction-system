package com.auction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Default route: redirect to home page
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/html/index.html"; // ✅ Change to login.html if needed
    }

    // Login page
    @GetMapping("/login")
    public String goToLoginPage() {
        return "redirect:/html/login.html";
    }

    // Registration page
    @GetMapping("/register")
    public String goToRegisterPage() {
        return "redirect:/html/register.html";
    }

    // Home page (after login)
    @GetMapping("/home")
    public String goToHomePage() {
        return "redirect:/html/index.html";
    }

    // Auctions listing page
    @GetMapping("/auctions")
    public String goToAuctionsPage() {
        return "redirect:/html/auctions.html";
    }

    // Admin dashboard
    @GetMapping("/admin")
    public String goToAdminDashboard() {
        return "redirect:/html/admin-dashboard.html";
    }
}
