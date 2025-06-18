package hostelmanagement;

import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame {
    private UserManager userManager;
    private RoomManager roomManager;
    private EmployeeManager employeeManager;
    private FinanceManager financeManager; // Add FinanceManager instance

    public MainMenuGUI(UserManager userManager) {
        this.userManager = userManager;
        this.roomManager = new RoomManager();
        this.employeeManager = new EmployeeManager();
        this.financeManager = new FinanceManager(); // Initialize FinanceManager

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Hostel Management System - Main Menu");
        setSize(500, 200); // Increased width to accommodate new button
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use a 1x4 grid for 4 buttons (Student, Employee, Rooms, Finance)
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton studentButton = new JButton("Student");
        studentButton.setEnabled(false); // disable as per your request

        JButton employeeButton = new JButton("Employee");
        JButton roomsButton = new JButton("Rooms");
        JButton financeButton = new JButton("Finance"); // New Finance button

        panel.add(studentButton);
        panel.add(employeeButton);
        panel.add(roomsButton);
        panel.add(financeButton); // Add finance button to the panel

        add(panel);

        employeeButton.addActionListener(e -> {
            new EmployeeManagementGUI(employeeManager).setVisible(true);
        });

        roomsButton.addActionListener(e -> {
            new RoomManagementGUI(roomManager).setVisible(true);
        });

        // Action listener for the new Finance button
        financeButton.addActionListener(e -> {
            // Pass the existing financeManager instance to the GUI
            new FinanceManagerGUI(financeManager).setVisible(true);
        });
    }
}