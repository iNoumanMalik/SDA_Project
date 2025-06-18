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
    private DefaultListModel<String> employeeListModel;
    private JList<String> employeeList;

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
        for (String emp : employeeManager.getAllEmployees()) {
            employeeListModel.addElement(emp);
        }
    }

    private class AddEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog(EmployeeManagementGUI.this,
                    "Enter Employee Name:", "Add Employee", JOptionPane.PLAIN_MESSAGE);
            if (name != null && !name.trim().isEmpty()) {
                if (employeeManager.addEmployee(name.trim())) {
                    refreshEmployeeList();
                } else {
                    JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                            "Employee already exists or invalid name.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class RemoveEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = employeeList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(EmployeeManagementGUI.this,
                        "Please select an employee to remove.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(EmployeeManagementGUI.this,
                    "Are you sure you want to remove employee " + selected + "?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (employeeManager.removeEmployee(selected)) {
                    refreshEmployeeList();
                }
            }
        }
    }
}
