package com.StudentManagement.javaservlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet("/DeleteStudentServlet")
public class DeleteStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the student ID from the form
        int studentId = Integer.parseInt(request.getParameter("student_id"));

        try {
            // Assuming DBUtil is a utility class for managing DB connections.
            Connection con = DBUtil.getConnection();

            // SQL query to delete the student from the database
            String query = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, studentId);

            // Execute the delete query
            int rowsAffected = stmt.executeUpdate();
            
            // If the student is deleted successfully, redirect to the admin dashboard
            if (rowsAffected > 0) {
                response.sendRedirect("AdminDashboardServlet");
            } else {
                // If no rows are deleted, redirect back to the delete form
                response.sendRedirect("deleteStudent.html");
            }
        } catch (SQLException e) {
            // If there is an error, redirect back to the delete form
            response.sendRedirect("deleteStudent.html");
        }
    }
}
