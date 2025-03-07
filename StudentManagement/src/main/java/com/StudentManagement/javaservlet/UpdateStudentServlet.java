package com.StudentManagement.javaservlet;

import java.io.IOException;
import java.io.PrintWriter;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            int studentId = Integer.parseInt(request.getParameter("student_id"));

            String name = "", class1 = "", gender = "";
            int marks = 0;

            try (Connection conn = DBUtil.getConnection()) {
                String query = "SELECT * FROM students WHERE student_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, studentId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            name = rs.getString("name");
                            class1 = rs.getString("class");
                            marks = rs.getInt("marks");
                            gender = rs.getString("gender");
                        }
                    }
                }
            }

            
            out.println("<!DOCTYPE html>");
            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta http-equiv='X-UA-Compatible' content='IE=edge'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Update Student</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
            out.println(".form-container { background-color: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); width: 300px; text-align: center; }");
            out.println("h2 { text-align: center; }");
            out.println(".input-field, .dropdown { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ddd; border-radius: 5px; }");
            out.println(".submit-btn, .dashboard-btn { width: 100%; padding: 10px; border: none; color: white; font-size: 16px; cursor: pointer; border-radius: 5px; margin-top: 10px; }");
            out.println(".submit-btn { background-color: #4CAF50; }");
            out.println(".submit-btn:hover { background-color: #45a049; }");
            out.println(".dashboard-btn { background-color: #007BFF; }");
            out.println(".dashboard-btn:hover { background-color: #0056b3; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='form-container'>");
            out.println("<h2>Update Student</h2>");
            out.println("<form action='UpdateStudentServlet' method='POST'>");
            out.println("<input type='hidden' name='update' value='true'>"); 
            out.println("<input type='number' class='input-field' name='student_id' value='" + studentId + "' placeholder='Student ID' required readonly>");
            out.println("<input type='text' class='input-field' name='name' value='" + name + "' placeholder='Name' required>");
            out.println("<select name='class' class='dropdown' required>");
            out.println("<option value='Post Graduate'" + (class1.equals("Post Graduate") ? " selected" : "") + ">Post Graduate</option>");
            out.println("<option value='Undergraduate'" + (class1.equals("Undergraduate") ? " selected" : "") + ">Undergraduate</option>");
            out.println("<option value='PhD'" + (class1.equals("PhD") ? " selected" : "") + ">PhD</option>");
            out.println("<option value='School'" + (class1.equals("School") ? " selected" : "") + ">School</option>");
            out.println("</select>");
            out.println("<input type='number' class='input-field' name='marks' value='" + marks + "' placeholder='Marks' required>");
            out.println("<label>Gender:</label><br>");
            out.println("<input type='radio' name='gender' value='Male'" + (gender.equals("Male") ? " checked" : "") + "> Male");
            out.println("<input type='radio' name='gender' value='Female'" + (gender.equals("Female") ? " checked" : "") + "> Female");
            out.println("<input type='radio' name='gender' value='Other'" + (gender.equals("Other") ? " checked" : "") + "> Other");
            out.println("<button type='submit' class='submit-btn'>Update Student</button>");
            out.println("</form>");
            out.println("<button class='dashboard-btn' onclick=\"window.location.href='AdminDashboardServlet'\">Go to Dashboard</button>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("true".equals(request.getParameter("update"))) {
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
        } else {
            try {
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UpdateStudentServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateStudentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
