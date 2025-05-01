// src/main/java/model/Payment.java
package model;

import java.time.LocalDate;

public class Payment {
    private int paymentId;
    private int employeeId;
    private double amount;
    private LocalDate paymentDate;
    private String month;
    private int year;
    
    // Constructors, getters, setters
    public Payment(int paymentId, int employeeId, double amount, LocalDate paymentDate, String month, int year) {
        this.paymentId = paymentId;
        this.employeeId = employeeId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.month = month;
        this.year = year;
    }
    
    // Getters and setters
    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}