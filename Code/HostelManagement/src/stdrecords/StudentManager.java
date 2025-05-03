/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.stdrecords;

import java.util.*;

public class StudentManager {
    static class Student {
        String id, name, email, course;
        
        public Student(String name, String email, String course) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
            this.email = email;
            this.course = course;
        }
        
        @Override
        public String toString() {
            return String.format("ID: %s\nName: %s\nEmail: %s\nCourse: %s\n", 
                              id, name, email, course);
        }
    }

    static List<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nSTUDENT MANAGEMENT SYSTEM");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addStudent() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter course: ");
        String course = scanner.nextLine();
        
        students.add(new Student(name, email, course));
        System.out.println("Student added successfully!");
    }

    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        students.forEach(System.out::println);
    }

    static void updateStudent() {
        System.out.print("Enter student ID to update: ");
        String id = scanner.nextLine();
        
        students.stream()
            .filter(s -> s.id.equals(id))
            .findFirst()
            .ifPresentOrElse(s -> {
                System.out.print("Enter new name: ");
                s.name = scanner.nextLine();
                System.out.print("Enter new email: ");
                s.email = scanner.nextLine();
                System.out.print("Enter new course: ");
                s.course = scanner.nextLine();
                System.out.println("Student updated!");
            }, () -> System.out.println("Student not found!"));
    }

    static void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine();
        
        if (students.removeIf(s -> s.id.equals(id))) {
            System.out.println("Student deleted!");
        } else {
            System.out.println("Student not found!");
        }
    }
}