package com.StudentManagement.javaservlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("student_id"));

        try {
            Connection con = DBUtil.getConnection();

            String query = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, studentId);

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                response.sendRedirect("AdminDashboardServlet");
            } else {
                response.sendRedirect("deleteStudent.html");
            }
        } catch (SQLException e) {
            response.sendRedirect("deleteStudent.html");
        }
    }
}
