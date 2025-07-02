package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementGUI extends JFrame {
    private EmployeeManager employeeManager;
    private DefaultListModel<Employee> employeeListModel;
    private JList<Employee> employeeList;

    // Color scheme (consistent with other GUIs)
    private final Color darkBackground = new Color(18, 18, 18);
    private final Color lighterBackground = new Color(30, 30, 30);
    private final Color accentColor = new Color(0, 150, 255);
    private final Color textColor = new Color(240, 240, 240);
    private final Color successColor = new Color(100, 220, 100); // For Add button
    private final Color infoColor = new Color(0, 180, 255); // For Update button
    private final Color warningColor = new Color(255, 165, 0); // For Remove button
    private final Color errorColor = new Color(255, 70, 70); // For Close button

    public EmployeeManagementGUI(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
        initializeUI();
        refreshEmployeeList();
    }

    private void initializeUI() {
        setTitle("Hostel Employee Management");
        setSize(600, 500); // Adjusted size for new button and better layout
        setLocationRelativeTo(null);
        setUndecorated(true); // Undecorated for custom look
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20)); // Rounded corners
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15)) {
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Title Bar with Close Button
        JPanel titleBarPanel = new JPanel(new BorderLayout());
        titleBarPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("EMPLOYEE MANAGEMENT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(accentColor);
        titleBarPanel.add(titleLabel, BorderLayout.CENTER);

        JButton closeButton = new JButton("X");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        closeButton.setForeground(textColor);
        closeButton.setBackground(errorColor);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setOpaque(true);
        closeButton.setPreferredSize(new Dimension(40, 40));
        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose());

        JPanel closeButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closeButtonContainer.setOpaque(false);
        closeButtonContainer.add(closeButton);
        titleBarPanel.add(closeButtonContainer, BorderLayout.EAST);

        mainPanel.add(titleBarPanel, BorderLayout.NORTH);

        // Employee List Panel
        JPanel listPanel = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(lighterBackground);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        listPanel.setOpaque(false);
        listPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        employeeListModel = new DefaultListModel<>();
        employeeList = new JList<>(employeeListModel);
        employeeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        employeeList.setBackground(new Color(45, 45, 45)); // Darker background for list
        employeeList.setForeground(textColor);
        employeeList.setFixedCellHeight(35); // Taller cells for better readability
        employeeList.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
        employeeList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    c.setBackground(accentColor.darker());
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(new Color(45, 45, 45));
                    c.setForeground(textColor);
                }
                setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12)); // Increased padding for list items
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(employeeList);
        scrollPane.setBorder(BorderFactory.createLineBorder(lighterBackground.brighter(), 2)); // Border for scroll pane
        listPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(listPanel, BorderLayout.CENTER);

        // Button Panel - This is where the buttons are added
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Increased horizontal gap
        buttonPanel.setOpaque(false);

        JButton addButton = createGradientButton("Add Employee", successColor.brighter(), successColor.darker(), new AddEmployeeListener());
        JButton removeButton = createGradientButton("Remove Selected", warningColor.brighter(), warningColor.darker(), new RemoveEmployeeListener());
        // Here is the "Update Selected" button!
        JButton updateButton = createGradientButton("Update Selected", infoColor.brighter(), infoColor.darker(), new UpdateEmployeeListener()); 

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton); // And here it's added to the panel

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // Rounded buttons

                g2.setColor(Color.WHITE);
                g2.setFont(getFont().deriveFont(Font.BOLD, 15)); // Slightly larger font for buttons
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
        button.setPreferredSize(new Dimension(150, 40)); // Reduced button size
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);
        return button;
    }

    private void refreshEmployeeList() {
        employeeListModel.clear();
        for (Employee emp : employeeManager.getAllEmployees()) {
            employeeListModel.addElement(emp);
        }
    }

    // Helper to convert Color to Hex string for HTML in JOptionPane
    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private void showInfoDialog(String message, String title) {
        JOptionPane.showMessageDialog(this,
                "<html><font color='" + toHex(textColor) + "'>" + message + "</font></html>",
                title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(this,
                "<html><font color='" + toHex(textColor) + "'>" + message + "</font></html>",
                title, JOptionPane.ERROR_MESSAGE);
    }

    private class AddEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField nameField = new JTextField(20);
            JTextField emailField = new JTextField(20);
            JTextField phoneField = new JTextField(15);
            JTextField experienceField = new JTextField(10);

            JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); // Grid layout with gaps
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Name:</font></html>"));
            panel.add(createStyledTextField(nameField));
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Email:</font></html>"));
            panel.add(createStyledTextField(emailField));
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Phone:</font></html>"));
            panel.add(createStyledTextField(phoneField));
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Experience:</font></html>"));
            panel.add(createStyledTextField(experienceField));
            panel.setBackground(lighterBackground); // Set panel background

            // Set custom UIManager properties for JOptionPane
            customizeOptionPaneUI();

            int result = JOptionPane.showConfirmDialog(EmployeeManagementGUI.this, panel,
                    "Add New Employee", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            // Reset UIManager defaults
            resetOptionPaneUI();

            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String experience = experienceField.getText().trim();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || experience.isEmpty()) {
                    showErrorDialog("All fields are required.", "Input Error");
                    return;
                }

                Employee newEmployee = new Employee(name, email, phone, experience);
                if (employeeManager.addEmployee(newEmployee)) {
                    refreshEmployeeList();
                    showInfoDialog("Employee added successfully!", "Success");
                } else {
                    showErrorDialog("Employee with this name already exists. Please use a unique name.", "Add Employee Failed");
                }
            }
        }
    }

    private class RemoveEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Employee selected = employeeList.getSelectedValue();
            if (selected == null) {
                showErrorDialog("Please select an employee to remove.", "Selection Error");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(EmployeeManagementGUI.this,
                    "<html><font color='" + toHex(textColor) + "'>Are you sure you want to remove employee " + selected.getName() + "?</font></html>",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                if (employeeManager.removeEmployee(selected)) {
                    refreshEmployeeList();
                    showInfoDialog("Employee removed successfully!", "Success");
                } else {
                    showErrorDialog("Failed to remove employee.", "Error");
                }
            }
        }
    }

    private class UpdateEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Employee selectedEmployee = employeeList.getSelectedValue();
            if (selectedEmployee == null) {
                showErrorDialog("Please select an employee to update.", "Selection Error");
                return;
            }

            // Populate fields with current values for editing
            JTextField nameField = new JTextField(selectedEmployee.getName(), 20);
            JTextField emailField = new JTextField(selectedEmployee.getEmail(), 20);
            JTextField phoneField = new JTextField(selectedEmployee.getPhone(), 15);
            JTextField experienceField = new JTextField(selectedEmployee.getExperience(), 10);

            JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Name:</font></html>"));
            panel.add(createStyledTextField(nameField));
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Email:</font></html>"));
            panel.add(createStyledTextField(emailField));
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Phone:</font></html>"));
            panel.add(createStyledTextField(phoneField));
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Experience:</font></html>"));
            panel.add(createStyledTextField(experienceField));
            panel.setBackground(lighterBackground);

            // Set custom UIManager properties for JOptionPane
            customizeOptionPaneUI();

            int result = JOptionPane.showConfirmDialog(EmployeeManagementGUI.this, panel,
                    "Update Employee Details for " + selectedEmployee.getName(),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            // Reset UIManager defaults
            resetOptionPaneUI();

            if (result == JOptionPane.OK_OPTION) {
                String newName = nameField.getText().trim();
                String newEmail = emailField.getText().trim();
                String newPhone = phoneField.getText().trim();
                String newExperience = experienceField.getText().trim();

                if (newName.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty() || newExperience.isEmpty()) {
                    showErrorDialog("All fields are required.", "Input Error");
                    return;
                }

                if (employeeManager.updateEmployee(selectedEmployee.getName(), newName, newEmail, newPhone, newExperience)) {
                    refreshEmployeeList();
                    showInfoDialog("Employee details updated successfully!", "Success");
                } else {
                    showErrorDialog("Failed to update employee. Check if the new name already exists for a different employee.", "Update Failed");
                }
            }
        }
    }

    // Helper method to create styled text fields for dialogs
    private JTextField createStyledTextField(JTextField field) {
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(textColor);
        field.setCaretColor(textColor); // Blinking cursor color
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8) // Padding inside text field
        ));
        return field;
    }

    // Helper method to set custom UI for OptionPane
    private void customizeOptionPaneUI() {
        UIManager.put("OptionPane.background", lighterBackground);
        UIManager.put("Panel.background", lighterBackground);
        UIManager.put("Button.background", accentColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Label.foreground", textColor);
    }

    // Helper method to reset UI for OptionPane
    private void resetOptionPaneUI() {
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Label.foreground", null);
    }
}
