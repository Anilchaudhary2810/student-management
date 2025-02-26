package com.StudentManagement.javaservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        String name = request.getParameter("name");
        String studentClass = request.getParameter("class");
        int marks = Integer.parseInt(request.getParameter("marks"));
        String gender = request.getParameter("gender");

        try {
            Connection con = DBUtil.getConnection();
            String query = "UPDATE students SET name = ?, class = ?, marks = ?, gender = ? WHERE student_id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, studentClass);
            stmt.setInt(3, marks);
            stmt.setString(4, gender);
            stmt.setInt(5, studentId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                response.sendRedirect("adminDashboard.html");
            } else {
                response.sendRedirect("updateStudent.html?error=notfound");
            }
        } catch (SQLException e) {
            response.sendRedirect("updateStudent.html?error=database");
        }
    }
}
