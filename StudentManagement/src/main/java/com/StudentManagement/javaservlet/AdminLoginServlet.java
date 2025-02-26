package com.StudentManagement.javaservlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the username and password parameters from the login form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Example hardcoded admin credentials
        String adminUsername = "anil";
        String adminPassword = "anil";

        // Check if the credentials match
        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            // Create a session and set an attribute to mark the admin as logged in
            HttpSession session = request.getSession();
            session.setAttribute("admin", "loggedIn");

            // Redirect to the admin dashboard
            response.sendRedirect("adminDashboard.html");
        } else {
            // If credentials don't match, redirect back to the login page
            response.sendRedirect("login.html");
        }
    }
}