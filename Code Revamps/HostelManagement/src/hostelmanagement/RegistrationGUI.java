package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class RegistrationGUI extends JFrame {
    private final UserManager userManager;
    
    // Color scheme matching LoginGUI
    private final Color primaryColor = new Color(0, 120, 215);
    private final Color darkBackground = new Color(30, 30, 40);
    private final Color lightBackground = new Color(50, 50, 60);
    private final Color textColor = new Color(240, 240, 240);
    
    // Components
    private final JTextField usernameField = createStyledTextField();
    private final JPasswordField passwordField = createStyledPasswordField();
    private final JPasswordField confirmPasswordField = createStyledPasswordField();
    private final JButton registerButton = createGradientButton("REGISTER", new Color(100, 220, 100), new Color(0, 150, 100));

    public RegistrationGUI(UserManager userManager) {
        this.userManager = userManager;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("User Registration");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed to DISPOSE_ON_CLOSE
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));

        // Main panel with rounded corners
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(darkBackground);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(lightBackground);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 20, 15, 20);

        // Title
        JLabel titleLabel = new JLabel("USER REGISTRATION", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(primaryColor);
        formPanel.add(titleLabel, gbc);

        // Username field
        gbc.insets = new Insets(5, 20, 5, 20);
        formPanel.add(createInputLabel("Username"), gbc);
        formPanel.add(usernameField, gbc);

        // Password field
        formPanel.add(createInputLabel("Password"), gbc);
        formPanel.add(passwordField, gbc);

        // Confirm Password field
        formPanel.add(createInputLabel("Confirm Password"), gbc);
        formPanel.add(confirmPasswordField, gbc);

        // Register button
        gbc.insets = new Insets(20, 20, 5, 20);
        formPanel.add(registerButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        registerButton.addActionListener(new RegisterListener());
        
        // Add window listener to handle closing properly
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); // Just close this window, not the entire application
            }
        });
    }

private JLabel createInputLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(textColor);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(textColor);
        field.setBackground(new Color(70, 70, 80));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(90, 90, 100), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        field.setCaretColor(primaryColor);
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(textColor);
        field.setBackground(new Color(70, 70, 80));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(90, 90, 100), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        field.setCaretColor(primaryColor);
        return field;
    }

    private JButton createGradientButton(String text, Color start, Color end) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gp = new GradientPaint(0, 0, start, getWidth(), getHeight(), end);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont().deriveFont(Font.BOLD, 14));
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(getText(), x, y);
                
                g2d.dispose();
            }
        };
        
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(0, 45));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    // [Keep all the other helper methods unchanged: createInputLabel(), createStyledTextField(), 
    // createStyledPasswordField(), createGradientButton()]

    private class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                showError("Username and password cannot be empty.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showError("Passwords do not match.");
                return;
            }

            if (userManager.addUser(username, password)) {
                JOptionPane.showMessageDialog(RegistrationGUI.this,
                    "Registration successful! You can now login.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Only close the registration window
            } else {
                showError("Username already exists.");
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}