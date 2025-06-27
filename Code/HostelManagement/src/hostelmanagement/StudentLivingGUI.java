package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class StudentLivingGUI extends JFrame {
    private StudentManager studentManager;
    private RoomManager roomManager;

    // Color scheme (from LoginGUI for consistency)
    private final Color darkBackground = new Color(18, 18, 18);
    private final Color lighterBackground = new Color(30, 30, 30);
    private final Color accentColor = new Color(0, 150, 255);
    private final Color textColor = new Color(240, 240, 240);
    private final Color successColor = new Color(100, 220, 100);
    private final Color infoColor = new Color(0, 180, 255);

    private JComboBox<String> studentComboBox;
    private JList<String> roomList;
    private DefaultListModel<String> roomListModel;
    private JTextArea outputArea;
    private JButton applyBtn;
    private JButton showDetailsBtn;

    public StudentLivingGUI(StudentManager studentManager, RoomManager roomManager) {
        this.studentManager = studentManager;
        this.roomManager = roomManager;

        setTitle("Hostel Management System - Student Accommodation");
        setSize(700, 600); // Slightly larger for more content
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true); // Undecorated like LoginGUI
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20)); // Rounded corners

        initializeUI();
    }

    private void initializeUI() {
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

        // Title
        JLabel titleLabel = new JLabel("STUDENT ACCOMMODATION", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(accentColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Content Panel (for selection, room list, and buttons)
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

        // Student selection
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

        // Available Rooms List
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 1.0; // Allow list to expand vertically
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(15, 0, 15, 0);

        roomListModel = new DefaultListModel<>();
        roomList = new JList<>(roomListModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roomList.setBackground(new Color(45, 45, 45)); // Darker background for list
        roomList.setForeground(textColor);
        roomList.setFixedCellHeight(30); // Make list items taller
        roomList.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
        roomList.setCellRenderer(new DefaultListCellRenderer() {
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
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding for list items
                return c;
            }
        });

        JScrollPane listScrollPane = new JScrollPane(roomList);
        contentPanel.add(listScrollPane, gbc);
        refreshRoomList(); // Populate after setting up renderer

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setOpaque(false);
        applyBtn = createGradientButton("Apply for Room", successColor.brighter(), successColor.darker());
        showDetailsBtn = createGradientButton("Room Details", infoColor.brighter(), infoColor.darker());

        applyBtn.addActionListener(e -> applyForRoom());
        showDetailsBtn.addActionListener(e -> showRoomDetails());

        buttonPanel.add(applyBtn);
        buttonPanel.add(showDetailsBtn);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 0; // Reset weighty
        gbc.insets = new Insets(20, 0, 10, 0);
        contentPanel.add(buttonPanel, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Segoe UI Mono", Font.PLAIN, 12));
        outputArea.setForeground(textColor);
        outputArea.setBackground(new Color(40, 40, 40));
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setPreferredSize(new Dimension(outputArea.getWidth(), 150)); // Fixed height for output
        outputScrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));

        mainPanel.add(outputScrollPane, BorderLayout.SOUTH);

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
        ((JLabel)comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);
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
        button.setPreferredSize(new Dimension(160, 45));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void refreshStudentList() {
        studentComboBox.removeAllItems();
        // Assuming studentManager.getAllStudents() returns a List<String> of student names
        for (String student : studentManager.getAllStudents()) {
            studentComboBox.addItem(student);
        }
        if (studentComboBox.getItemCount() > 0) {
            studentComboBox.setSelectedIndex(0);
        }
    }

    private void refreshRoomList() {
        roomListModel.clear();
        for (Room room : roomManager.getAllRooms()) {
            if (room.getStatus().equals("available")) {
                roomListModel.addElement("Room " + room.getRoomNumber() + " (Capacity: " + room.getCapacity() + ")");
            }
        }
    }

    private void applyForRoom() {
        String selectedStudent = (String) studentComboBox.getSelectedItem();
        String selectedRoomString = roomList.getSelectedValue();

        if (selectedStudent == null) {
            showErrorDialog("Please select a student first.", "Selection Error");
            return;
        }
        if (selectedRoomString == null) {
            showErrorDialog("Please select an available room from the list.", "Selection Error");
            return;
        }

        // Extract room number from the selected string (e.g., "Room 101 (Capacity: 2)")
        String roomNumber = selectedRoomString.split(" ")[1];
        Room room = roomManager.getRoom(roomNumber);

        if (room != null && room.getStatus().equals("available")) {
            outputArea.append("\n--- Initiating Application for " + selectedStudent + " for " + selectedRoomString + " ---\n");
            outputArea.append("Submitting application...\n");
            // Simulate an asynchronous approval process
            Timer timer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    approveApplication(selectedStudent, room);
                    ((Timer) e.getSource()).stop();
                }
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            showErrorDialog("Selected room is not available or does not exist.", "Room Unavailable");
            outputArea.append("Error: Room " + roomNumber + " is not available.\n");
        }
    }

    private void approveApplication(String studentName, Room room) {
        outputArea.append("Application for " + studentName + " approved for room " + room.getRoomNumber() + ".\n");
        signContract(studentName, room);
    }

    private void signContract(String studentName, Room room) {
        outputArea.append("Requesting " + studentName + " to sign the lease agreement for room " + room.getRoomNumber() + "...\n");
        // In a real system, this would involve a confirmation dialog or document signing
        int choice = JOptionPane.showConfirmDialog(this,
                "<html><font color='" + toHex(textColor) + "'>Confirm " + studentName + " has signed the lease for Room " + room.getRoomNumber() + "?</font></html>",
                "Sign Lease Agreement", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            outputArea.append(studentName + " has successfully signed the lease agreement.\n");
            processPayment(studentName, room);
        } else {
            outputArea.append("Lease signing cancelled. Room assignment for " + studentName + " not completed.\n");
            showErrorDialog("Lease signing was cancelled. Room not assigned.", "Process Cancelled");
        }
    }

    private void processPayment(String studentName, Room room) {
        outputArea.append("Processing initial payment for room " + room.getRoomNumber() + "...\n");
        // Simulate payment collection
        double roomFee = 500.00; // Example fee
        String paymentAmountStr = JOptionPane.showInputDialog(this,
                "<html><font color='" + toHex(textColor) + "'>Enter payment amount for Room " + room.getRoomNumber() + " (e.g., $" + String.format("%.2f", roomFee) + "):</font></html>",
                "Collect Payment",
                JOptionPane.PLAIN_MESSAGE);

        if (paymentAmountStr == null || paymentAmountStr.trim().isEmpty()) {
            outputArea.append("Payment cancelled or no amount entered. Room not assigned.\n");
            showErrorDialog("Payment not received. Room assignment failed.", "Payment Failed");
            return;
        }

        try {
            double payment = Double.parseDouble(paymentAmountStr);
            if (payment >= roomFee) { // Simple check, could be more complex
                outputArea.append("Payment of $" + String.format("%.2f", payment) + " received for room " + room.getRoomNumber() + ".\n");
                room.setStatus("occupied");
                roomManager.updateRoomStatus(room.getRoomNumber(), "occupied"); // Update the room in RoomManager
                // Also, assign the student to the room in StudentManager or similar logic
                // studentManager.assignRoomToStudent(studentName, room.getRoomNumber());

                notifyMoveIn(studentName, room);
                refreshRoomList(); // Update list to reflect occupied room
                JOptionPane.showMessageDialog(this,
                        "<html><font color='" + toHex(textColor) + "'>Congratulations! " + studentName + " has successfully secured Room " + room.getRoomNumber() + ".</font></html>",
                        "Room Assigned!", JOptionPane.INFORMATION_MESSAGE);
            } else {
                outputArea.append("Insufficient payment. Required: $" + String.format("%.2f", roomFee) + ", Received: $" + String.format("%.2f", payment) + ".\n");
                showErrorDialog("Payment is insufficient. Room not assigned.", "Payment Error");
            }
        } catch (NumberFormatException e) {
            outputArea.append("Invalid payment amount entered. Room not assigned.\n");
            showErrorDialog("Invalid input for payment. Please enter a number.", "Input Error");
        }
    }

    private void notifyMoveIn(String studentName, Room room) {
        outputArea.append("Move-in date confirmed. Welcome to your new room " +
                room.getRoomNumber() + ", " + studentName + "!\n");
        outputArea.append("--- Process Complete ---\n");
    }

    private void showRoomDetails() {
        String selectedRoomString = roomList.getSelectedValue();
        if (selectedRoomString == null) {
            outputArea.append("Please select a room from the list to view details.\n");
            showErrorDialog("No room selected.", "Selection Error");
            return;
        }

        String roomNumber = selectedRoomString.split(" ")[1];
        Room room = roomManager.getRoom(roomNumber);

        if (room != null) {
            outputArea.append("\n--- Details for Room " + room.getRoomNumber() + " ---\n");
            outputArea.append("  Number: " + room.getRoomNumber() + "\n");
            outputArea.append("  Capacity: " + room.getCapacity() + " students\n");
            outputArea.append("  Status: " + room.getStatus().toUpperCase() + "\n");
            // Add more details if Room class has them, e.g., features, current occupants
            // For example:
            // outputArea.append("  Features: " + room.getFeatures() + "\n");
            // outputArea.append("  Current Occupants: " + room.getCurrentOccupants() + "\n");
        } else {
            outputArea.append("Error: Details for selected room could not be retrieved.\n");
        }
    }

    // Helper to convert Color to Hex string for HTML in JOptionPane
    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private void showErrorDialog(String no_room_selected, String selection_Error) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}