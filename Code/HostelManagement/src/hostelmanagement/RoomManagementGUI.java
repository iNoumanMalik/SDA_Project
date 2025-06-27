package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

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

class RoomManager implements RoomSubject{
    private List<Room> rooms;
    private List<RoomObserver> observers = new ArrayList<>();  // List of observers

    public RoomManager() {
        this.rooms = new ArrayList<>();
        rooms.add(new Room("101", 2));
        rooms.add(new Room("102", 3));
        rooms.add(new Room("201", 4));
        rooms.add(new Room("202", 2));
    }
    
    @Override
    public void registerObserver(RoomObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(RoomObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (RoomObserver observer : observers) {
            for (Room room : rooms) {
                observer.update(room);
            }
        }
    }

    public void addRoom(String roomNumber, int capacity) {
        if (getRoom(roomNumber) == null) {
            rooms.add(new Room(roomNumber, capacity));
        } else {
            throw new IllegalArgumentException("Room number " + roomNumber + " already exists.");
        }
    }

    public boolean removeRoom(String roomNumber) {
        return rooms.removeIf(room -> room.getRoomNumber().equals(roomNumber));
    }

       // Modify existing methods to notify observers
    public void updateRoomStatus(String roomNumber, String newStatus) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                room.setStatus(newStatus);
                notifyObservers();  // Notify when status changes
                break;
            }
        }
    }
    
    public void updateRoomCapacity(String roomNumber, int newCapacity) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                room.setCapacity(newCapacity);
                notifyObservers();  // Notify when capacity changes
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

public class RoomManagementGUI extends JFrame implements RoomObserver{
    private RoomManager roomManager;
    private DefaultListModel<String> roomListModel;
    private JList<String> roomList;
    private JTextArea detailsTextArea;

    private final Color darkBackground = new Color(18, 18, 18);
    private final Color lighterBackground = new Color(30, 30, 30);
    private final Color accentColor = new Color(0, 150, 255);
    private final Color textColor = new Color(240, 240, 240);
    private final Color successColor = new Color(100, 220, 100);
    private final Color infoColor = new Color(0, 180, 255);
    private final Color warningColor = new Color(255, 165, 0);
    private final Color errorColor = new Color(255, 70, 70);

 
    public RoomManagementGUI(RoomManager roomManager) {
        this.roomManager = roomManager;
        initializeUI();
        refreshRoomList();
    }
    
    public void update(Room room) {
        // This method will be called when any room changes
        SwingUtilities.invokeLater(() -> {
            refreshRoomList();
            updateDetailsArea();
        });
    }

    private void initializeUI() {
        setTitle("Hostel Room Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

        // Main Panel with proper layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(darkBackground);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title Bar with proper close button
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setOpaque(false);
        
        JLabel title = new JLabel("Room Management", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(accentColor);
        titleBar.add(title, BorderLayout.CENTER);

        JButton closeBtn = new JButton("×");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        closeBtn.setForeground(textColor);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dispose());
        titleBar.add(closeBtn, BorderLayout.EAST);

        mainPanel.add(titleBar, BorderLayout.NORTH);

        // Content Area
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        // Room List - Left Side
        roomListModel = new DefaultListModel<>();
        roomList = new JList<>(roomListModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roomList.setBackground(new Color(40, 40, 50));
        roomList.setForeground(textColor);
        roomList.setFixedCellHeight(35);
        roomList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane listScroll = new JScrollPane(roomList);
        listScroll.setPreferredSize(new Dimension(250, 0));
        listScroll.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 80)));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        contentPanel.add(listScroll, gbc);

        // Right Panel - Controls and Details
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setOpaque(false);

        // Control Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        buttonPanel.add(createButton("Add Room", successColor));
        buttonPanel.add(createButton("Remove Room", warningColor));
        buttonPanel.add(createButton("Update Status", infoColor));
        buttonPanel.add(createButton("Update Capacity", infoColor));

        rightPanel.add(buttonPanel, BorderLayout.NORTH);

        // Details Panel - Now properly sized
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(accentColor, 1),
            "Room Details",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            accentColor
        ));

        detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        detailsTextArea.setLineWrap(true);
        detailsTextArea.setWrapStyleWord(true);
        detailsTextArea.setForeground(textColor);
        detailsTextArea.setBackground(new Color(45, 45, 55));
        detailsTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane detailsScroll = new JScrollPane(detailsTextArea);
        detailsScroll.setPreferredSize(new Dimension(350, 200));
        detailsScroll.setBorder(null);
        detailsPanel.add(detailsScroll, BorderLayout.CENTER);

        rightPanel.add(detailsPanel, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        contentPanel.add(rightPanel, gbc);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Center window properly
        setLocationRelativeTo(null);
        setVisible(true);

        // Add action listeners
        roomList.addListSelectionListener(e -> updateDetailsArea());
    }

    private JButton createButton(String text, Color baseColor) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Gradient colors based on button type
            Color startColor, endColor;
            if (text.equals("Add Room")) {
                startColor = new Color(100, 220, 100); // Bright green
                endColor = new Color(60, 160, 60);     // Darker green
            } else if (text.equals("Remove Room")) {
                startColor = new Color(255, 120, 90);  // Coral
                endColor = new Color(220, 70, 60);     // Darker red
            } else { // Update buttons
                startColor = new Color(70, 170, 255);  // Bright blue
                endColor = new Color(40, 120, 220);    // Darker blue
            }

            // Hover effect
            if (getModel().isRollover()) {
                startColor = startColor.brighter();
                endColor = endColor.brighter();
            }

            // Pressed effect
            if (getModel().isPressed()) {
                startColor = startColor.darker();
                endColor = endColor.darker();
            }

            // Paint gradient background
            GradientPaint gp = new GradientPaint(
                0, 0, startColor,
                getWidth(), getHeight(), endColor
            );
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

            // Draw text
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
    button.setPreferredSize(new Dimension(150, 40));
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    
    // Add appropriate action listeners
    if (text.equals("Add Room")) {
        button.addActionListener(new AddRoomListener());
    } else if (text.equals("Remove Room")) {
        button.addActionListener(new RemoveRoomListener());
    } else if (text.equals("Update Status")) {
        button.addActionListener(new UpdateStatusListener());
    } else if (text.equals("Update Capacity")) {
        button.addActionListener(new UpdateCapacityListener());
    }
    
    return button;
}

    private JButton createModernButton(String text, Color color, ActionListener listener) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bgColor = color;
                if (getModel().isPressed()) {
                    bgColor = color.darker();
                } else if (getModel().isRollover()) {
                    bgColor = color.brighter();
                }

                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                g2.setColor(Color.WHITE);
                g2.setFont(getFont().deriveFont(Font.BOLD, 16));
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
        button.setPreferredSize(new Dimension(250, 55));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        button.addActionListener(listener);
        return button;
    }

    private void refreshRoomList() {
        roomListModel.clear();
        for (Room room : roomManager.getAllRooms()) {
            roomListModel.addElement(room.toString());
        }
        updateDetailsArea();
    }

    private void updateDetailsArea() {
        String selected = roomList.getSelectedValue();
        if (selected != null) {
            String roomNumber = selected.split(" ")[1]; 
            Room room = roomManager.getRoom(roomNumber);
            if (room != null) {
                detailsTextArea.setText(
                    "══════════════════════════════\n" +
                    "  ROOM NUMBER:   " + room.getRoomNumber() + "\n" +
                    "══════════════════════════════\n" +
                    "  CAPACITY:      " + room.getCapacity() + " students\n" +
                    "  OCCUPANCY:     " + room.getCurrentOccupancy() + " students\n" +
                    "  AVAILABLE:     " + (room.getCapacity() - room.getCurrentOccupancy()) + " spaces\n" +
                    "  STATUS:        " + room.getStatus().toUpperCase() + "\n" +
                    "══════════════════════════════"
                );
            } else {
                detailsTextArea.setText("Details not found for selected room.");
            }
        } else {
            detailsTextArea.setText("Select a room from the list to view details.");
        }
    }

    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private void showStyledDialog(String message, String title, int messageType, Color accent) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(lighterBackground);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(accent);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JLabel messageLabel = new JLabel("<html><div style='width:300px;'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(textColor);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(messageLabel, BorderLayout.CENTER);
        
        JOptionPane.showMessageDialog(
            this,
            panel,
            title,
            messageType
        );
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
            panel.setBackground(lighterBackground);

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
                    showStyledDialog("Room " + roomNumber + " added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE, successColor);
                } catch (NumberFormatException ex) {
                    showStyledDialog("Please enter a valid number for capacity.", "Input Error", JOptionPane.ERROR_MESSAGE, errorColor);
                } catch (IllegalArgumentException ex) {
                    showStyledDialog(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, errorColor);
                }
            }
        }
    }

   private class RemoveRoomListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String selected = roomList.getSelectedValue();
        if (selected == null) {
            showStyledDialog("Please select a room to remove.", "Selection Error", 
                JOptionPane.ERROR_MESSAGE, errorColor);
            return;
        }

        String roomNumber = selected.split(" ")[1];
        
        // Create a custom panel for the dialog
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(lighterBackground);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel message = new JLabel(
            "<html><div style='width:300px; color:" + toHex(textColor) + 
            "'>Are you sure you want to remove room " + roomNumber + "?</div></html>");
        message.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(message, BorderLayout.CENTER);

        // Customize the option pane
        UIManager.put("OptionPane.background", lighterBackground);
        UIManager.put("Panel.background", lighterBackground);
        UIManager.put("Button.background", accentColor);
        UIManager.put("Button.foreground", Color.WHITE);

        int confirm = JOptionPane.showConfirmDialog(
            RoomManagementGUI.this,
            panel,
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        // Reset UI manager defaults
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);

        if (confirm == JOptionPane.YES_OPTION) {
            if (roomManager.removeRoom(roomNumber)) {
                refreshRoomList();
                showStyledDialog("Room " + roomNumber + " removed successfully.", 
                    "Success", JOptionPane.INFORMATION_MESSAGE, successColor);
            } else {
                showStyledDialog("Failed to remove room " + roomNumber + ". It might not exist.", 
                    "Error", JOptionPane.ERROR_MESSAGE, errorColor);
            }
        }
    }
}

    private class UpdateStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = roomList.getSelectedValue();
            if (selected == null) {
                showStyledDialog("Please select a room to update its status.", "Selection Error", JOptionPane.ERROR_MESSAGE, errorColor);
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
                roomManager.getRoom(roomNumber).getStatus()
            );

            if (newStatus != null) {
                roomManager.updateRoomStatus(roomNumber, newStatus);
                refreshRoomList();
                showStyledDialog("Status of room " + roomNumber + " updated to " + newStatus + ".", "Status Updated", JOptionPane.INFORMATION_MESSAGE, infoColor);
            }
        }
    }

    private class UpdateCapacityListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = roomList.getSelectedValue();
            if (selected == null) {
                showStyledDialog("Please select a room to update its capacity.", "Selection Error", JOptionPane.ERROR_MESSAGE, errorColor);
                return;
            }

            String roomNumber = selected.split(" ")[1];
            JTextField capacityField = new JTextField(String.valueOf(roomManager.getRoom(roomNumber).getCapacity()), 5);

            JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
            panel.add(new JLabel("<html><font color='" + toHex(textColor) + "'>New Capacity:</font></html>"));
            panel.add(capacityField);
            panel.setBackground(lighterBackground);

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
                    Room room = roomManager.getRoom(roomNumber);
                    if (room != null && newCapacity < room.getCurrentOccupancy()) {
                        throw new IllegalArgumentException("New capacity cannot be less than current occupancy (" + room.getCurrentOccupancy() + ").");
                    }

                    roomManager.updateRoomCapacity(roomNumber, newCapacity);
                    refreshRoomList();
                    showStyledDialog("Capacity of room " + roomNumber + " updated to " + newCapacity + ".", "Capacity Updated", JOptionPane.INFORMATION_MESSAGE, infoColor);
                } catch (NumberFormatException ex) {
                    showStyledDialog("Please enter a valid number for capacity.", "Input Error", JOptionPane.ERROR_MESSAGE, errorColor);
                } catch (IllegalArgumentException ex) {
                    showStyledDialog(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, errorColor);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RoomManager manager = new RoomManager();
            new RoomManagementGUI(manager);
        });
    }
}