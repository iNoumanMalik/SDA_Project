package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JFrame {
    private final RoomManager roomManager;
    private final EmployeeManager employeeManager;
    private final FinanceManager financeManager;
    private final StudentManager studentManager;

    public MainMenuGUI() {
        this.roomManager = new RoomManager();
        this.employeeManager = new EmployeeManager();
        this.financeManager = new FinanceManager();
        this.studentManager = new StudentManager();

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Hostel Management System - Main Menu");
        setSize(700, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(1, 6, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create and add all buttons
        panel.add(createButton("Student", 
            e -> new StudentManagementGUI(studentManager).setVisible(true)));
        
        panel.add(createButton("Employee", 
            e -> new EmployeeManagementGUI(employeeManager).setVisible(true)));
        
        panel.add(createButton("Rooms", 
            e -> new RoomManagementGUI(roomManager).setVisible(true)));
        
        panel.add(createButton("Finance", 
            e -> new FinanceManagerGUI(financeManager).setVisible(true)));
        
        panel.add(createButton("Student Living", 
            e -> new StudentLivingGUI(studentManager, roomManager).setVisible(true)));
        
        panel.add(createButton("Student Leaving", 
            e -> new StudentLeavingGUI(studentManager, roomManager).setVisible(true)));

        add(panel);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }
}