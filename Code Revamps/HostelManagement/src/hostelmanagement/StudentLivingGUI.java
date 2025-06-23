/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StudentLivingGUI extends JFrame {
    private StudentManager studentManager;
    private RoomManager roomManager;
    
    private JComboBox<String> studentComboBox;
    private JList<String> roomList;
    private DefaultListModel<String> roomListModel;
    private JTextArea outputArea;

    public StudentLivingGUI(StudentManager studentManager, RoomManager roomManager) {
        this.studentManager = studentManager;
        this.roomManager = roomManager;
        
        setTitle("Student Accommodation System");
        setSize(600, 500);
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

        // Room list
        roomListModel = new DefaultListModel<>();
        refreshRoomList();
        roomList = new JList<>(roomListModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(roomList);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton applyBtn = new JButton("Apply for Room");
        JButton showDetailsBtn = new JButton("Show Room Details");

        applyBtn.addActionListener(e -> applyForRoom());
        showDetailsBtn.addActionListener(e -> showRoomDetails());

        buttonPanel.add(applyBtn);
        buttonPanel.add(showDetailsBtn);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        mainPanel.add(selectionPanel, BorderLayout.NORTH);
        mainPanel.add(listScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(outputScrollPane, BorderLayout.SOUTH);
    }

    private void refreshStudentList() {
        studentComboBox.removeAllItems();
        for (String student : studentManager.getAllStudents()) {
            studentComboBox.addItem(student);
        }
    }

    private void refreshRoomList() {
        roomListModel.clear();
        for (Room room : roomManager.getAllRooms()) {
            if (room.getStatus().equals("available")) {
                roomListModel.addElement(room.toString());
            }
        }
    }

    private void applyForRoom() {
        String selectedStudent = (String) studentComboBox.getSelectedItem();
        String selectedRoom = roomList.getSelectedValue();
        
        if (selectedStudent == null || selectedRoom == null) {
            outputArea.append("Please select both a student and a room\n");
            return;
        }

        String roomNumber = selectedRoom.split(" ")[1];
        Room room = roomManager.getRoom(roomNumber);
        
        if (room != null) {
            outputArea.append("Application submitted for " + roomNumber + "\n");
            approveApplication(selectedStudent, room);
        }
    }

    private void approveApplication(String studentName, Room room) {
        outputArea.append("Application approved for " + studentName + "\n");
        signContract(studentName, room);
    }

    private void signContract(String studentName, Room room) {
        outputArea.append(studentName + " signed lease for room " + room.getRoomNumber() + "\n");
        processPayment(studentName, room);
    }

    private void processPayment(String studentName, Room room) {
        // In a real system, you would collect payment details here
        outputArea.append("Payment received for room " + room.getRoomNumber() + "\n");
        room.setStatus("occupied");
        roomManager.updateRoomStatus(room.getRoomNumber(), "occupied");
        notifyMoveIn(studentName, room);
        refreshRoomList();
    }

    private void notifyMoveIn(String studentName, Room room) {
        outputArea.append("Move-in date confirmed. Welcome to your new room " + 
                         room.getRoomNumber() + ", " + studentName + "!\n");
    }

    private void showRoomDetails() {
        String selectedRoom = roomList.getSelectedValue();
        if (selectedRoom == null) {
            outputArea.append("Please select a room\n");
            return;
        }

        String roomNumber = selectedRoom.split(" ")[1];
        Room room = roomManager.getRoom(roomNumber);
        
        if (room != null) {
            outputArea.append("Room Details:\n");
            outputArea.append("Number: " + room.getRoomNumber() + "\n");
            outputArea.append("Capacity: " + room.getCapacity() + "\n");
            outputArea.append("Status: " + room.getStatus() + "\n");
        }
    }
}