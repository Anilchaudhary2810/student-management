package com.StudentManagement.javaservlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String studentClass = request.getParameter("class"); // Dropdown values: Post Graduate, Undergraduate, PhD, School
        int marks = Integer.parseInt(request.getParameter("marks"));
        String gender = request.getParameter("gender"); // New gender field

        try {
            Connection con = DBUtil.getConnection();
            
            // Check if student already exists
            String checkQuery = "SELECT * FROM students WHERE name = ? AND class = ? AND gender = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setString(1, name);
            checkStmt.setString(2, studentClass);
            checkStmt.setString(3, gender);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Duplicate entry found - Redirect with an error message
                response.sendRedirect("addStudent.html?error=duplicate");
            } else {
                // Insert new student if no duplicate found
                String insertQuery = "INSERT INTO students (name, class, marks, gender) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = con.prepareStatement(insertQuery);
                insertStmt.setString(1, name);
                insertStmt.setString(2, studentClass);
                insertStmt.setInt(3, marks);
                insertStmt.setString(4, gender);
                
                insertStmt.executeUpdate();
                
                // Redirect to dashboard after successful insertion
                response.sendRedirect("adminDashboard.html");
            }
        } catch (SQLException e) {
            response.sendRedirect("addStudent.html?error=database");
        }
    }
}
