/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelmanagement;

/**
 *
 * @author SP23-BSE-014
 */

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
}