package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.util.List;

// Assume FinanceManager.java exists and is correctly implemented
/*
// Placeholder for FinanceManager for compilation purposes
class FinanceManager {
    public void processPayroll(String period) {
        System.out.println("Processing payroll for period: " + period);
        // Implement actual payroll logic here
    }

    public void recordOfflinePaymentEmployee(String empId, double amount, String method, String date, String refNo) {
        System.out.println("Recording employee payment - Emp ID: " + empId + ", Amount: " + amount);
        // Implement actual recording logic
    }

    public void manageDeductions(String empId, double amount, String type) {
        System.out.println("Managing deduction for employee - Emp ID: " + empId + ", Amount: " + amount + ", Type: " + type);
        // Implement actual deduction logic
    }

    public void viewStudentFeeStatus(String studentId) {
        System.out.println("Viewing fee status for student: " + studentId);
        // Implement actual fee status viewing logic
    }

    public void recordOfflinePaymentStudent(String studentId, double amount, String method, String date, String refNo) {
        System.out.println("Recording student payment - Student ID: " + studentId + ", Amount: " + amount);
        // Implement actual recording logic
    }

    public void applyLateFee(List<String> studentIds) {
        System.out.println("Applying late fees to students: " + studentIds);
        // Implement actual late fee logic
    }

    public void manageWaiver(String studentId, double amount, String type, String reason) {
        System.out.println("Managing waiver for student - Student ID: " + studentId + ", Amount: " + amount + ", Type: " + type + ", Reason: " + reason);
        // Implement actual waiver logic
    }
}
*/

public class FinanceManagerGUI extends JFrame {

    private FinanceManager manager;

    // Color scheme (consistent with other GUIs)
    private final Color darkBackground = new Color(18, 18, 18);
    private final Color lighterBackground = new Color(30, 30, 30);
    private final Color accentColor = new Color(0, 150, 255); // A vibrant blue for titles/accents
    private final Color textColor = new Color(240, 240, 240); // Light gray for general text
    private final Color buttonStartColor = new Color(70, 70, 70); // Darker gray for button gradient start
    private final Color buttonEndColor = new Color(50, 50, 50); // Even darker gray for button gradient end
    private final Color successColor = new Color(100, 220, 100); // Green for positive actions
    private final Color warningColor = new Color(255, 165, 0); // Orange for cautionary actions
    private final Color infoColor = new Color(0, 180, 255);    // Light blue for informational actions
    private final Color errorColor = new Color(255, 70, 70); // Red for destructive/close actions

    public FinanceManagerGUI(FinanceManager financeManager) {
        this.manager = financeManager;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Hostel Finance Management");
        setSize(500, 600); // Increased height and width for better spacing
        setLocationRelativeTo(null); // Center the window
        setUndecorated(true); // Remove default frame decorations
        // Set rounded corners for the frame
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window

        // Main panel with custom background and border
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)) { // Increased gaps
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(darkBackground); // Set custom background color
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Fill with rounded corners
            }
        };
        mainPanel.setOpaque(false); // Make it transparent to show the custom painting
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25)); // Increased padding

        // --- Title Bar with Close Button ---
        JPanel titleBarPanel = new JPanel(new BorderLayout());
        titleBarPanel.setOpaque(false); // Make it transparent

        JLabel titleLabel = new JLabel("FINANCE MANAGEMENT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Larger, bolder font
        titleLabel.setForeground(accentColor); // Accent color for the title
        titleBarPanel.add(titleLabel, BorderLayout.CENTER);

        JButton closeButton = new JButton("X");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        closeButton.setForeground(textColor);
        closeButton.setBackground(errorColor); // Red color for close button
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setOpaque(true);
        closeButton.setPreferredSize(new Dimension(40, 40)); // Fixed size
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose()); // Close the frame

        JPanel closeButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closeButtonContainer.setOpaque(false);
        closeButtonContainer.add(closeButton);
        titleBarPanel.add(closeButtonContainer, BorderLayout.EAST);

        mainPanel.add(titleBarPanel, BorderLayout.NORTH);

        // --- Button Panel (Center) ---
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Make it transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Each button takes a new row
        gbc.fill = GridBagConstraints.HORIZONTAL; // Buttons expand horizontally
        gbc.insets = new Insets(8, 0, 8, 0); // Padding between buttons

        // Employee Section Header
        JLabel lblEmployee = new JLabel("EMPLOYEE PAYMENT ACTIONS:");
        lblEmployee.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEmployee.setForeground(textColor.brighter());
        gbc.insets = new Insets(15, 0, 10, 0); // More top padding for section header
        buttonPanel.add(lblEmployee, gbc);
        gbc.insets = new Insets(8, 0, 8, 0); // Reset padding for buttons

        // Employee Buttons
        buttonPanel.add(createGradientButton("Process Payroll", infoColor.brighter(), infoColor.darker(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set custom UIManager properties for JOptionPane
                customizeOptionPaneUI();

                String period = JOptionPane.showInputDialog(FinanceManagerGUI.this,
                        "<html><font color='" + toHex(textColor) + "'>Enter payroll period (e.g., May 2025):</font></html>",
                        "Process Payroll", JOptionPane.PLAIN_MESSAGE);

                // Reset UIManager defaults
                resetOptionPaneUI();

                if (period != null && !period.trim().isEmpty()) {
                    manager.processPayroll(period.trim());
                    showInfoDialog("Payroll processed for: " + period, "Success");
                } else if (period != null) { // User clicked OK but entered empty
                    showErrorDialog("Payroll period cannot be empty.", "Input Error");
                }
            }
        }), gbc);

        buttonPanel.add(createGradientButton("Record Employee Payment", successColor.brighter(), successColor.darker(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField empIdField = new JTextField();
                JTextField amountField = new JTextField();
                JTextField methodField = new JTextField();
                JTextField dateField = new JTextField();
                JTextField refNoField = new JTextField();

                JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // Added gaps
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Employee ID:</font></html>"));
                inputPanel.add(createStyledTextField(empIdField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Amount:</font></html>"));
                inputPanel.add(createStyledTextField(amountField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Payment Method:</font></html>"));
                inputPanel.add(createStyledTextField(methodField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Date (YYYY-MM-DD):</font></html>"));
                inputPanel.add(createStyledTextField(dateField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Reference Number:</font></html>"));
                inputPanel.add(createStyledTextField(refNoField));
                inputPanel.setBackground(lighterBackground);

                customizeOptionPaneUI();
                int result = JOptionPane.showConfirmDialog(FinanceManagerGUI.this, inputPanel, "Record Employee Payment",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                resetOptionPaneUI();

                if (result == JOptionPane.OK_OPTION) {
                    String empId = empIdField.getText().trim();
                    String amountStr = amountField.getText().trim();
                    String method = methodField.getText().trim();
                    String date = dateField.getText().trim();
                    String refNo = refNoField.toString(); // Changed to trim()

                    if (empId.isEmpty() || amountStr.isEmpty() || method.isEmpty() || date.isEmpty() || refNo.isEmpty()) {
                        showErrorDialog("All fields are required.", "Input Error");
                        return;
                    }
                    try {
                        double amount = Double.parseDouble(amountStr);
                        manager.recordOfflinePaymentEmployee(empId, amount, method, date, refNo);
                        showInfoDialog("Recorded payment of " + String.format("%.2f", amount) + " for employee " + empId, "Success");
                    } catch (NumberFormatException ex) {
                        showErrorDialog("Invalid amount. Please enter a numeric value.", "Input Error");
                    }
                }
            }
        }), gbc);

        buttonPanel.add(createGradientButton("Manage Deductions", warningColor.brighter(), warningColor.darker(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField empIdField = new JTextField();
                JTextField amountField = new JTextField();
                JTextField typeField = new JTextField();

                JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Employee ID:</font></html>"));
                inputPanel.add(createStyledTextField(empIdField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Deduction Amount:</font></html>"));
                inputPanel.add(createStyledTextField(amountField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Deduction Type:</font></html>"));
                inputPanel.add(createStyledTextField(typeField));
                inputPanel.setBackground(lighterBackground);

                customizeOptionPaneUI();
                int result = JOptionPane.showConfirmDialog(FinanceManagerGUI.this, inputPanel, "Manage Employee Deductions",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                resetOptionPaneUI();

                if (result == JOptionPane.OK_OPTION) {
                    String empId = empIdField.getText().trim();
                    String amountStr = amountField.getText().trim();
                    String type = typeField.getText().trim();

                    if (empId.isEmpty() || amountStr.isEmpty() || type.isEmpty()) {
                        showErrorDialog("All fields are required.", "Input Error");
                        return;
                    }
                    try {
                        double amount = Double.parseDouble(amountStr);
                        manager.manageDeductions(empId, amount, type);
                        showInfoDialog("Deduction of " + String.format("%.2f", amount) + " applied for employee " + empId, "Success");
                    } catch (NumberFormatException ex) {
                        showErrorDialog("Invalid amount. Please enter a numeric value.", "Input Error");
                    }
                }
            }
        }), gbc);

        // Student Section Header
        JLabel lblStudent = new JLabel("STUDENT FEE ACTIONS:");
        lblStudent.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblStudent.setForeground(textColor.brighter());
        gbc.insets = new Insets(25, 0, 10, 0); // More top padding for section header
        buttonPanel.add(lblStudent, gbc);
        gbc.insets = new Insets(8, 0, 8, 0); // Reset padding for buttons

        // Student Buttons
        buttonPanel.add(createGradientButton("View Fee Status", infoColor.brighter(), infoColor.darker(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customizeOptionPaneUI();
                String studentId = JOptionPane.showInputDialog(FinanceManagerGUI.this,
                        "<html><font color='" + toHex(textColor) + "'>Enter Student ID:</font></html>",
                        "View Fee Status", JOptionPane.PLAIN_MESSAGE);
                resetOptionPaneUI();

                if (studentId != null && !studentId.trim().isEmpty()) {
                    manager.viewStudentFeeStatus(studentId.trim());
                    showInfoDialog("Displayed fee status for: " + studentId, "Information");
                } else if (studentId != null) {
                    showErrorDialog("Student ID cannot be empty.", "Input Error");
                }
            }
        }), gbc);

        buttonPanel.add(createGradientButton("Record Student Payment", successColor.brighter(), successColor.darker(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField studentIdField = new JTextField();
                JTextField amountField = new JTextField();
                JTextField methodField = new JTextField();
                JTextField dateField = new JTextField();
                JTextField refNoField = new JTextField();

                JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Student ID:</font></html>"));
                inputPanel.add(createStyledTextField(studentIdField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Amount:</font></html>"));
                inputPanel.add(createStyledTextField(amountField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Payment Method:</font></html>"));
                inputPanel.add(createStyledTextField(methodField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Date (YYYY-MM-DD):</font></html>"));
                inputPanel.add(createStyledTextField(dateField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Reference Number:</font></html>"));
                inputPanel.add(createStyledTextField(refNoField));
                inputPanel.setBackground(lighterBackground);

                customizeOptionPaneUI();
                int result = JOptionPane.showConfirmDialog(FinanceManagerGUI.this, inputPanel, "Record Student Payment",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                resetOptionPaneUI();

                if (result == JOptionPane.OK_OPTION) {
                    String studentId = studentIdField.getText().trim();
                    String amountStr = amountField.getText().trim();
                    String method = methodField.getText().trim();
                    String date = dateField.getText().trim();
                    String refNo = refNoField.getText().trim();

                    if (studentId.isEmpty() || amountStr.isEmpty() || method.isEmpty() || date.isEmpty() || refNo.isEmpty()) {
                        showErrorDialog("All fields are required.", "Input Error");
                        return;
                    }
                    try {
                        double amount = Double.parseDouble(amountStr);
                        manager.recordOfflinePaymentStudent(studentId, amount, method, date, refNo);
                        showInfoDialog("Recorded payment of " + String.format("%.2f", amount) + " for student " + studentId, "Success");
                    } catch (NumberFormatException ex) {
                        showErrorDialog("Invalid amount. Please enter a numeric value.", "Input Error");
                    }
                }
            }
        }), gbc);

        buttonPanel.add(createGradientButton("Apply Late Fees", warningColor.brighter(), warningColor.darker(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customizeOptionPaneUI();
                String ids = JOptionPane.showInputDialog(FinanceManagerGUI.this,
                        "<html><font color='" + toHex(textColor) + "'>Enter Student IDs separated by commas:</font></html>",
                        "Apply Late Fees", JOptionPane.PLAIN_MESSAGE);
                resetOptionPaneUI();

                if (ids != null && !ids.trim().isEmpty()) {
                    List<String> studentIds = Arrays.asList(ids.split("\\s*,\\s*"));
                    manager.applyLateFee(studentIds);
                    showInfoDialog("Applied late fees to: " + studentIds, "Success");
                } else if (ids != null) {
                    showErrorDialog("Student IDs cannot be empty.", "Input Error");
                }
            }
        }), gbc);

        buttonPanel.add(createGradientButton("Manage Waiver", infoColor.brighter(), infoColor.darker(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField studentIdField = new JTextField();
                JTextField amountField = new JTextField();
                JTextField typeField = new JTextField();
                JTextArea reasonArea = new JTextArea(3, 20);
                JScrollPane scrollPane = new JScrollPane(reasonArea);
                reasonArea.setLineWrap(true);
                reasonArea.setWrapStyleWord(true);

                JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Student ID:</font></html>"));
                inputPanel.add(createStyledTextField(studentIdField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Waiver Amount:</font></html>"));
                inputPanel.add(createStyledTextField(amountField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Waiver Type:</font></html>"));
                inputPanel.add(createStyledTextField(typeField));
                inputPanel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Reason:</font></html>"));
                inputPanel.add(createStyledTextAreaScrollPane(scrollPane, reasonArea)); // Pass the JTextArea as well
                inputPanel.setBackground(lighterBackground);

                customizeOptionPaneUI();
                int result = JOptionPane.showConfirmDialog(FinanceManagerGUI.this, inputPanel, "Manage Waiver",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                resetOptionPaneUI();

                if (result == JOptionPane.OK_OPTION) {
                    String studentId = studentIdField.getText().trim();
                    String amountStr = amountField.getText().trim();
                    String type = typeField.getText().trim();
                    String reason = reasonArea.getText().trim();

                    if (studentId.isEmpty() || amountStr.isEmpty() || type.isEmpty() || reason.isEmpty()) {
                        showErrorDialog("All fields are required.", "Input Error");
                        return;
                    }
                    try {
                        double amount = Double.parseDouble(amountStr);
                        manager.manageWaiver(studentId, amount, type, reason);
                        showInfoDialog("Waiver of " + String.format("%.2f", amount) + " applied for student " + studentId, "Success");
                    } catch (NumberFormatException ex) {
                        showErrorDialog("Invalid amount. Please enter a numeric value.", "Input Error");
                    }
                }
            }
        }), gbc);

        // Add the button panel to the center of the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel); // Add the main panel to the JFrame
    }

    // Helper method to create gradient buttons
    private JButton createGradientButton(String text, Color startColor, Color endColor, ActionListener listener) {
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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // Rounded corners for buttons

                g2.setColor(Color.WHITE); // Text color for buttons
                g2.setFont(getFont().deriveFont(Font.BOLD, 15f)); // Bold and slightly larger font
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), x, y);

                g2.dispose();
            }
        };
        button.setContentAreaFilled(false); // Do not paint default background
        button.setBorderPainted(false); // Do not paint default border
        button.setFocusPainted(false); // Do not paint focus border
        button.setPreferredSize(new Dimension(250, 50)); // Set preferred size for consistent buttons
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        button.addActionListener(listener);
        return button;
    }

    // Helper method to create styled text fields for dialogs
    private JTextField createStyledTextField(JTextField field) {
        field.setBackground(new Color(60, 60, 60)); // Darker background for input fields
        field.setForeground(textColor);
        field.setCaretColor(textColor); // Blinking cursor color
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1), // Subtle border
            BorderFactory.createEmptyBorder(5, 8, 5, 8) // Padding inside text field
        ));
        return field;
    }

    // Helper method to create styled JTextArea in JScrollPane for dialogs
    private JScrollPane createStyledTextAreaScrollPane(JScrollPane scrollPane, JTextArea textArea) {
        textArea.setBackground(new Color(60, 60, 60));
        textArea.setForeground(textColor);
        textArea.setCaretColor(textColor);
        textArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove scroll pane's default border
        scrollPane.getViewport().setBackground(new Color(60, 60, 60)); // Ensure viewport background matches textarea
        return scrollPane;
    }

    // Helper method to set custom UI for OptionPane
    private void customizeOptionPaneUI() {
        UIManager.put("OptionPane.background", lighterBackground);
        UIManager.put("Panel.background", lighterBackground);
        UIManager.put("Button.background", accentColor); // Buttons in dialogs also styled
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Label.foreground", textColor);
        UIManager.put("TextField.background", new Color(60, 60, 60));
        UIManager.put("TextField.foreground", textColor);
        UIManager.put("TextField.caretForeground", textColor);
        UIManager.put("TextArea.background", new Color(60, 60, 60));
        UIManager.put("TextArea.foreground", textColor);
        UIManager.put("TextArea.caretForeground", textColor);
    }

    // Helper method to reset UI for OptionPane
    private void resetOptionPaneUI() {
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Label.foreground", null);
        UIManager.put("TextField.background", null);
        UIManager.put("TextField.foreground", null);
        UIManager.put("TextField.caretForeground", null);
        UIManager.put("TextArea.background", null);
        UIManager.put("TextArea.foreground", null);
        UIManager.put("TextArea.caretForeground", null);
    }

    // Helper to convert Color to Hex string for HTML in JOptionPane
    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    // Standardized info dialog
    private void showInfoDialog(String message, String title) {
        JOptionPane.showMessageDialog(this,
                "<html><font color='" + toHex(textColor) + "'>" + message + "</font></html>",
                title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Standardized error dialog
    private void showErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(this,
                "<html><font color='" + toHex(textColor) + "'>" + message + "</font></html>",
                title, JOptionPane.ERROR_MESSAGE);
    }
}