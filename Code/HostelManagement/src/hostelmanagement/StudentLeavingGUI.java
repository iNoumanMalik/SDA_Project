package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StudentLeavingGUI extends JFrame {
    private Map<String, String> currentAssignments = new HashMap<>();
    private Map<String, Double> studentBalances = new HashMap<>();
    private StudentManager studentManager;
    private RoomManager roomManager;

    // Color scheme (from LoginGUI)
    private final Color darkBackground = new Color(18, 18, 18);
    private final Color lighterBackground = new Color(30, 30, 30);
    private final Color accentColor = new Color(0, 150, 255);
    private final Color textColor = new Color(240, 240, 240);

    private JComboBox<String> studentComboBox;
    private JTextArea outputArea;
    private JButton startMoveOutBtn;
    private JButton makePaymentBtn;
    private JButton completeBtn;

    public StudentLeavingGUI(StudentManager studentManager, RoomManager roomManager) {
        this.studentManager = studentManager;
        this.roomManager = roomManager;

        setTitle("Hostel Management System - Student Move-Out");
        setSize(600, 500); // Slightly larger
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true); // Undecorated like LoginGUI
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20)); // Rounded corners

        initializeUI();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(darkBackground);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("STUDENT MOVE-OUT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(accentColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Content Panel (for selection, buttons, and output)
        JPanel contentPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(lighterBackground);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Student selection panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        contentPanel.add(createInputLabel("Select Student:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        studentComboBox = createStyledComboBox();
        refreshStudentList();
        contentPanel.add(studentComboBox, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        buttonPanel.setOpaque(false);
        startMoveOutBtn = createGradientButton("Start Move-Out", new Color(0, 180, 255), new Color(0, 100, 255));
        makePaymentBtn = createGradientButton("Make Payment", new Color(100, 220, 100), new Color(0, 150, 100));
        completeBtn = createGradientButton("Complete Process", new Color(255, 100, 100), new Color(200, 50, 50));

        startMoveOutBtn.addActionListener(e -> startMoveOut());
        makePaymentBtn.addActionListener(e -> makePayment());
        completeBtn.addActionListener(e -> completeMoveOut());

        buttonPanel.add(startMoveOutBtn);
        buttonPanel.add(makePaymentBtn);
        buttonPanel.add(completeBtn);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(20, 0, 20, 0);
        contentPanel.add(buttonPanel, gbc);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Segoe UI Mono", Font.PLAIN, 12)); // Monospaced for log-like output
        outputArea.setForeground(textColor);
        outputArea.setBackground(new Color(45, 45, 45)); // Darker background for text area
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1)); // Border for scroll pane

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0; // Allow it to expand vertically
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 0, 0);
        contentPanel.add(scrollPane, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JLabel createInputLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(textColor);
        return label;
    }

    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setForeground(textColor);
        comboBox.setBackground(new Color(60, 60, 60));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8))
        );
        ((JLabel)comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER); // Center align text
        return comboBox;
    }

    private JButton createGradientButton(String text, Color startColor, Color endColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0, 0, startColor,
                        getWidth(), getHeight(), endColor
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                g2.setColor(Color.WHITE);
                g2.setFont(getFont().deriveFont(Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), x, y);

                g2.dispose();
            }
        };

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 45)); // Slightly larger buttons
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void refreshStudentList() {
        studentComboBox.removeAllItems();
        // Assuming studentManager has a method to get a list of all active students
        for (String student : studentManager.getAllStudents()) {
            studentComboBox.addItem(student);
        }
        if (studentComboBox.getItemCount() > 0) {
            studentComboBox.setSelectedIndex(0);
        }
    }

    private void startMoveOut() {
        String selectedStudent = (String) studentComboBox.getSelectedItem();
        if (selectedStudent == null) {
            showErrorDialog("Please select a student to start the move-out process.", "No Student Selected");
            return;
        }

        outputArea.append("\n--- Starting Move-out for " + selectedStudent + " ---\n");
        provideChecklist();
        scheduleInspection(selectedStudent);
    }

    private void provideChecklist() {
        outputArea.append("\nMove-out Checklist:\n");
        outputArea.append("  • Clean the room thoroughly\n");
        outputArea.append("  • Return all issued keys\n");
        outputArea.append("  • Ensure all personal belongings are removed\n");
        outputArea.append("  • Clear any outstanding dues\n");
    }

    private void scheduleInspection(String studentName) {
        outputArea.append("\nScheduling room inspection for " + studentName + "...\n");
        // Simulate a delay for realism
        Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conductInspection(studentName);
                ((Timer)e.getSource()).stop();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void conductInspection(String studentName) {
        boolean damageFound = new Random().nextBoolean(); // Simulate random damage
        outputArea.append("Conducting room inspection...\n");

        if (damageFound) {
            double damageFee = 150 + (new Random().nextInt(10) * 10); // Random fee between 150-240
            studentBalances.put(studentName, studentBalances.getOrDefault(studentName, 0.0) + damageFee);
            outputArea.append("  • Damage found during inspection! A fee of $" + String.format("%.2f", damageFee) + " has been added.\n");
        } else {
            outputArea.append("  • Room inspection passed with no damages found.\n");
        }
        showOutstandingBalance(studentName);
    }

    private void showOutstandingBalance(String studentName) {
        double balance = studentBalances.getOrDefault(studentName, 0.0);
        outputArea.append("  • Current outstanding balance: $" + String.format("%.2f", balance) + "\n");
        if (balance > 0) {
            outputArea.append("Please proceed to 'Make Payment' to clear your dues.\n");
        }
    }

    private void makePayment() {
        String selectedStudent = (String) studentComboBox.getSelectedItem();
        if (selectedStudent == null) {
            showErrorDialog("Please select a student to make a payment.", "No Student Selected");
            return;
        }

        double currentBalance = studentBalances.getOrDefault(selectedStudent, 0.0);
        String amountStr = JOptionPane.showInputDialog(this,
                "<html><font color='" + toHex(textColor) + "'>Enter payment amount for " + selectedStudent + "<br>Outstanding Balance: $" + String.format("%.2f", currentBalance) + "</font></html>",
                "Make Payment",
                JOptionPane.PLAIN_MESSAGE);

        if (amountStr == null || amountStr.trim().isEmpty()) {
            outputArea.append("Payment cancelled or no amount entered.\n");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                showErrorDialog("Payment amount must be positive.", "Invalid Amount");
                return;
            }

            if (amount >= currentBalance) {
                studentBalances.put(selectedStudent, 0.0);
                outputArea.append("Payment of $" + String.format("%.2f", amount) + " received. All outstanding dues cleared for " + selectedStudent + ".\n");
                returnKeys(selectedStudent);
            } else {
                studentBalances.put(selectedStudent, currentBalance - amount);
                outputArea.append("Partial payment of $" + String.format("%.2f", amount) + " received. Remaining balance: $" + String.format("%.2f", studentBalances.get(selectedStudent)) + "\n");
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid payment amount. Please enter a numeric value.", "Invalid Input");
            outputArea.append("Payment failed: Invalid amount entered.\n");
        }
    }

    private void returnKeys(String studentName) {
        outputArea.append(studentName + " has successfully returned the hostel keys.\n");
    }

    private void completeMoveOut() {
        String selectedStudent = (String) studentComboBox.getSelectedItem();
        if (selectedStudent == null) {
            showErrorDialog("Please select a student to complete the move-out process.", "No Student Selected");
            return;
        }

        double balance = studentBalances.getOrDefault(selectedStudent, 0.0);
        if (balance > 0) {
            showErrorDialog("Cannot complete move-out. Outstanding balance of $" + String.format("%.2f", balance) + " must be cleared.", "Outstanding Dues");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "<html><font color='" + toHex(textColor) + "'>Are you sure you want to complete the move-out for " + selectedStudent + "?<br>This will mark their room as available.</font></html>",
                "Confirm Move-Out", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            outputArea.append("\n--- Completing Move-out for " + selectedStudent + " ---\n");
            // In a real application, you'd unassign the student from their room
            // roomManager.unassignStudentFromRoom(selectedStudent, currentAssignments.get(selectedStudent));
            // studentManager.removeStudent(selectedStudent); // Or mark as inactive

            outputArea.append("Move-out process successfully completed for " + selectedStudent + ".\n");
            outputArea.append("The room previously occupied by " + selectedStudent + " is now available for new assignments.\n");
            refreshStudentList(); // Update the list after a student moves out
            JOptionPane.showMessageDialog(this,
                    "<html><font color='" + toHex(textColor) + "'>Student " + selectedStudent + " has successfully moved out.</font></html>",
                    "Move-Out Complete", JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Close the window after successful move-out
        } else {
            outputArea.append("Move-out completion cancelled by user.\n");
        }
    }

    private void showErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(this,
                "<html><font color='" + toHex(textColor) + "'>" + message + "</font></html>",
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}