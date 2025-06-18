package hostelmanagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.awt.*; // Import for BorderLayout, GridLayout, etc. if needed later

public class FinanceManagerGUI extends JFrame {

    private FinanceManager manager; // Use the separate FinanceManager class

    public FinanceManagerGUI(FinanceManager financeManager) {
        this.manager = financeManager; // Initialize with an existing FinanceManager instance
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Hostel Finance Management");
        setSize(420, 450); // Increased height to accommodate more buttons/layout
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window

        // Use a JPanel with a BorderLayout for better organization
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Use a GridBagLayout for flexible button placement
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Each button takes a new row
        gbc.fill = GridBagConstraints.HORIZONTAL; // Buttons expand horizontally
        gbc.insets = new Insets(5, 0, 5, 0); // Padding between components

        // Employee Section
        JLabel lblEmployee = new JLabel("Employee Payment Actions:");
        lblEmployee.setFont(lblEmployee.getFont().deriveFont(Font.BOLD, 14f));
        gbc.insets = new Insets(10, 0, 5, 0); // More top padding for section header
        buttonPanel.add(lblEmployee, gbc);
        gbc.insets = new Insets(5, 0, 5, 0); // Reset padding for buttons

        JButton btnProcessPayroll = new JButton("Process Payroll");
        btnProcessPayroll.addActionListener(e -> {
            String period = JOptionPane.showInputDialog(this, "Enter payroll period (e.g. May 2025):");
            if (period != null && !period.trim().isEmpty()) {
                manager.processPayroll(period.trim());
                JOptionPane.showMessageDialog(this, "Payroll processed for: " + period);
            }
        });
        buttonPanel.add(btnProcessPayroll, gbc);

        JButton btnRecordPaymentEmp = new JButton("Record Employee Payment");
        btnRecordPaymentEmp.addActionListener(e -> {
            JTextField empIdField = new JTextField();
            JTextField amountField = new JTextField();
            JTextField methodField = new JTextField();
            JTextField dateField = new JTextField();
            JTextField refNoField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
            inputPanel.add(new JLabel("Employee ID:"));
            inputPanel.add(empIdField);
            inputPanel.add(new JLabel("Amount:"));
            inputPanel.add(amountField);
            inputPanel.add(new JLabel("Payment Method:"));
            inputPanel.add(methodField);
            inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
            inputPanel.add(dateField);
            inputPanel.add(new JLabel("Reference Number:"));
            inputPanel.add(refNoField);

            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Record Employee Payment",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String empId = empIdField.getText().trim();
                String amountStr = amountField.getText().trim();
                String method = methodField.getText().trim();
                String date = dateField.getText().trim();
                String refNo = refNoField.getText().trim();

                if (empId.isEmpty() || amountStr.isEmpty() || method.isEmpty() || date.isEmpty() || refNo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    double amount = Double.parseDouble(amountStr);
                    manager.recordOfflinePaymentEmployee(empId, amount, method, date, refNo);
                    JOptionPane.showMessageDialog(this, "Recorded payment of " + amount + " for employee " + empId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(btnRecordPaymentEmp, gbc);

        JButton btnManageDeduction = new JButton("Manage Deductions");
        btnManageDeduction.addActionListener(e -> {
            JTextField empIdField = new JTextField();
            JTextField amountField = new JTextField();
            JTextField typeField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
            inputPanel.add(new JLabel("Employee ID:"));
            inputPanel.add(empIdField);
            inputPanel.add(new JLabel("Deduction Amount:"));
            inputPanel.add(amountField);
            inputPanel.add(new JLabel("Deduction Type:"));
            inputPanel.add(typeField);

            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Manage Employee Deductions",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String empId = empIdField.getText().trim();
                String amountStr = amountField.getText().trim();
                String type = typeField.getText().trim();

                if (empId.isEmpty() || amountStr.isEmpty() || type.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    double amount = Double.parseDouble(amountStr);
                    manager.manageDeductions(empId, amount, type);
                    JOptionPane.showMessageDialog(this, "Deduction applied for employee " + empId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(btnManageDeduction, gbc);

        // Add more employee buttons here if desired
        // JButton btnViewPaymentHistory = new JButton("View Payment History");
        // btnViewPaymentHistory.addActionListener(e -> { /* ... */ });
        // buttonPanel.add(btnViewPaymentHistory, gbc);

        // Student Section
        JLabel lblStudent = new JLabel("Student Fee Actions:");
        lblStudent.setFont(lblStudent.getFont().deriveFont(Font.BOLD, 14f));
        gbc.insets = new Insets(20, 0, 5, 0); // More top padding for section header
        buttonPanel.add(lblStudent, gbc);
        gbc.insets = new Insets(5, 0, 5, 0); // Reset padding for buttons

        JButton btnViewFeeStatus = new JButton("View Fee Status");
        btnViewFeeStatus.addActionListener(e -> {
            String studentId = JOptionPane.showInputDialog(this, "Student ID:");
            if (studentId != null && !studentId.trim().isEmpty()) {
                manager.viewStudentFeeStatus(studentId.trim());
                JOptionPane.showMessageDialog(this, "Viewed fee status for: " + studentId);
            }
        });
        buttonPanel.add(btnViewFeeStatus, gbc);

        JButton btnRecordPaymentStu = new JButton("Record Student Payment");
        btnRecordPaymentStu.addActionListener(e -> {
            JTextField studentIdField = new JTextField();
            JTextField amountField = new JTextField();
            JTextField methodField = new JTextField();
            JTextField dateField = new JTextField();
            JTextField refNoField = new JTextField();

            JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
            inputPanel.add(new JLabel("Student ID:"));
            inputPanel.add(studentIdField);
            inputPanel.add(new JLabel("Amount:"));
            inputPanel.add(amountField);
            inputPanel.add(new JLabel("Payment Method:"));
            inputPanel.add(methodField);
            inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
            inputPanel.add(dateField);
            inputPanel.add(new JLabel("Reference Number:"));
            inputPanel.add(refNoField);

            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Record Student Payment",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String studentId = studentIdField.getText().trim();
                String amountStr = amountField.getText().trim();
                String method = methodField.getText().trim();
                String date = dateField.getText().trim();
                String refNo = refNoField.getText().trim();

                if (studentId.isEmpty() || amountStr.isEmpty() || method.isEmpty() || date.isEmpty() || refNo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    double amount = Double.parseDouble(amountStr);
                    manager.recordOfflinePaymentStudent(studentId, amount, method, date, refNo);
                    JOptionPane.showMessageDialog(this, "Recorded payment of " + amount + " for student " + studentId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(btnRecordPaymentStu, gbc);

        JButton btnApplyLateFee = new JButton("Apply Late Fees");
        btnApplyLateFee.addActionListener(e -> {
            String ids = JOptionPane.showInputDialog(this, "Enter Student IDs separated by commas:");
            if (ids != null && !ids.trim().isEmpty()) {
                List<String> studentIds = Arrays.asList(ids.split("\\s*,\\s*"));
                manager.applyLateFee(studentIds);
                JOptionPane.showMessageDialog(this, "Applied late fees to: " + studentIds);
            }
        });
        buttonPanel.add(btnApplyLateFee, gbc);

        JButton btnManageWaiver = new JButton("Manage Waiver");
        btnManageWaiver.addActionListener(e -> {
            JTextField studentIdField = new JTextField();
            JTextField amountField = new JTextField();
            JTextField typeField = new JTextField();
            JTextArea reasonArea = new JTextArea(3, 20);
            JScrollPane scrollPane = new JScrollPane(reasonArea);

            JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
            inputPanel.add(new JLabel("Student ID:"));
            inputPanel.add(studentIdField);
            inputPanel.add(new JLabel("Waiver Amount:"));
            inputPanel.add(amountField);
            inputPanel.add(new JLabel("Waiver Type:"));
            inputPanel.add(typeField);
            inputPanel.add(new JLabel("Reason:"));
            inputPanel.add(scrollPane);

            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Manage Waiver",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String studentId = studentIdField.getText().trim();
                String amountStr = amountField.getText().trim();
                String type = typeField.getText().trim();
                String reason = reasonArea.getText().trim();

                if (studentId.isEmpty() || amountStr.isEmpty() || type.isEmpty() || reason.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    double amount = Double.parseDouble(amountStr);
                    manager.manageWaiver(studentId, amount, type, reason);
                    JOptionPane.showMessageDialog(this, "Waiver applied for student " + studentId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(btnManageWaiver, gbc);

        // Add the button panel to the center of the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel); // Add the main panel to the JFrame
    }
}