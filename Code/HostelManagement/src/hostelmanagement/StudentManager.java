package hostelmanagement;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<String> students;

    public StudentManager() {
        students = new ArrayList<>();
        // Add default students
        students.add("Ali Raza");
        students.add("Fatima Khan");
    }

    public List<String> getAllStudents() {
        return new ArrayList<>(students);
    }

    public boolean addStudent(String name) {
        if (name == null || name.trim().isEmpty()) return false;
        if (students.contains(name.trim())) return false;
        students.add(name.trim());
        return true;
    }

    public boolean removeStudent(String name) {
        return students.remove(name);
    }

    // --- START: ADDED for Update functionality ---
    /**
     * Updates an existing student's name.
     *
     * @param oldName The current name of the student to be updated.
     * @param newName The new name for the student.
     * @return true if the student was successfully updated, false otherwise.
     */
    public boolean updateStudent(String oldName, String newName) {
        if (oldName == null || oldName.trim().isEmpty() || newName == null || newName.trim().isEmpty()) {
            return false; // Invalid names
        }
        if (!students.contains(oldName.trim())) {
            return false; // Old name not found
        }
        // Check if newName already exists for a *different* student
        if (students.contains(newName.trim()) && !oldName.trim().equalsIgnoreCase(newName.trim())) {
            return false; // New name already exists for another student
        }

        int index = students.indexOf(oldName.trim());
        if (index != -1) {
            students.set(index, newName.trim());
            return true;
        }
        return false;
    }
    // --- END: ADDED for Update functionality ---
}