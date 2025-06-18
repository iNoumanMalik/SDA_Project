/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostelmanagment;

/**
 *
 * @author sayyam
 */
import java.util.Date;

public class LeavedStudent {
    private String studentID;
    private String name;
    private String roomNumber;
    private Date departureDate;
    private String departureReason;
    private boolean hasVacated;
    private boolean hasPendingDues;
    private boolean isTemporaryLeave;
    private boolean isArchived;

    // Constructor
    public LeavedStudent(String studentID, String name, String roomNumber) {
        this.studentID = studentID;
        this.name = name;
        this.roomNumber = roomNumber;
        this.hasVacated = false;
        this.hasPendingDues = false;
        this.isTemporaryLeave = false;
        this.isArchived = false;
    }

    // ========================
    // Core Functionality
    // ========================

    public boolean markAsLeaved(Date departureDate, String reason) {
        if (departureDate == null || reason == null || reason.isEmpty()) {
            System.out.println("Departure date and reason are required.");
            return false;
        }

        if (!hasVacated) {
            System.out.println("Student has not vacated the room yet.");
            return false;
        }

        if (hasPendingDues) {
            System.out.println("Outstanding dues must be settled before proceeding.");
            return false;
        }

        this.departureDate = departureDate;
        this.departureReason = reason;
        archiveStudentData();
        updateRoomStatus();
        updateAttendance();
        sendNotifications();
        System.out.println("Student marked as leaved successfully.");
        return true;
    }

    public void markTemporaryLeave(Date leaveStart, String reason) {
        this.departureDate = leaveStart;
        this.departureReason = reason;
        this.isTemporaryLeave = true;
        System.out.println("Student marked as on temporary leave.");
        // Keep room allocation intact
    }

    public void vacateRoom() {
        this.hasVacated = true;
        System.out.println("Room has been vacated.");
    }

    public void settleDues() {
        this.hasPendingDues = false;
        System.out.println("Pending dues settled.");
    }

    // ========================
    // Internal Processes
    // ========================

    private void updateRoomStatus() {
        // Simulate room status update
        System.out.println("Room " + roomNumber + " marked as vacant.");
    }

    private void updateAttendance() {
        // Simulate attendance update
        System.out.println("Attendance updated with departure date: " + departureDate);
    }

    private void archiveStudentData() {
        this.isArchived = true;
        System.out.println("Student data archived.");
    }

    private void sendNotifications() {
        // Simulate notification to student, warden, and admin
        System.out.println("Notifications sent to student, warden, and admin.");
    }

    // ========================
    // Getters & Setters
    // ========================

    public boolean hasPendingDues() {
        return hasPendingDues;
    }

    public void setHasPendingDues(boolean hasPendingDues) {
        this.hasPendingDues = hasPendingDues;
    }

    public boolean isTemporaryLeave() {
        return isTemporaryLeave;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public String getDepartureReason() {
        return departureReason;
    }

    public boolean isHasVacated() {
        return hasVacated;
    }

    // ========================
    // Exception Handling
    // ========================

    public void handleSystemFailure() {
        System.out.println("System encountered an error. Please retry or contact admin.");
    }

    public void handleRecordNotFound() {
        System.out.println("Student record not found. Please verify the ID.");
    }

    public void handlePermissionError() {
        System.out.println("Access denied. You do not have permission to perform this operation.");
    }

    public void handleMissingData() {
        System.out.println("Missing departure date or reason. Cannot proceed.");
    }

    // ========================
    // Reporting
    // ========================

    public void generateLeavedStudentReport() {
        System.out.println("------ Leaved Student Report ------");
        System.out.println("ID: " + studentID);
        System.out.println("Name: " + name);
        System.out.println("Room: " + roomNumber);
        System.out.println("Departure Date: " + departureDate);
        System.out.println("Reason: " + departureReason);
        System.out.println("Vacated: " + hasVacated);
        System.out.println("Pending Dues: " + hasPendingDues);
        System.out.println("Temporary Leave: " + isTemporaryLeave);
        System.out.println("Archived: " + isArchived);
    }
}

