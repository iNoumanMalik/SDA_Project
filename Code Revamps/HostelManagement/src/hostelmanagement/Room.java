package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    }

    public void addRoom(String roomNumber, int capacity) {
        rooms.add(new Room(roomNumber, capacity));
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

class RoomManagementGUI extends JFrame {
    private RoomManager roomManager;
    private DefaultListModel<String> roomListModel;
    private JList<String> roomList;

    public RoomManagementGUI(RoomManager roomManager) {
        this.roomManager = roomManager;
        initializeUI();
        refreshRoomList();
    }

    private void initializeUI() {
        setTitle("Hostel Room Management System");
        setSize(600, 400);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Room list on the left
        roomListModel = new DefaultListModel<>();
        roomList = new JList<>(roomListModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(roomList);
        listScrollPane.setPreferredSize(new Dimension(200, 0));

        // Control panel on the right
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add Room Button
        JButton addButton = new JButton("Add New Room");
        addButton.addActionListener(new AddRoomListener());
        controlPanel.add(addButton, gbc);

        // Remove Room Button
        JButton removeButton = new JButton("Remove Selected Room");
        removeButton.addActionListener(new RemoveRoomListener());
        controlPanel.add(removeButton, gbc);

        // Update Status Button
        JButton statusButton = new JButton("Update Room Status");
        statusButton.addActionListener(new UpdateStatusListener());
        controlPanel.add(statusButton, gbc);

        // Update Capacity Button
        JButton capacityButton = new JButton("Update Room Capacity");
        capacityButton.addActionListener(new UpdateCapacityListener());
        controlPanel.add(capacityButton, gbc);

        // Details Panel at the bottom
        JPanel detailsPanel = new JPanel(new BorderLayout());
        JLabel detailsLabel = new JLabel("Room Details:");
        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsPanel.add(detailsLabel, BorderLayout.NORTH);
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        // Add selection listener to show details
        roomList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = roomList.getSelectedValue();
                if (selected != null) {
                    String roomNumber = selected.split(" ")[1];
                    Room room = roomManager.getRoom(roomNumber);
                    detailsTextArea.setText(
                        "Room Number: " + room.getRoomNumber() + "\n" +
                        "Capacity: " + room.getCapacity() + "\n" +
                        "Current Occupancy: " + room.getCurrentOccupancy() + "\n" +
                        "Status: " + room.getStatus()
                    );
                }
            }
        });

        // Add components to main panel
        mainPanel.add(listScrollPane, BorderLayout.WEST);
        mainPanel.add(controlPanel, BorderLayout.CENTER);
        mainPanel.add(detailsPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void refreshRoomList() {
        roomListModel.clear();
        for (Room room : roomManager.getAllRooms()) {
            roomListModel.addElement(room.toString());
        }
    }

    // Action Listeners
    private class AddRoomListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField roomNumberField = new JTextField();
            JTextField capacityField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Room Number:"));
            panel.add(roomNumberField);
            panel.add(new JLabel("Capacity:"));
            panel.add(capacityField);

            int result = JOptionPane.showConfirmDialog(
                RoomManagementGUI.this,
                panel,
                "Add New Room",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String roomNumber = roomNumberField.getText().trim();
                    int capacity = Integer.parseInt(capacityField.getText().trim());
                    
                    if (roomNumber.isEmpty()) {
                        throw new IllegalArgumentException("Room number cannot be empty");
                    }
                    if (capacity <= 0) {
                        throw new IllegalArgumentException("Capacity must be positive");
                    }
                    
                    roomManager.addRoom(roomNumber, capacity);
                    refreshRoomList();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        RoomManagementGUI.this,
                        "Please enter a valid number for capacity",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(
                        RoomManagementGUI.this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }

    private class RemoveRoomListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = roomList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(
                    RoomManagementGUI.this,
                    "Please select a room to remove",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String roomNumber = selected.split(" ")[1];
            int confirm = JOptionPane.showConfirmDialog(
                RoomManagementGUI.this,
                "Are you sure you want to remove room " + roomNumber + "?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (roomManager.removeRoom(roomNumber)) {
                    refreshRoomList();
                }
            }
        }
    }

    private class UpdateStatusListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = roomList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(
                    RoomManagementGUI.this,
                    "Please select a room to update",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String roomNumber = selected.split(" ")[1];
            String[] statusOptions = {"available", "occupied", "maintenance"};
            String newStatus = (String) JOptionPane.showInputDialog(
                RoomManagementGUI.this,
                "Select new status for room " + roomNumber,
                "Update Room Status",
                JOptionPane.PLAIN_MESSAGE,
                null,
                statusOptions,
                statusOptions[0]
            );

            if (newStatus != null) {
                roomManager.updateRoomStatus(roomNumber, newStatus);
                refreshRoomList();
            }
        }
    }

    private class UpdateCapacityListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = roomList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(
                    RoomManagementGUI.this,
                    "Please select a room to update",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String roomNumber = selected.split(" ")[1];
            JTextField capacityField = new JTextField();

            JPanel panel = new JPanel(new GridLayout(1, 2));
            panel.add(new JLabel("New Capacity:"));
            panel.add(capacityField);

            int result = JOptionPane.showConfirmDialog(
                RoomManagementGUI.this,
                panel,
                "Update Capacity for Room " + roomNumber,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    int newCapacity = Integer.parseInt(capacityField.getText().trim());
                    if (newCapacity <= 0) {
                        throw new IllegalArgumentException("Capacity must be positive");
                    }
                    roomManager.updateRoomCapacity(roomNumber, newCapacity);
                    refreshRoomList();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        RoomManagementGUI.this,
                        "Please enter a valid number for capacity",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(
                        RoomManagementGUI.this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
    }
}