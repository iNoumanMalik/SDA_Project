package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.*;

public class RegistrationGUI extends JFrame {
    private final UserManager userManager;
    
    // Color scheme matching LoginGUI
    private final Color primaryColor = new Color(0, 120, 215);
    private final Color darkBackground = new Color(30, 30, 40);
    private final Color lightBackground = new Color(50, 50, 60);
    private final Color textColor = new Color(240, 240, 240);
    private final Color errorColor = new Color(255, 100, 100);
    
    // Components
    private final JTextField usernameField = createStyledTextField();
    private final JPasswordField passwordField = createStyledPasswordField();
    private final JPasswordField confirmPasswordField = createStyledPasswordField();
    private final JButton registerButton = createGradientButton("REGISTER", new Color(100, 220, 100), new Color(0, 150, 100));
    
    // Validation labels
    private final JLabel usernameValidationLabel = createValidationLabel("");
    private final JLabel passwordValidationLabel = createValidationLabel("");
    private final JLabel confirmPasswordValidationLabel = createValidationLabel("");

    public RegistrationGUI(UserManager userManager) {
        this.userManager = userManager;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("User Registration");
        setSize(450, 600); // Increased height to accommodate validation labels
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        gbc.insets = new Insets(5, 20, 5, 20);

        // Title
        JLabel titleLabel = new JLabel("USER REGISTRATION", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(primaryColor);
        gbc.insets = new Insets(5, 20, 15, 20);
        formPanel.add(titleLabel, gbc);

        // Username field
        gbc.insets = new Insets(5, 20, 2, 20);
        formPanel.add(createInputLabel("Username"), gbc);
        formPanel.add(usernameField, gbc);
        gbc.insets = new Insets(0, 20, 10, 20);
        formPanel.add(usernameValidationLabel, gbc);

        // Password field
        gbc.insets = new Insets(5, 20, 2, 20);
        formPanel.add(createInputLabel("Password"), gbc);
        formPanel.add(passwordField, gbc);
        gbc.insets = new Insets(0, 20, 10, 20);
        formPanel.add(passwordValidationLabel, gbc);

        // Confirm Password field
        gbc.insets = new Insets(5, 20, 2, 20);
        formPanel.add(createInputLabel("Confirm Password"), gbc);
        formPanel.add(confirmPasswordField, gbc);
        gbc.insets = new Insets(0, 20, 20, 20);
        formPanel.add(confirmPasswordValidationLabel, gbc);

        // Register button
        gbc.insets = new Insets(20, 20, 5, 20);
        formPanel.add(registerButton, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Add listeners
        registerButton.addActionListener(new RegisterListener());
        usernameField.addFocusListener(new UsernameFieldListener());
        passwordField.addFocusListener(new PasswordFieldListener());
        confirmPasswordField.addFocusListener(new ConfirmPasswordFieldListener());
        
        // Add window listener to handle closing properly
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private JLabel createValidationLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(errorColor);
        return label;
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

    private boolean validateUsername(String username) {
        if (username.isEmpty()) {
            usernameValidationLabel.setText("Username cannot be empty");
            return false;
        }
        
        if (username.length() < 4) {
            usernameValidationLabel.setText("Username must be at least 4 characters");
            return false;
        }
        
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            usernameValidationLabel.setText("Only letters, numbers and underscores allowed");
            return false;
        }
        
        usernameValidationLabel.setText("");
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
            passwordValidationLabel.setText("Password cannot be empty");
            return false;
        }
        
        if (password.length() < 6) {
            passwordValidationLabel.setText("Password must be at least 6 characters");
            return false;
        }
        
        passwordValidationLabel.setText("");
        return true;
    }

    private boolean validateConfirmPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            confirmPasswordValidationLabel.setText("Passwords do not match");
            return false;
        }
        
        confirmPasswordValidationLabel.setText("");
        return true;
    }

    private class UsernameFieldListener extends FocusAdapter {
        @Override
        public void focusLost(FocusEvent e) {
            validateUsername(usernameField.getText().trim());
        }
    }

    private class PasswordFieldListener extends FocusAdapter {
        @Override
        public void focusLost(FocusEvent e) {
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            validatePassword(password);
            if (!confirmPassword.isEmpty()) {
                validateConfirmPassword(password, confirmPassword);
            }
        }
    }

    private class ConfirmPasswordFieldListener extends FocusAdapter {
        @Override
        public void focusLost(FocusEvent e) {
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            validateConfirmPassword(password, confirmPassword);
        }
    }

private class RegisterListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Reset validation messages
        usernameValidationLabel.setText("");
        passwordValidationLabel.setText("");
        confirmPasswordValidationLabel.setText("");

        // Validate all fields
        boolean isValid = true;

        if (username.isEmpty()) {
            usernameValidationLabel.setText("Username cannot be empty");
            isValid = false;
        } else if (username.length() < 4) {
            usernameValidationLabel.setText("Username must be at least 4 characters");
            isValid = false;
        } else if (!username.matches("^[a-zA-Z0-9_]+$")) {
            usernameValidationLabel.setText("Only letters, numbers and underscores allowed");
            isValid = false;
        }

        if (password.isEmpty()) {
            passwordValidationLabel.setText("Password cannot be empty");
            isValid = false;
        } else if (password.length() < 6) {
            passwordValidationLabel.setText("Password must be at least 6 characters");
            isValid = false;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordValidationLabel.setText("Passwords do not match");
            isValid = false;
        }

        if (!isValid) {
            return;
        }

        // Check if username exists
        if (userManager.usernameExists(username)) {
            usernameValidationLabel.setText("Username already exists");
            return;
        }

        // Attempt registration
        if (userManager.addUser(username, password)) {
            JOptionPane.showMessageDialog(RegistrationGUI.this,
                "Registration successful! You can now login.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(RegistrationGUI.this,
                "Registration failed. Please try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
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