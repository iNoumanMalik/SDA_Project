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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeManagementGUI extends JFrame {
    private EmployeeManager employeeManager;
    private DefaultListModel<Employee> employeeListModel;
    private JList<Employee> employeeList;

    public EmployeeManagementGUI(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
        initializeUI();
        refreshEmployeeList();
    }

    private void initializeUI() {
        setTitle("Hostel Employee Management");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        employeeListModel = new DefaultListModel<>();
        employeeList = new JList<>(employeeListModel);
        employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(employeeList);

        JButton addButton = new JButton("Add Employee");
        JButton removeButton = new JButton("Remove Selected");

        addButton.addActionListener(new AddEmployeeListener());
        removeButton.addActionListener(new RemoveEmployeeListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshEmployeeList() {
        employeeListModel.clear();
        for (Employee emp : employeeManager.getAllEmployees()) {
            employeeListModel.addElement(emp);
        }
    }

    private class AddEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a panel with 4 text fields for input
            JTextField nameField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField phoneField = new JTextField();
            JTextField experienceField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Phone:"));
            panel.add(phoneField);
            panel.add(new JLabel("Experience:"));
            panel.add(experienceField);

            int result = JOptionPane.showConfirmDialog(EmployeeManagementGUI.this, panel,
                    "Add New Employee", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String experience = experienceField.getText().trim();

                // Basic validation (all fields required)
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || experience.isEmpty()) {
                    JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                            "All fields are required.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Optionally, add more validation here (email format, phone number format, etc.)

                Employee newEmployee = new Employee(name, email, phone, experience);
                if (employeeManager.addEmployee(newEmployee)) {
                    refreshEmployeeList();
                } else {
                    JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                            "Employee with this name already exists or invalid input.",
                            "Add Employee Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class RemoveEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Employee selected = employeeList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                        "Please select an employee to remove.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(EmployeeManagementGUI.this,
                    "Are you sure you want to remove employee " + selected.getName() + "?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (employeeManager.removeEmployee(selected)) {
                    refreshEmployeeList();
                }
            }
        }
    }
}