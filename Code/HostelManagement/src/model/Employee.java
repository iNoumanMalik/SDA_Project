// src/main/java/model/Employee.java
package model;

import java.time.LocalDate;

public class Employee {
    private int employeeId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String position;
    private LocalDate joinDate;
    private LocalDate leaveDate;
    private String status;
    
    // Constructors, getters, setters
    public Employee(int employeeId, String name, String email, String phone, String address, 
                   String position, LocalDate joinDate, LocalDate leaveDate, String status) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.position = position;
        this.joinDate = joinDate;
        this.leaveDate = leaveDate;
        this.status = status;
    }
    
    // Getters and setters
    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
    public LocalDate getLeaveDate() { return leaveDate; }
    public void setLeaveDate(LocalDate leaveDate) { this.leaveDate = leaveDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}