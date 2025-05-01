// src/main/java/model/Student.java
package model;

import java.time.LocalDate;

public class Student {
    private int studentId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private int roomId;
    private LocalDate joinDate;
    private LocalDate leaveDate;
    private String status;
    
    // Constructors, getters, setters
    public Student(int studentId, String name, String email, String phone, String address, 
                  int roomId, LocalDate joinDate, LocalDate leaveDate, String status) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.roomId = roomId;
        this.joinDate = joinDate;
        this.leaveDate = leaveDate;
        this.status = status;
    }
    
    // Getters and setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
    public LocalDate getLeaveDate() { return leaveDate; }
    public void setLeaveDate(LocalDate leaveDate) { this.leaveDate = leaveDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}