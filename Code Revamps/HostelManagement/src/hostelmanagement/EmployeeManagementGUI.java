/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelmanagement;

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
        setSize(450, 350); // Adjusted size for new button and better layout
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        employeeListModel = new DefaultListModel<>();
        employeeList = new JList<>(employeeListModel);
        employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(employeeList);

        JButton addButton = new JButton("Add Employee");
        JButton removeButton = new JButton("Remove Selected");
        JButton updateButton = new JButton("Update Selected"); // New update button

        addButton.addActionListener(new AddEmployeeListener());
        removeButton.addActionListener(new RemoveEmployeeListener());
        updateButton.addActionListener(new UpdateEmployeeListener()); // Add action listener for update

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Center buttons with spacing
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton); // Add the new update button to the panel

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

                Employee newEmployee = new Employee(name, email, phone, experience);
                if (employeeManager.addEmployee(newEmployee)) {
                    refreshEmployeeList();
                    JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                            "Employee added successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                            "Employee with this name already exists. Please use a unique name.",
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
                    JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                            "Employee removed successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                     JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                            "Failed to remove employee.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class UpdateEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Employee selectedEmployee = employeeList.getSelectedValue();
            if (selectedEmployee == null) {
                JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                        "Please select an employee to update.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Populate fields with current values for editing
            JTextField nameField = new JTextField(selectedEmployee.getName());
            JTextField emailField = new JTextField(selectedEmployee.getEmail());
            JTextField phoneField = new JTextField(selectedEmployee.getPhone());
            JTextField experienceField = new JTextField(selectedEmployee.getExperience());

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
                    "Update Employee Details for " + selectedEmployee.getName(),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String newName = nameField.getText().trim();
                String newEmail = emailField.getText().trim();
                String newPhone = phoneField.getText().trim();
                String newExperience = experienceField.getText().trim();

                // Basic validation (all fields required)
                if (newName.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty() || newExperience.isEmpty()) {
                    JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                            "All fields are required.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Call the update method in EmployeeManager
                if (employeeManager.updateEmployee(selectedEmployee.getName(), newName, newEmail, newPhone, newExperience)) {
                    refreshEmployeeList();
                    JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                            "Employee details updated successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                            "Failed to update employee. Check if the new name already exists for a different employee.",
                            "Update Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
