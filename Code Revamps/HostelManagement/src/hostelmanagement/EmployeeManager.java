/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelmanagement;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employees;

    public EmployeeManager() {
        employees = new ArrayList<>();
        // Optionally add some default employees
        employees.add(new Employee("Adil Bashir", "adil12@example.com", "1234567890", "5 years"));
        employees.add(new Employee("Kazim Shauket", "Kazim23@example.com", "0987654321", "3 years"));
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public boolean addEmployee(Employee employee) {
        if (employee == null || employee.getName() == null || employee.getName().trim().isEmpty())
            return false;
        if (employees.contains(employee)) // uses Employee.equals() based on name
            return false; // avoid duplicates
        employees.add(employee);
        return true;
    }

    public boolean removeEmployee(Employee employee) {
        return employees.remove(employee);
    }

    /**
     * Updates an existing employee's details. Finds the employee by their current name
     * and updates their email, phone, and experience. If the name itself is changing,
     * it will also update the name.
     *
     * @param originalName The current name of the employee to be updated.
     * @param newName The new name for the employee.
     * @param newEmail The new email for the employee.
     * @param newPhone The new phone number for the employee.
     * @param newExperience The new experience string for the employee.
     * @return true if the employee was found and updated, false otherwise.
     */
    public boolean updateEmployee(String originalName, String newName, String newEmail, String newPhone, String newExperience) {
        for (Employee emp : employees) {
            if (emp.getName().equalsIgnoreCase(originalName)) {
                // Before updating the name, check if the new name already exists for a *different* employee
                // This prevents accidentally overwriting another employee if the name is unique identifier
                if (!originalName.equalsIgnoreCase(newName) && employees.stream().anyMatch(e -> e.getName().equalsIgnoreCase(newName) && e != emp)) {
                    // New name conflicts with another existing employee
                    return false;
                }

                emp.setName(newName);
                emp.setEmail(newEmail);
                emp.setPhone(newPhone);
                emp.setExperience(newExperience);
                return true;
            }
        }
        return false; // Employee not found
    }
}
