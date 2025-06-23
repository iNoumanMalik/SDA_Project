/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StudentLeavingGUI extends JFrame {
    private Map<String, String> currentAssignments = new HashMap<>();
    private Map<String, Double> studentBalances = new HashMap<>();
    private StudentManager studentManager;
    private RoomManager roomManager;

    private JComboBox<String> studentComboBox;
    private JTextArea outputArea;

    public StudentLeavingGUI(StudentManager studentManager, RoomManager roomManager) {
        this.studentManager = studentManager;
        this.roomManager = roomManager;
        
        setTitle("Student Move-Out System");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initializeUI();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Student selection panel
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.add(new JLabel("Select Student:"));
        studentComboBox = new JComboBox<>();
        refreshStudentList();
        selectionPanel.add(studentComboBox);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton startMoveOutBtn = new JButton("Start Move-Out");
        JButton makePaymentBtn = new JButton("Make Payment");
        JButton completeBtn = new JButton("Complete Process");

        startMoveOutBtn.addActionListener(e -> startMoveOut());
        makePaymentBtn.addActionListener(e -> makePayment());
        completeBtn.addActionListener(e -> completeMoveOut());

        buttonPanel.add(startMoveOutBtn);
        buttonPanel.add(makePaymentBtn);
        buttonPanel.add(completeBtn);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        mainPanel.add(selectionPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void refreshStudentList() {
        studentComboBox.removeAllItems();
        for (String student : studentManager.getAllStudents()) {
            studentComboBox.addItem(student);
        }
    }

    private void startMoveOut() {
        String selectedStudent = (String) studentComboBox.getSelectedItem();
        if (selectedStudent == null) {
            outputArea.append("Please select a student\n");
            return;
        }

        outputArea.append("Move-out process started for " + selectedStudent + "\n");
        provideChecklist();
        scheduleInspection(selectedStudent);
    }

    private void provideChecklist() {
        outputArea.append("Checklist:\n");
        outputArea.append("- Clean the room\n");
        outputArea.append("- Return keys\n");
        outputArea.append("- Schedule inspection\n");
        outputArea.append("- Pay any dues\n");
    }

    private void scheduleInspection(String studentName) {
        outputArea.append("Inspection scheduled for " + studentName + "\n");
        conductInspection(studentName);
    }

    private void conductInspection(String studentName) {
        boolean damageFound = new Random().nextBoolean();
        if (damageFound) {
            double damageFee = 100;
            studentBalances.put(studentName, studentBalances.getOrDefault(studentName, 0.0) + damageFee);
            outputArea.append("Damage found. Fee added: $" + damageFee + "\n");
        } else {
            outputArea.append("Room inspection passed with no issues.\n");
        }
        showOutstandingBalance(studentName);
    }

    private void showOutstandingBalance(String studentName) {
        double balance = studentBalances.getOrDefault(studentName, 0.0);
        outputArea.append("Outstanding balance: $" + balance + "\n");
    }

    private void makePayment() {
        String selectedStudent = (String) studentComboBox.getSelectedItem();
        if (selectedStudent == null) {
            outputArea.append("Please select a student\n");
            return;
        }

        String amountStr = JOptionPane.showInputDialog(this, "Enter payment amount:");
        try {
            double amount = Double.parseDouble(amountStr);
            double balance = studentBalances.getOrDefault(selectedStudent, 0.0);
            
            if (amount >= balance) {
                studentBalances.put(selectedStudent, 0.0);
                outputArea.append("Payment of $" + amount + " completed. No outstanding dues.\n");
                returnKeys(selectedStudent);
            } else {
                outputArea.append("Insufficient payment. Remaining balance: $" + (balance - amount) + "\n");
            }
        } catch (NumberFormatException e) {
            outputArea.append("Invalid payment amount\n");
        }
    }

    private void returnKeys(String studentName) {
        outputArea.append(studentName + " returned the keys.\n");
    }

    private void completeMoveOut() {
        String selectedStudent = (String) studentComboBox.getSelectedItem();
        if (selectedStudent == null) {
            outputArea.append("Please select a student\n");
            return;
        }

        outputArea.append("Move-out complete for " + selectedStudent + ". Room is now available.\n");
        // Here you would update your roomManager to mark the room as available
    }
}