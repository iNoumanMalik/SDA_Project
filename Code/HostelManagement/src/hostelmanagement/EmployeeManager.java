package hostelmanagement;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employees;

    public EmployeeManager() {
        employees = new ArrayList<>();
        employees.add(new Employee("Adil Bashir", "adil12@example.com", "1234567890", "5 years"));
        employees.add(new Employee("Kazim Shauket", "Kazim23@example.com", "0987654321", "3 years"));
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees); // defensive copy
    }

    public boolean addEmployee(Employee employee) {
        if (employee == null || employee.getName() == null || employee.getName().trim().isEmpty())
            return false;
        if (employees.contains(employee))
            return false;
        employees.add(employee);
        return true;
    }

    public boolean removeEmployee(Employee employee) {
        return employees.remove(employee);
    }

    public boolean updateEmployee(String originalName, String newName, String newEmail, String newPhone, String newExperience) {
        for (Employee emp : employees) {
            if (emp.getName().equalsIgnoreCase(originalName)) {
                if (!originalName.equalsIgnoreCase(newName)) {
                    for (Employee e : employees) {
                        if (e.getName().equalsIgnoreCase(newName) && e != emp)
                            return false;
                    }
                }
                emp.setName(newName);
                emp.setEmail(newEmail);
                emp.setPhone(newPhone);
                emp.setExperience(newExperience);
                return true;
            }
        }
        return false;
    }
}
