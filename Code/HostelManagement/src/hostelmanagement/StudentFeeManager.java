import java.util.List;
import java.util.Arrays;

public class StudentFeeManager {

    public void viewStudentFeeStatus(String studentId) {
        System.out.println("Viewing fee status for student: " + studentId);
    }

    public void recordOfflinePayment(String studentId, double amount, String method, String date, String refNo) {
        System.out.println("Recorded offline payment of " + amount + " for student " + studentId +
                " via " + method + " on " + date + " [Ref: " + refNo + "]");
    }

    public void applyLateFee(List<String> studentIds) {
        System.out.println("Applying late fees to students: " + studentIds);
    }

    public void manageWaiver(String studentId, double amount, String type, String reason) {
        System.out.println("Applied waiver of " + amount + " (" + type + ") for student " + studentId +
                " due to: " + reason);
    }

    public void adjustFeeBalance(String studentId, double adjustmentAmount, String reason) {
        System.out.println("Adjusted fee balance of student " + studentId + " by " + adjustmentAmount +
                " due to: " + reason);
    }

    public void generateFeeReport(String filter) {
        System.out.println("Generating fee report with filter: " + filter);
    }

    public void sendFeeNotification(String studentId, String message) {
        System.out.println("Sending fee notification to " + studentId + ": " + message);
    }

    public static void main(String[] args) {
        StudentFeeManager manager = new StudentFeeManager();

        manager.viewStudentFeeStatus("STU001");
        manager.recordOfflinePayment("STU001", 15000.0, "Cash", "2025-05-14", "PAY123");

        List<String> lateFeeStudents = Arrays.asList("STU001", "STU002");
        manager.applyLateFee(lateFeeStudents);

        manager.manageWaiver("STU001", 1000.0, "Scholarship", "Merit-based award");
        manager.adjustFeeBalance("STU002", -500.0, "Extra class refund");
        manager.generateFeeReport("Monthly");
        manager.sendFeeNotification("STU001", "Your May fee has been received.");
    }
}
