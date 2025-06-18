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
    private List<String> employees;

    public EmployeeManager() {
        employees = new ArrayList<>();
        // Optionally add some default employees
        employees.add("John Doe");
        employees.add("Jane Smith");
    }

    public List<String> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public boolean addEmployee(String name) {
        if (name == null || name.trim().isEmpty()) return false;
        if (employees.contains(name)) return false; // avoid duplicates
        employees.add(name.trim());
        return true;
    }

    public boolean removeEmployee(String name) {
        return employees.remove(name);
    }
}

