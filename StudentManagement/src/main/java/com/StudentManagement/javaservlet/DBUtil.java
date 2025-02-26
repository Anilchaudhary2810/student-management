package com.StudentManagement.javaservlet;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/student_portal";
    private static final String USER = "root";
    private static final String PASSWORD = "Anil@$123";

    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection to the database
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found.");
        }
    }
}
