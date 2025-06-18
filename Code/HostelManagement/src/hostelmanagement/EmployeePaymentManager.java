
import java.util.List;
import java.util.Arrays;

public class EmployeePaymentManager {

    public void processPayroll(String period) {
        System.out.println("Processing payroll for: " + period);
    }

    public void recordOfflinePayment(String employeeId, double amount, String method, String date, String refNo) {
        System.out.println("Recorded offline payment of " + amount + " for " + employeeId +
                " via " + method + " on " + date + " [Ref: " + refNo + "]");
    }

    public void manageDeductions(String employeeId, double amount, String type) {
        System.out.println("Applied " + type + " of " + amount + " for " + employeeId);
    }

    public void viewPaymentHistory(String employeeId) {
        System.out.println("Displaying payment history for: " + employeeId);
    }

    public void generatePayrollReports(String filter) {
        System.out.println("Generating payroll report with filter: " + filter);
    }

    public void generatePaymentSlips(String period) {
        System.out.println("Generating payment slips for: " + period);
    }

    public void configurePaymentSchedule(String frequency, List<String> payDates) {
        System.out.println("Configuring payment schedule: " + frequency);
        for (String date : payDates) {
            System.out.println(" - Pay Date: " + date);
        }
    }

    public static void main(String[] args) {
        EmployeePaymentManager manager = new EmployeePaymentManager();

        manager.processPayroll("May 2025");
        manager.recordOfflinePayment("EMP001", 50000.0, "Bank Transfer", "2025-05-10", "REF12345");
        manager.manageDeductions("EMP001", 2000.0, "Tax");
        manager.viewPaymentHistory("EMP001");
        manager.generatePayrollReports("Monthly");
        manager.generatePaymentSlips("May 2025");

        List<String> payDates = Arrays.asList("2025-05-10", "2025-06-10");
        manager.configurePaymentSchedule("Monthly", payDates);
    }
}
