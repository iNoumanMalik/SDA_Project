/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostelmanagment;

/**
 *
 * @author sayyam
 */
public class LeavedEmployee {
    private String name;
    private String employeeId;
    private String reason;
    private String leaveDate;

    public LeavedEmployee(String name, String employeeId, String reason, String leaveDate) {
        this.name = name;
        this.employeeId = employeeId;
        this.reason = reason;
        this.leaveDate = leaveDate;
    }

    // Getters and Setters
    public String getName() { return name; }
    public String getEmployeeId() { return employeeId; }
    public String getReason() { return reason; }
    public String getLeaveDate() { return leaveDate; }

    public void setName(String name) { this.name = name; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public void setReason(String reason) { this.reason = reason; }
    public void setLeaveDate(String leaveDate) { this.leaveDate = leaveDate; }

    @Override
    public String toString() {
        return name + " (ID: " + employeeId + ") - Left on " + leaveDate + " | Reason: " + reason;
    }
}
