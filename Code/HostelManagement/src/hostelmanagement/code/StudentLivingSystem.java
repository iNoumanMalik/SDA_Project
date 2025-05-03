import java.util.*;

class Student {
    String studentId;
    String name;
    boolean isLoggedIn;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.isLoggedIn = false;
    }

    public boolean login(String enteredId) {
        if (this.studentId.equals(enteredId)) {
            isLoggedIn = true;
            System.out.println(name + " logged in successfully.");
            return true;
        } else {
            System.out.println("Login failed.");
            return false;
        }
    }
}

class Accommodation {
    String id;
    String type;
    double rent;
    boolean available;

    public Accommodation(String id, String type, double rent) {
        this.id = id;
        this.type = type;
        this.rent = rent;
        this.available = true;
    }

    public void showDetails() {
        System.out.println("Accommodation ID: " + id);
        System.out.println("Type: " + type);
        System.out.println("Rent: $" + rent);
        System.out.println("Available: " + available);
    }
}

class HousingSystem {
    List<Accommodation> accommodations;
    Map<String, String> assignments;

    public HousingSystem() {
        accommodations = new ArrayList<>();
        assignments = new HashMap<>();
        accommodations.add(new Accommodation("A101", "Single", 300));
        accommodations.add(new Accommodation("B202", "Shared", 200));
    }

    public void showAvailableOptions() {
        System.out.println("Available accommodations:");
        for (Accommodation acc : accommodations) {
            if (acc.available) {
                acc.showDetails();
                System.out.println("-----");
            }
        }
    }

    public Accommodation selectAccommodation(String accId) {
        for (Accommodation acc : accommodations) {
            if (acc.id.equals(accId) && acc.available) {
                return acc;
            }
        }
        return null;
    }

    public void apply(Student student, Accommodation acc) {
        if (acc != null && acc.available) {
            System.out.println("Application submitted for " + acc.id);
            approveApplication(student, acc);
        } else {
            System.out.println("Accommodation not available.");
        }
    }

    private void approveApplication(Student student, Accommodation acc) {
        System.out.println("Application approved for " + student.name);
        signContract(student, acc);
    }

    private void signContract(Student student, Accommodation acc) {
        System.out.println(student.name + " signed lease for " + acc.id);
        processPayment(student, acc);
    }

    private void processPayment(Student student, Accommodation acc) {
        System.out.println("Payment received: $" + acc.rent);
        acc.available = false;
        assignments.put(student.studentId, acc.id);
        notifyMoveIn(student, acc);
    }

    private void notifyMoveIn(Student student, Accommodation acc) {
        System.out.println("Move-in date confirmed. Welcome to your new home, " + student.name + "!");
    }
}

public class StudentLivingSystem {
    public static void main(String[] args) {
        Student student = new Student("S123", "Adil");
        HousingSystem system = new HousingSystem();

        if (student.login("S123")) {
            system.showAvailableOptions();

            Accommodation selected = system.selectAccommodation("A101");
            system.apply(student, selected);
        }
    }
}

