package com.StudentManagement.javaservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        ArrayList<Student> students = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT student_id, name, class, marks, gender FROM students";
            try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("student_id");  
                    String name = rs.getString("name");
                    String studentClass = rs.getString("class");
                    int marks = rs.getInt("marks");
                    String gender = rs.getString("gender");

                    students.add(new Student(id, name, studentClass, marks, gender));
                }
            }
        } catch (SQLException e) { // Print error in logs
            // Print error in logs
            out.println("<p>Error fetching student data: " + e.getMessage() + "</p>");
        }

        // Debug: Check if students list is empty
        if (students.isEmpty()) {
            out.println("<p>No students found. Please check if data exists in the database.</p>");
        }

        // Start HTML response
        out.println("<html lang='en'>");
        out.println("<head><title>Admin Dashboard</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; }");
        out.println(".navbar { background-color: #4CAF50; padding: 15px; color: white; text-align: center; }");
        out.println(".container { padding: 20px; }");
        out.println(".button { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; }");
        out.println(".button:hover { background-color: #45a049; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("table, th, td { border: 1px solid #ddd; }");
        out.println("th, td { padding: 8px; text-align: left; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='navbar'><h2>Admin Dashboard</h2></div>");
        out.println("<div class='container'><h3>Student Data</h3>");
        out.println("<table><thead><tr><th>ID</th><th>Name</th><th>Class</th><th>Marks</th><th>Gender</th><th>Actions</th></tr></thead>");
        out.println("<tbody>");

        // Display student data
        for (Student student : students) {
            out.println("<tr>");
            out.println("<td>" + student.getId() + "</td>");
            out.println("<td>" + student.getName() + "</td>");
            out.println("<td>" + student.getStudentClass() + "</td>");
            out.println("<td>" + student.getMarks() + "</td>");
            out.println("<td>" + student.getGender() + "</td>");
            out.println("<td>");
            out.println("<a href='updateStudent.html?id=" + student.getId() + "' class='button'>Edit</a> ");
            out.println("<a href='DeleteStudentServlet?id=" + student.getId() + "' class='button' onclick='return confirm(\"Are you sure?\")'>Delete</a>");
            out.println("</td>");
            out.println("</tr>");
        }

        out.println("</tbody></table>");
        out.println("<br><a href='addStudent.html' class='button'>Add Student</a>");
        out.println("<br><br><a href='LogoutServlet' class='button'>Logout</a>");
        out.println("</div>");
        out.println("</body></html>");
    }
}
