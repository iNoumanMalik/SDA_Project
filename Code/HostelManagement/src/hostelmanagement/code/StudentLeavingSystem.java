import java.util.*;

class Student {
    String studentId;
    String name;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }
}

class HousingOffice {
    Map<String, String> currentAssignments = new HashMap<>();
    Map<String, Double> studentBalances = new HashMap<>();

    public void assignRoom(Student student, String roomId) {
        currentAssignments.put(student.studentId, roomId);
        studentBalances.put(student.studentId, 0.0);
        System.out.println(student.name + " assigned to room " + roomId);
    }

    public void startMoveOut(Student student) {
        if (!currentAssignments.containsKey(student.studentId)) {
            System.out.println("Student not currently assigned any accommodation.");
            return;
        }

        System.out.println("Move-out process started for " + student.name);
        provideChecklist();
        scheduleInspection(student);
    }

    public void provideChecklist() {
        System.out.println("Checklist:");
        System.out.println("- Clean the room");
        System.out.println("- Return keys");
        System.out.println("- Schedule inspection");
        System.out.println("- Pay any dues");
    }

    public void scheduleInspection(Student student) {
        System.out.println("Inspection scheduled for " + student.name);
        conductInspection(student);
    }

    public void conductInspection(Student student) {
        boolean damageFound = new Random().nextBoolean();  // Randomly simulate damage
        if (damageFound) {
            double damageFee = 100;
            studentBalances.put(student.studentId, studentBalances.get(student.studentId) + damageFee);
            System.out.println("Damage found. Fee added: $" + damageFee);
        } else {
            System.out.println("Room inspection passed with no issues.");
        }
        showOutstandingBalance(student);
    }

    public void showOutstandingBalance(Student student) {
        double balance = studentBalances.get(student.studentId);
        System.out.println("Outstanding balance: $" + balance);
    }

    public void makeFinalPayment(Student student, double amount) {
        double balance = studentBalances.get(student.studentId);
        if (amount >= balance) {
            studentBalances.put(student.studentId, 0.0);
            System.out.println("Payment of $" + amount + " completed. No outstanding dues.");
            returnKeys(student);
        } else {
            System.out.println("Insufficient payment. Remaining balance: $" + (balance - amount));
        }
    }

    public void returnKeys(Student student) {
        System.out.println(student.name + " returned the keys.");
        completeMoveOut(student);
    }

    public void completeMoveOut(Student student) {
        currentAssignments.remove(student.studentId);
        System.out.println("Move-out complete. Room is now available for new students.");
    }
}

public class StudentLeavingSystem {
    public static void main(String[] args) {
        Student student = new Student("S456", "Amina");
        HousingOffice office = new HousingOffice();

        // Setup - Assign room
        office.assignRoom(student, "C303");

        // Start move-out
        office.startMoveOut(student);

        // Student pays balance
        office.makeFinalPayment(student, 100);  // Adjust amount based on damage
    }
}
