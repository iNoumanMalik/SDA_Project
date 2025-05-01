// src/main/java/model/Fee.java
package model;

import java.time.LocalDate;

public class Fee {
    private int feeId;
    private int studentId;
    private double amount;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private String status;
    
    // Constructors, getters, setters
    public Fee(int feeId, int studentId, double amount, LocalDate dueDate, LocalDate paidDate, String status) {
        this.feeId = feeId;
        this.studentId = studentId;
        this.amount = amount;
        this.dueDate = dueDate;
        this.paidDate = paidDate;
        this.status = status;
    }
    
    // Getters and setters
    public int getFeeId() { return feeId; }
    public void setFeeId(int feeId) { this.feeId = feeId; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getPaidDate() { return paidDate; }
    public void setPaidDate(LocalDate paidDate) { this.paidDate = paidDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}