import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeeManagementSystem {
    // === MODEL ===
    class Employee {
        private String id;
        private String name;
        private String department;
        private String title;
        private String email;
        private String status; // Active / Inactive

        // Constructor
        public Employee(String id, String name, String department, String title, String email, String status) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.title = title;
            this.email = email;
            this.status = status;
        }

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    // === SERVICE ===
    private Map<String, Employee> employeeMap = new HashMap<>();

    // Create new employee
    public void addEmployee(Employee employee) {
        if (!employeeMap.containsKey(employee.getId())) {
            employeeMap.put(employee.getId(), employee);
            System.out.println("Employee added: " + employee.getName());
        } else {
            System.out.println("Duplicate Employee ID: " + employee.getId());
        }
    }

    // Update existing employee
    public void updateEmployee(String id, Employee updatedEmployee) {
        if (employeeMap.containsKey(id)) {
            employeeMap.put(id, updatedEmployee);
            System.out.println("Employee updated: " + updatedEmployee.getName());
        } else {
            System.out.println("Employee not found: " + id);
        }
    }

    // Delete (or mark as inactive)
    public void deleteEmployee(String id) {
        if (employeeMap.containsKey(id)) {
            Employee employee = employeeMap.get(id);
            employee.setStatus("Inactive");
            System.out.println("Employee marked as inactive: " + employee.getName());
        } else {
            System.out.println("Employee not found: " + id);
        }
    }

    // View all employees
    public List<Employee> viewAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    // === VIEW (for console interaction, basic UI) ===
    public void start() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. View Employees");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String department = scanner.nextLine();
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    Employee employee = new Employee(id, name, department, title, email, "Active");
                    addEmployee(employee);
                    break;
                case 2:
                    System.out.print("Enter ID to update: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New Department: ");
                    String newDepartment = scanner.nextLine();
                    System.out.print("Enter New Title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Enter New Email: ");
                    String newEmail = scanner.nextLine();
                    Employee updatedEmployee = new Employee(updateId, newName, newDepartment, newTitle, newEmail, "Active");
                    updateEmployee(updateId, updatedEmployee);
                    break;
                case 3:
                    System.out.print("Enter ID to delete: ");
                    String deleteId = scanner.nextLine();
                    deleteEmployee(deleteId);
                    break;
                case 4:
                    List<Employee> employees = viewAllEmployees();
                    for (Employee emp : employees) {
                        System.out.println("ID: " + emp.getId() + ", Name: " + emp.getName() + ", Status: " + emp.getStatus());
                    }
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    // Main method to run the application
    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        ems.start();
    }
}