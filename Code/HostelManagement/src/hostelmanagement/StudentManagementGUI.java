package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementGUI extends JFrame {
    private StudentManager studentManager;
    private DefaultListModel<String> studentListModel;
    private JList<String> studentList;

    // Color scheme (consistent with other GUIs)
    private final Color darkBackground = new Color(18, 18, 18);
    private final Color lighterBackground = new Color(30, 30, 30);
    private final Color accentColor = new Color(0, 150, 255);
    private final Color textColor = new Color(240, 240, 240);
    private final Color successColor = new Color(100, 220, 100); // For Add button
    private final Color warningColor = new Color(255, 165, 0); // For Remove button
    private final Color updateColor = new Color(0, 191, 255); // For Update button
    private final Color errorColor = new Color(255, 70, 70); // For Close button

    public StudentManagementGUI(StudentManager studentManager) {
        this.studentManager = studentManager;
        initializeUI();
        refreshStudentList();
    }

    private void initializeUI() {
        setTitle("Hostel Student Management");
        setSize(500, 450); // Increased size for better visual
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
        JLabel titleLabel = new JLabel("STUDENT MANAGEMENT", SwingConstants.CENTER);
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

        JPanel closeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closeButtonPanel.setOpaque(false);
        closeButtonPanel.add(closeButton);
        titleBarPanel.add(closeButtonPanel, BorderLayout.EAST);

        mainPanel.add(titleBarPanel, BorderLayout.NORTH);

        // Student List Panel
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

        studentListModel = new DefaultListModel<>();
        studentList = new JList<>(studentListModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        studentList.setBackground(new Color(45, 45, 45)); // Darker background for list
        studentList.setForeground(textColor);
        studentList.setFixedCellHeight(35); // Taller cells for better readability
        studentList.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
        studentList.setCellRenderer(new DefaultListCellRenderer() {
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

        JScrollPane scrollPane = new JScrollPane(studentList);
        scrollPane.setBorder(BorderFactory.createLineBorder(lighterBackground.brighter(), 2)); // Border for scroll pane
        listPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(listPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Increased horizontal gap
        buttonPanel.setOpaque(false);

        JButton addButton = createGradientButton("Add Student", successColor.brighter(), successColor.darker(), new AddStudentListener());
        JButton updateButton = createGradientButton("Update Student", updateColor.brighter(), updateColor.darker(), new UpdateStudentListener());
        JButton removeButton = createGradientButton("Remove Selected", warningColor.brighter(), warningColor.darker(), new RemoveStudentListener());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton); // The "Update Student" button is definitely added here.
        buttonPanel.add(removeButton);

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
        // --- CHANGE: Reduced button width further for fitting ---
        button.setPreferredSize(new Dimension(130, 45)); // Reduced width to 130
        // --- END CHANGE ---
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);
        return button;
    }

    private void refreshStudentList() {
        studentListModel.clear();
        for (String student : studentManager.getAllStudents()) {
            studentListModel.addElement(student);
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

    private class AddStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField nameField = new JTextField(20);
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Student Name:</font></html>"));
            panel.add(nameField);
            panel.setBackground(lighterBackground);

            applyOptionPaneUIOverrides();

            int result = JOptionPane.showConfirmDialog(StudentManagementGUI.this,
                    panel,
                    "Add New Student",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            resetOptionPaneUIOverrides();

            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                if (!name.isEmpty()) {
                    if (studentManager.addStudent(name)) {
                        refreshStudentList();
                        showInfoDialog("Student '" + name + "' added successfully.", "Success");
                    } else {
                        showErrorDialog("Student '" + name + "' already exists or invalid name.", "Error");
                    }
                } else {
                    showErrorDialog("Student name cannot be empty.", "Error");
                }
            }
        }
    }

    private class UpdateStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedStudent = studentList.getSelectedValue();
            if (selectedStudent == null) {
                showErrorDialog("Please select a student to update.", "Selection Error");
                return;
            }

            JTextField newNameField = new JTextField(20);
            newNameField.setText(selectedStudent);
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>New Student Name:</font></html>"));
            panel.add(newNameField);
            panel.setBackground(lighterBackground);

            applyOptionPaneUIOverrides();

            int result = JOptionPane.showConfirmDialog(StudentManagementGUI.this,
                    panel,
                    "Update Student: " + selectedStudent,
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            resetOptionPaneUIOverrides();

            if (result == JOptionPane.OK_OPTION) {
                String newName = newNameField.getText().trim();
                if (!newName.isEmpty()) {
                    if (studentManager.updateStudent(selectedStudent, newName)) {
                        refreshStudentList();
                        showInfoDialog("Student '" + selectedStudent + "' updated to '" + newName + "' successfully.", "Success");
                    } else {
                        showErrorDialog("Failed to update student '" + selectedStudent + "'. New name might be a duplicate or invalid.", "Update Error");
                    }
                } else {
                    showErrorDialog("New student name cannot be empty.", "Error");
                }
            }
        }
    }

    private class RemoveStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = studentList.getSelectedValue();
            if (selected == null) {
                showErrorDialog("Please select a student to remove.", "Selection Error");
                return;
            }
            applyOptionPaneUIOverrides();

            int confirm = JOptionPane.showConfirmDialog(StudentManagementGUI.this,
                    "<html><font color='" + toHex(textColor) + "'>Are you sure you want to remove student " + selected + "?</font></html>",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            resetOptionPaneUIOverrides();

            if (confirm == JOptionPane.YES_OPTION) {
                if (studentManager.removeStudent(selected)) {
                    refreshStudentList();
                    showInfoDialog("Student '" + selected + "' removed successfully.", "Success");
                } else {
                    showErrorDialog("Failed to remove student '" + selected + "'.", "Error");
                }
            }
        }
    }

    private void applyOptionPaneUIOverrides() {
        UIManager.put("OptionPane.background", lighterBackground);
        UIManager.put("Panel.background", lighterBackground);
        UIManager.put("Button.background", accentColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Label.foreground", textColor);
        UIManager.put("TextField.background", new Color(60, 60, 60));
        UIManager.put("TextField.foreground", textColor);
        UIManager.put("TextField.caretForeground", textColor);
    }

    private void resetOptionPaneUIOverrides() {
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Label.foreground", null);
        UIManager.put("TextField.background", null);
        UIManager.put("TextField.foreground", null);
        UIManager.put("TextField.caretForeground", null);
    }
}