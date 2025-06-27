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
}
