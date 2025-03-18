package com.StudentManagement.javaservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT * FROM students WHERE student_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, studentId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        request.setAttribute("studentId", studentId);
                        request.setAttribute("name", rs.getString("name"));
                        request.setAttribute("class1", rs.getString("class"));
                        request.setAttribute("marks", rs.getInt("marks"));
                        request.setAttribute("gender", rs.getString("gender"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateStudentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("updateStudent.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = DBUtil.getConnection()) {
            int studentId = Integer.parseInt(request.getParameter("student_id"));
            String name = request.getParameter("name");
            String class1 = request.getParameter("class");
            int marks = Integer.parseInt(request.getParameter("marks"));
            String gender = request.getParameter("gender");

            String updateQuery = "UPDATE students SET name=?, class=?, marks=?, gender=? WHERE student_id=?";
            try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                stmt.setString(1, name);
                stmt.setString(2, class1);
                stmt.setInt(3, marks);
                stmt.setString(4, gender);
                stmt.setInt(5, studentId);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    response.sendRedirect("AdminDashboardServlet?message=Student Updated Successfully");
                } else {
                    response.sendRedirect("UpdateStudentServlet?student_id=" + studentId + "&error=Update Failed");
                }
            }
        } catch (SQLException e) {
            response.sendRedirect("UpdateStudentServlet?student_id=" + request.getParameter("student_id") + "&error=Database Error");
        }
    }
}
