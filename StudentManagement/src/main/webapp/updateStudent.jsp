<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%
    String errorMessage = request.getParameter("error");
    int studentId = (Integer) request.getAttribute("studentId");
    String name = (String) request.getAttribute("name");
    String class1 = (String) request.getAttribute("class1");
    int marks = (Integer) request.getAttribute("marks");
    String gender = (String) request.getAttribute("gender");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Student</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .form-container { background-color: white; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); width: 300px; text-align: center; }
        h2 { text-align: center; }
        .input-field, .dropdown { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ddd; border-radius: 5px; }
        .submit-btn, .dashboard-btn { width: 100%; padding: 10px; border: none; color: white; font-size: 16px; cursor: pointer; border-radius: 5px; margin-top: 10px; }
        .submit-btn { background-color: #4CAF50; }
        .submit-btn:hover { background-color: #45a049; }
        .dashboard-btn { background-color: #007BFF; }
        .dashboard-btn:hover { background-color: #0056b3; }
        .error { color: red; font-size: 14px; }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Update Student</h2>
        <% if (errorMessage != null) { %>
            <p class="error"><%= errorMessage %></p>
        <% } %>
        <form action="UpdateStudentServlet" method="POST">
            <input type="hidden" name="update" value="true">
            <input type="number" class="input-field" name="student_id" value="<%= studentId %>" readonly>
            <input type="text" class="input-field" name="name" value="<%= name %>" required>
            <select name="class" class="dropdown" required>
                <option value="Post Graduate" <%= class1.equals("Post Graduate") ? "selected" : "" %>>Post Graduate</option>
                <option value="Undergraduate" <%= class1.equals("Undergraduate") ? "selected" : "" %>>Undergraduate</option>
                <option value="PhD" <%= class1.equals("PhD") ? "selected" : "" %>>PhD</option>
                <option value="School" <%= class1.equals("School") ? "selected" : "" %>>School</option>
            </select>
            <input type="number" class="input-field" name="marks" value="<%= marks %>" required>
            <label>Gender:</label><br>
            <input type="radio" name="gender" value="Male" <%= gender.equals("Male") ? "checked" : "" %>> Male
            <input type="radio" name="gender" value="Female" <%= gender.equals("Female") ? "checked" : "" %>> Female
            <input type="radio" name="gender" value="Other" <%= gender.equals("Other") ? "checked" : "" %>> Other
            <button type="submit" class="submit-btn">Update Student</button>
        </form>
        <button class="dashboard-btn" onclick="window.location.href='AdminDashboardServlet'">Go to Dashboard</button>
    </div>
</body>
</html>
