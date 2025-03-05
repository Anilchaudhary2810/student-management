package com.StudentManagement.javaservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;

public class AdminDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // Check if admin is logged in
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("index.html");
            return;
        }

        ArrayList<Student> students = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT * FROM students"; 
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    int id = rs.getInt("student_id");
                    String name = rs.getString("name");
                    String studentClass = rs.getString("class");
                    int marks = rs.getInt("marks");
                    String gender = rs.getString("gender");

                    students.add(new Student(id, name, studentClass, marks, gender));
                }
            }
        } catch (SQLException e) { 
            request.setAttribute("error", "Database error while fetching students.");
        }

        // Debugging logs to check if data is retrieved
        System.out.println("📌 Total Students Retrieved: " + students.size());
        for (Student s : students) {
            System.out.println("Student: " + s.getId() + " - " + s.getName());
        }

        // Pass student data to JSP
        request.setAttribute("students", students);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminDashboard.jsp");
        dispatcher.forward(request, response);
    }
}
