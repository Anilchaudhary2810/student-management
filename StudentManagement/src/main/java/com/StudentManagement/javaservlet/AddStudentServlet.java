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
        String name = request.getParameter("name").trim();
        String studentClass = request.getParameter("class").trim();
        String gender = request.getParameter("gender").trim();

        int marks;
        try {
            marks = Integer.parseInt(request.getParameter("marks").trim());
        } catch (NumberFormatException e) {
            response.sendRedirect("addStudent.html?error=invalid_input");
            return;
        }

        if (name.isEmpty() || studentClass.isEmpty() || gender.isEmpty()) {
            response.sendRedirect("addStudent.html?error=empty_fields");
            return;
        }

        try (Connection con = DBUtil.getConnection()) {
            String checkQuery = "SELECT * FROM students WHERE name = ? AND class = ? AND gender = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
                checkStmt.setString(1, name);
                checkStmt.setString(2, studentClass);
                checkStmt.setString(3, gender);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    response.sendRedirect("addStudent.html?error=duplicate");
                    return;
                }
            }

            String insertQuery = "INSERT INTO students (name, class, marks, gender) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                insertStmt.setString(1, name);
                insertStmt.setString(2, studentClass);
                insertStmt.setInt(3, marks);
                insertStmt.setString(4, gender);
                insertStmt.executeUpdate();
            }

            response.sendRedirect("AdminDashboardServlet");

        } catch (SQLException e) {
            response.sendRedirect("addStudent.html?error=database");
        }
    }
}
