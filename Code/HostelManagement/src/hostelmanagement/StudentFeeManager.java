package hostelmanagement;

import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author unique-pc
 */
public class StudentFeeManager {
    
public void viewStudentFeeStatus(String studentId) {
        // Fetch and display fee status from database
    }

    public void recordOfflinePayment(String studentId, double amount, String method, String date, String refNo) {
        // Validate and update payment
    }

    public void applyLateFee(List<String> studentIds) {
        // Calculate and apply late fee based on policy
    }

    public void manageWaiver(String studentId, double amount, String type, String reason) {
        // Apply waiver and update balance
    }

    public void adjustFeeBalance(String studentId, double adjustmentAmount, String reason) {
        // Add/subtract from student fee
    }

    public void generateFeeReport(String filter) {
        // Generate and export reports
    }

    public void sendFeeNotification(String studentId, String message) {
        // Send notification via preferred method
    }
}
