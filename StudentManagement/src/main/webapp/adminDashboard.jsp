<%@ page import="java.util.ArrayList" %>
<%@ page import="com.StudentManagement.javaservlet.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; }
        .navbar { background-color: #4CAF50; padding: 15px; color: white; text-align: center; }
        .container { padding: 20px; }
        .button { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; }
        .button:hover { background-color: #45a049; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; }
        table, th, td { border: 1px solid #ddd; }
        th, td { padding: 8px; text-align: left; }
    </style>
</head>
<body>
    <div class="navbar">
        <h2>Admin Dashboard</h2>
    </div>

    <div class="container">
        <h3>Student Data</h3>
        
        <%
            Integer studentCount = (Integer) request.getAttribute("studentCount");
        %>
        <p><strong>Total Students: <%= (studentCount != null) ? studentCount : 0 %></strong></p>

        <table>
            <thead>
                <tr>
                    <th>Student ID</th>
                    <th>Name</th>
                    <th>Class</th>
                    <th>Marks</th>
                    <th>Gender</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    Object studentsObj = request.getAttribute("students");
                    ArrayList<Student> students = null;

                    if (studentsObj instanceof ArrayList<?>) {
                        students = (ArrayList<Student>) studentsObj;
                    }

                    if (students != null && !students.isEmpty()) {
                        for (Student student : students) {
                %>
                <tr>
                    <td><%= student.getId() %></td>
                    <td><%= student.getName() %></td>
                    <td><%= student.getStudentClass() %></td>
                    <td><%= student.getMarks() %></td>
                    <td><%= student.getGender() %></td>
                    <td>
                        <a href="UpdateStudentServlet?student_id=<%= student.getId() %>" class="button">Update</a>
                        <form action="DeleteStudentServlet" method="post" style="display: inline;">
                            <input type="hidden" name="student_id" value="<%= student.getId() %>">
                            <button type="submit" class="button">Delete</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" style="text-align: center; sacolor: red;">No students found.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
        
        <br>
        <button class="button" onclick="window.location.href='addStudent.html'">Add Student</button>
        <button class="button" onclick="window.location.href='deleteStudent.html'">Delete Student using ID</button>
        <button class="button" onclick="window.location.href='updateStudent.html'">Update Student using ID</button>
        <br><br>
        <button class="button" onclick="window.location.href='LogoutServlet'">Logout</button>
    </div>
</body>
</html>
