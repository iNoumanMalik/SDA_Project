package hostelmanagement;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employees;

    public EmployeeManager() {
        employees = new ArrayList<>();
        // Optional default entries
        employees.add(new Employee("Adil Bashir", "adil12@example.com", "1234567890", "5 years"));
        employees.add(new Employee("Kazim Shauket", "Kazim23@example.com", "0987654321", "3 years"));
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees); // Return a copy for encapsulation
    }

    public boolean addEmployee(Employee employee) {
        // Basic validation for null or empty name
        if (employee == null || employee.getName() == null || employee.getName().trim().isEmpty())
            return false;

        // Check for duplicate employee by name using the Employee's equals() method
        if (employees.contains(employee))
            return false;

        employees.add(employee);
        return true;
    }

    public boolean removeEmployee(Employee employee) {
        // Removes the employee using Employee's equals() method for comparison
        return employees.remove(employee);
    }

    /**
     * Updates an existing employee's details by their original name.
     * Prevents changing to a new name that already exists for another employee.
     *
     * @param originalName The current name of the employee to be updated.
     * @param newName The new name for the employee.
     * @param newEmail The new email for the employee.
     * @param newPhone The new phone number for the employee.
     * @param newExperience The new experience details for the employee.
     * @return true if the employee was found and updated, false otherwise (e.g., original name not found, or new name conflicts).
     */
    public boolean updateEmployee(String originalName, String newName, String newEmail, String newPhone, String newExperience) {
        for (Employee emp : employees) {
            if (emp.getName().equalsIgnoreCase(originalName)) {
                // If the name is changing, check if the new name already exists for a different employee
                if (!originalName.equalsIgnoreCase(newName) &&
                        employees.stream().anyMatch(e -> e.getName().equalsIgnoreCase(newName) && e != emp)) {
                    return false; // New name conflicts with another employee
                }

                // Update the employee's details
                emp.setName(newName);
                emp.setEmail(newEmail);
                emp.setPhone(newPhone);
                emp.setExperience(newExperience);
                return true;
            }
        }
        return false; // Employee with originalName not found
    }
}
