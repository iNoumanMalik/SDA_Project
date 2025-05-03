/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostelmanagment;

/**
 *
 * @author sayyam
 */
public class LeavedEmployeeManager {
    private List<LeavedEmployee> leavedEmployees;

    public LeavedEmployeeManager() {
        this.leavedEmployees = new ArrayList<>();
    }

    // Add a new leaved employee
    public void addLeavedEmployee(LeavedEmployee employee) {
        leavedEmployees.add(employee);
    }

    // Remove by Employee ID
    public boolean removeLeavedEmployee(String employeeId) {
        return leavedEmployees.removeIf(emp -> emp.getEmployeeId().equals(employeeId));
    }

    // Get employee by ID
    public LeavedEmployee getEmployeeById(String employeeId) {
        for (LeavedEmployee emp : leavedEmployees) {
            if (emp.getEmployeeId().equals(employeeId)) {
                return emp;
            }
        }
        return null;
    }

    // Update details
    public boolean updateLeavedEmployee(String employeeId, LeavedEmployee updatedEmployee) {
        for (int i = 0; i < leavedEmployees.size(); i++) {
            if (leavedEmployees.get(i).getEmployeeId().equals(employeeId)) {
                leavedEmployees.set(i, updatedEmployee);
                return true;
            }
        }
        return false;
    }

    // Get all leaved employees
    public List<LeavedEmployee> getAllLeavedEmployees() {
        return new ArrayList<>(leavedEmployees); // return a copy for safety
    }

    // Count total leaved employees
    public int countLeavedEmployees() {
        return leavedEmployees.size();
    }
}
