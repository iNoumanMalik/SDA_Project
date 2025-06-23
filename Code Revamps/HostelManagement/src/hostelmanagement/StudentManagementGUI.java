package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentManagementGUI extends JFrame {
    private StudentManager studentManager;
    private DefaultListModel<String> studentListModel;
    private JList<String> studentList;

    public StudentManagementGUI(StudentManager studentManager) {
        this.studentManager = studentManager;
        initializeUI();
        refreshStudentList();
    }

    private void initializeUI() {
        setTitle("Hostel Student Management");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        studentListModel = new DefaultListModel<>();
        studentList = new JList<>(studentListModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(studentList);

        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Selected");

        addButton.addActionListener(new AddStudentListener());
        removeButton.addActionListener(new RemoveStudentListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshStudentList() {
        studentListModel.clear();
        for (String student : studentManager.getAllStudents()) {
            studentListModel.addElement(student);
        }
    }

    private class AddStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog(StudentManagementGUI.this,
                    "Enter Student Name:", "Add Student", JOptionPane.PLAIN_MESSAGE);
            if (name != null && !name.trim().isEmpty()) {
                if (studentManager.addStudent(name.trim())) {
                    refreshStudentList();
                } else {
                    JOptionPane.showMessageDialog(StudentManagementGUI.this,
                            "Student already exists or invalid name.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class RemoveStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = studentList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(StudentManagementGUI.this,
                        "Please select a student to remove.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(StudentManagementGUI.this,
                    "Are you sure you want to remove student " + selected + "?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (studentManager.removeStudent(selected)) {
                    refreshStudentList();
                }
            }
        }
    }
}
