package com.StudentManagement.javaservlet;

public class Student {
    private final int id;
    private final String name;
    private final String studentClass;
    private final int marks;
    private final String gender;

    public Student(int id, String name, String studentClass, int marks, String gender) {
        this.id = id;
        this.name = name;
        this.studentClass = studentClass;
        this.marks = marks;
        this.gender = gender;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getStudentClass() { return studentClass; } 
    public int getMarks() { return marks; }
    public String getGender() { return gender; }
}
