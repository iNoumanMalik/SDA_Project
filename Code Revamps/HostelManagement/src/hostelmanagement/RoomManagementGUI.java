package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

// Assuming Room.java and RoomManager.java are in the same package and are correct.


class Room {
    private String roomNumber;
    private int capacity;
    private int currentOccupancy;
    private String status; // "available", "occupied", "maintenance"

    public Room(String roomNumber, int capacity) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.currentOccupancy = 0;
        this.status = "available";
    }

    // Getters and setters
    public String getRoomNumber() { return roomNumber; }
    public int getCapacity() { return capacity; }
    public int getCurrentOccupancy() { return currentOccupancy; }
    public String getStatus() { return status; }

    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setCurrentOccupancy(int currentOccupancy) { this.currentOccupancy = currentOccupancy; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + status + ") - " + currentOccupancy + "/" + capacity;
    }
}

class RoomManager {
    private List<Room> rooms;

    public RoomManager() {
        this.rooms = new ArrayList<>();
        // Initialize with some sample rooms
        rooms.add(new Room("101", 2));
        rooms.add(new Room("102", 3));
        rooms.add(new Room("201", 4));
        rooms.add(new Room("202", 2));
    }

    public void addRoom(String roomNumber, int capacity) {
        // Prevent adding duplicate room numbers
        if (getRoom(roomNumber) == null) {
            rooms.add(new Room(roomNumber, capacity));
        } else {
            throw new IllegalArgumentException("Room number " + roomNumber + " already exists.");
        }
    }

    public boolean removeRoom(String roomNumber) {
        return rooms.removeIf(room -> room.getRoomNumber().equals(roomNumber));
    }

    public void updateRoomStatus(String roomNumber, String newStatus) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                room.setStatus(newStatus);
                break;
            }
        }
    }

    public void updateRoomCapacity(String roomNumber, int newCapacity) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                room.setCapacity(newCapacity);
                break;
            }
        }
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    public Room getRoom(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }
}


public class RoomManagementGUI extends JFrame {
    private RoomManager roomManager;
    private DefaultListModel<String> roomListModel;
    private JList<String> roomList;
    private JTextArea detailsTextArea;

    // Color scheme (consistent with StudentLivingGUI and LoginGUI)
    private final Color darkBackground = new Color(18, 18, 18);
    private final Color lighterBackground = new Color(30, 30, 30);
    private final Color accentColor = new Color(0, 150, 255);
    private final Color textColor = new Color(240, 240, 240);
    private final Color successColor = new Color(100, 220, 100);
    private final Color infoColor = new Color(0, 180, 255);
    private final Color warningColor = new Color(255, 165, 0); // Orange for remove
    private final Color errorColor = new Color(255, 70, 70); // Red for close

    public RoomManagementGUI(RoomManager roomManager) {
        this.roomManager = roomManager;
        initializeUI();
        refreshRoomList();
    }

    private void initializeUI() {
        setTitle("Hostel Room Management System");
        setSize(800, 600); // Increased size for better layout
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Changed to EXIT_ON_CLOSE for main window behavior
        setUndecorated(true); // Undecorated for custom look
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20)); // Rounded corners

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

        // Title and Close Button
        JPanel titleBarPanel = new JPanel(new BorderLayout());
        titleBarPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("ROOM MANAGEMENT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
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
        closeButton.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose()); // Close the window
        
        JPanel closeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closeButtonPanel.setOpaque(false);
        closeButtonPanel.add(closeButton);
        titleBarPanel.add(closeButtonPanel, BorderLayout.EAST);

        mainPanel.add(titleBarPanel, BorderLayout.NORTH);

        // Content panel (list on left, controls/details on right)
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Room list on the left
        roomListModel = new DefaultListModel<>();
        roomList = new JList<>(roomListModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roomList.setBackground(new Color(45, 45, 45));
        roomList.setForeground(textColor);
        roomList.setFixedCellHeight(30);
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
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return c;
            }
        });
        JScrollPane listScrollPane = new JScrollPane(roomList);
        listScrollPane.setPreferredSize(new Dimension(250, 0)); // Wider list
        listScrollPane.setBorder(BorderFactory.createLineBorder(lighterBackground.brighter(), 2));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        contentPanel.add(listScrollPane, gbc);

        // Right panel for controls and details
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Inner padding
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.gridwidth = GridBagConstraints.REMAINDER;
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightGbc.insets = new Insets(8, 0, 8, 0); // Spacing between components

        // Control buttons
        rightPanel.add(createGradientButton("Add New Room", successColor.brighter(), successColor.darker(), new AddRoomListener()), rightGbc);
        rightPanel.add(createGradientButton("Remove Selected Room", warningColor.brighter(), warningColor.darker(), new RemoveRoomListener()), rightGbc);
        rightPanel.add(createGradientButton("Update Room Status", infoColor.brighter(), infoColor.darker(), new UpdateStatusListener()), rightGbc);
        rightPanel.add(createGradientButton("Update Room Capacity", infoColor.brighter(), infoColor.darker(), new UpdateCapacityListener()), rightGbc);

        // Spacer to push details to bottom
        rightGbc.weighty = 1.0;
        rightPanel.add(Box.createVerticalGlue(), rightGbc);

        // Room Details Panel
        JPanel detailsPanel = new JPanel(new BorderLayout(5, 5));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(accentColor, 1),
            "Selected Room Details",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            accentColor
        ));
        
        detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsTextArea.setFont(new Font("Segoe UI Mono", Font.PLAIN, 13));
        detailsTextArea.setForeground(textColor);
        detailsTextArea.setBackground(new Color(40, 40, 40));
        detailsTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane detailsScrollPane = new JScrollPane(detailsTextArea);
        detailsScrollPane.setPreferredSize(new Dimension(300, 150)); // Fixed height for details
        detailsScrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
        detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);

        rightGbc.gridx = 0;
        rightGbc.gridy = GridBagConstraints.RELATIVE; // Place below buttons
        rightGbc.weighty = 0.5; // Give some weight to details panel
        rightGbc.fill = GridBagConstraints.BOTH;
        rightGbc.insets = new Insets(15, 0, 0, 0); // Top margin for details panel
        rightPanel.add(detailsPanel, rightGbc);


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        contentPanel.add(rightPanel, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Add selection listener to show details
        roomList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateDetailsArea();
            }
        });
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
        button.setPreferredSize(new Dimension(200, 45)); // Consistent button size
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);
        return button;
    }

    private void refreshRoomList() {
        roomListModel.clear();
        for (Room room : roomManager.getAllRooms()) {
            roomListModel.addElement(room.toString());
        }
        updateDetailsArea(); // Update details area when list refreshes
    }

    private void updateDetailsArea() {
        String selected = roomList.getSelectedValue();
        if (selected != null) {
            // Extract room number from string like "Room 101 (available) - 0/2"
            String roomNumber = selected.split(" ")[1]; 
            Room room = roomManager.getRoom(roomNumber);
            if (room != null) {
                detailsTextArea.setText(
                    "Room Number: " + room.getRoomNumber() + "\n" +
                    "Capacity: " + room.getCapacity() + "\n" +
                    "Current Occupancy: " + room.getCurrentOccupancy() + "\n" +
                    "Status: " + room.getStatus().toUpperCase()
                );
            } else {
                detailsTextArea.setText("Details not found for selected room.");
            }
        } else {
            detailsTextArea.setText("Select a room from the list to see its details.");
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

    // Action Listeners
    private class AddRoomListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField roomNumberField = new JTextField(10);
            JTextField capacityField = new JTextField(5);

            JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Room Number:</font></html>"));
            panel.add(roomNumberField);
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>Capacity:</font></html>"));
            panel.add(capacityField);
            panel.setBackground(lighterBackground); // Set panel background

            // Custom JOptionPane for consistent look
            UIManager.put("OptionPane.background", lighterBackground);
            UIManager.put("Panel.background", lighterBackground);
            UIManager.put("Button.background", accentColor);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Label.foreground", textColor);
            UIManager.put("TextField.background", new Color(60,60,60));
            UIManager.put("TextField.foreground", textColor);
            UIManager.put("TextField.caretForeground", textColor);


            int result = JOptionPane.showConfirmDialog(
                RoomManagementGUI.this,
                panel,
                "Add New Room",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            // Reset UIManager defaults if necessary, or manage a custom UI for dialogs
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);
            UIManager.put("Label.foreground", null);
            UIManager.put("TextField.background", null);
            UIManager.put("TextField.foreground", null);
            UIManager.put("TextField.caretForeground", null);


            if (result == JOptionPane.OK_OPTION) {
                try {
                    String roomNumber = roomNumberField.getText().trim();
                    int capacity = Integer.parseInt(capacityField.getText().trim());
                    
                    if (roomNumber.isEmpty()) {
                        throw new IllegalArgumentException("Room number cannot be empty.");
                    }
                    if (capacity <= 0) {
                        throw new IllegalArgumentException("Capacity must be positive.");
                    }
                    
                    roomManager.addRoom(roomNumber, capacity);
                    refreshRoomList();
                    showInfoDialog("Room " + roomNumber + " added successfully.", "Success");
                } catch (NumberFormatException ex) {
                    showErrorDialog("Please enter a valid number for capacity.", "Input Error");
                } catch (IllegalArgumentException ex) {
                    showErrorDialog(ex.getMessage(), "Error");
                }
            }
        }
    }

    private class RemoveRoomListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = roomList.getSelectedValue();
            if (selected == null) {
                showErrorDialog("Please select a room to remove.", "Selection Error");
                return;
            }

            String roomNumber = selected.split(" ")[1];
            int confirm = JOptionPane.showConfirmDialog(
                RoomManagementGUI.this,
                "<html><font color='" + toHex(textColor) + "'>Are you sure you want to remove room " + roomNumber + "?</font></html>",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (roomManager.removeRoom(roomNumber)) {
                    refreshRoomList();
                    showInfoDialog("Room " + roomNumber + " removed successfully.", "Success");
                } else {
                    showErrorDialog("Failed to remove room " + roomNumber + ". It might not exist.", "Error");
                }
            }
        }
    }

    private class UpdateStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = roomList.getSelectedValue();
            if (selected == null) {
                showErrorDialog("Please select a room to update its status.", "Selection Error");
                return;
            }

            String roomNumber = selected.split(" ")[1];
            String[] statusOptions = {"available", "occupied", "maintenance"};
            String newStatus = (String) JOptionPane.showInputDialog(
                RoomManagementGUI.this,
                "<html><font color='" + toHex(textColor) + "'>Select new status for room " + roomNumber + ":</font></html>",
                "Update Room Status",
                JOptionPane.PLAIN_MESSAGE,
                null,
                statusOptions,
                roomManager.getRoom(roomNumber).getStatus() // Pre-select current status
            );

            if (newStatus != null) {
                roomManager.updateRoomStatus(roomNumber, newStatus);
                refreshRoomList();
                showInfoDialog("Status of room " + roomNumber + " updated to " + newStatus + ".", "Status Updated");
            }
        }
    }

    private class UpdateCapacityListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = roomList.getSelectedValue();
            if (selected == null) {
                showErrorDialog("Please select a room to update its capacity.", "Selection Error");
                return;
            }

            String roomNumber = selected.split(" ")[1];
            JTextField capacityField = new JTextField(String.valueOf(roomManager.getRoom(roomNumber).getCapacity()), 5); // Pre-fill current capacity

            JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>New Capacity:</font></html>"));
            panel.add(capacityField);
            panel.setBackground(lighterBackground); // Set panel background

            // Custom JOptionPane for consistent look
            UIManager.put("OptionPane.background", lighterBackground);
            UIManager.put("Panel.background", lighterBackground);
            UIManager.put("Button.background", accentColor);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Label.foreground", textColor);
            UIManager.put("TextField.background", new Color(60,60,60));
            UIManager.put("TextField.foreground", textColor);
            UIManager.put("TextField.caretForeground", textColor);

            int result = JOptionPane.showConfirmDialog(
                RoomManagementGUI.this,
                panel,
                "Update Capacity for Room " + roomNumber,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            // Reset UIManager defaults if necessary
            UIManager.put("OptionPane.background", null);
            UIManager.put("Panel.background", null);
            UIManager.put("Button.background", null);
            UIManager.put("Button.foreground", null);
            UIManager.put("Label.foreground", null);
            UIManager.put("TextField.background", null);
            UIManager.put("TextField.foreground", null);
            UIManager.put("TextField.caretForeground", null);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    int newCapacity = Integer.parseInt(capacityField.getText().trim());
                    if (newCapacity <= 0) {
                        throw new IllegalArgumentException("Capacity must be positive.");
                    }
                    // Optional: Check if new capacity is less than current occupancy
                    Room room = roomManager.getRoom(roomNumber);
                    if (room != null && newCapacity < room.getCurrentOccupancy()) {
                        throw new IllegalArgumentException("New capacity cannot be less than current occupancy (" + room.getCurrentOccupancy() + ").");
                    }

                    roomManager.updateRoomCapacity(roomNumber, newCapacity);
                    refreshRoomList();
                    showInfoDialog("Capacity of room " + roomNumber + " updated to " + newCapacity + ".", "Capacity Updated");
                } catch (NumberFormatException ex) {
                    showErrorDialog("Please enter a valid number for capacity.", "Input Error");
                } catch (IllegalArgumentException ex) {
                    showErrorDialog(ex.getMessage(), "Error");
                }
            }
        }
    }
}