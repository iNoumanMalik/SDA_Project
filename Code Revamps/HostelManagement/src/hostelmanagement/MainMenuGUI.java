/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelmanagement;

/**
 *
 * @author SP23-BSE-014
 */

import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {
    private UserManager userManager;
    private RoomManager roomManager;
    private EmployeeManager employeeManager;

    public MainMenuGUI(UserManager userManager) {
        this.userManager = userManager;
        this.roomManager = new RoomManager();
        this.employeeManager = new EmployeeManager();

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Hostel Management System - Main Menu");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton studentButton = new JButton("Student");
        studentButton.setEnabled(false); // disable as per your request

        JButton employeeButton = new JButton("Employee");
        JButton roomsButton = new JButton("Rooms");

        panel.add(studentButton);
        panel.add(employeeButton);
        panel.add(roomsButton);

        add(panel);

        employeeButton.addActionListener(e -> {
            new EmployeeManagementGUI(employeeManager).setVisible(true);
        });

        roomsButton.addActionListener(e -> {
            new RoomManagementGUI(roomManager).setVisible(true);
        });
    }
}
