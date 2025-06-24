package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

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
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set dark theme
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(20, 30, 48);
                Color color2 = new Color(36, 59, 85);
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Header label
        JLabel headerLabel = new JLabel("HOSTEL MANAGEMENT SYSTEM", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerLabel.setForeground(new Color(220, 220, 220));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        buttonPanel.setOpaque(false);

        // Create and add all buttons
        buttonPanel.add(createGradientButton("Student Management", 
            new Color(106, 17, 203), new Color(37, 117, 252),
            e -> new StudentManagementGUI(studentManager).setVisible(true)));
        
        buttonPanel.add(createGradientButton("Employee Management", 
            new Color(252, 74, 26), new Color(247, 183, 51),
            e -> new EmployeeManagementGUI(employeeManager).setVisible(true)));
        
        buttonPanel.add(createGradientButton("Room Management", 
            new Color(31, 64, 55), new Color(153, 242, 200),
            e -> new RoomManagementGUI(roomManager).setVisible(true)));
        
        buttonPanel.add(createGradientButton("Finance Management", 
            new Color(131, 58, 180), new Color(193, 53, 132),
            e -> new FinanceManagerGUI(financeManager).setVisible(true)));
        
        buttonPanel.add(createGradientButton("Student Living", 
            new Color(0, 90, 167), new Color(51, 154, 240),
            e -> new StudentLivingGUI(studentManager, roomManager).setVisible(true)));
        
        buttonPanel.add(createGradientButton("Student Leaving", 
            new Color(194, 21, 0), new Color(255, 197, 0),
            e -> new StudentLeavingGUI(studentManager, roomManager).setVisible(true)));

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Footer
        JLabel footerLabel = new JLabel("© 2025 Hostel Management System | Nouman & Co.", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setForeground(new Color(180, 180, 180));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createGradientButton(String text, Color startColor, Color endColor, ActionListener action) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Paint gradient background
                GradientPaint gp = new GradientPaint(
                    new Point2D.Float(0, 0), 
                    startColor, 
                    new Point2D.Float(getWidth(), getHeight()), 
                    endColor
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Paint text
                g2.setColor(Color.WHITE);
                g2.setFont(getFont().deriveFont(Font.BOLD, 14));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), x, y);
                
                // Paint border
                g2.setColor(new Color(255, 255, 255, 50));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                
                g2.dispose();
            }
        };
        
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 80));
        button.addActionListener(action);
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        return button;
    }
    
}
