package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class LoginGUI extends JFrame {
    private final UserManager userManager;
    
    // Color scheme
    private final Color darkBackground = new Color(18, 18, 18);
    private final Color lighterBackground = new Color(30, 30, 30);
    private final Color accentColor = new Color(0, 150, 255);
    private final Color textColor = new Color(240, 240, 240);
    
    // Login Panel components
    private final JTextField loginUsernameField = createStyledTextField();
    private final JPasswordField loginPasswordField = createStyledPasswordField();
    private final JButton loginButton = createGradientButton("Login", new Color(0, 180, 255), new Color(0, 100, 255));
    private final JButton registerButton = createGradientButton("Register", new Color(100, 220, 100), new Color(0, 150, 100));
    private final JButton showResetButton = createFlatButton("Forgot Password?");

    // Reset Panel components
    private final JTextField resetUsernameField = createStyledTextField();
    private final JPasswordField resetNewPasswordField = createStyledPasswordField();
    private final JPasswordField resetConfirmPasswordField = createStyledPasswordField();
    private final JButton resetPasswordButton = createGradientButton("Reset Password", new Color(255, 100, 100), new Color(200, 50, 50));
    private final JButton backToLoginButton = createFlatButton("Back to Login");

    // Panels
    private final JPanel cardPanel;
    private final JPanel loginPanel;
    private final JPanel resetPanel;
    private final CardLayout cardLayout = new CardLayout();

    public LoginGUI(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Hostel Management System - Login");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

        // Initialize panels
        cardPanel = new JPanel(cardLayout) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(darkBackground);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        
        loginPanel = createLoginPanel();
        resetPanel = createResetPanel();

        // Add panels to cardPanel
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(resetPanel, "Reset");

        add(cardPanel, BorderLayout.CENTER);
        setupActionListeners();
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(lighterBackground);
                g2d.fillRoundRect(20, 20, getWidth()-40, getHeight()-40, 15, 15);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 15, 5);

        // Title
        JLabel titleLabel = new JLabel("HOSTEL LOGIN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(accentColor);
        gbc.insets = new Insets(0, 0, 30, 0);
        panel.add(titleLabel, gbc);

        // Username
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(createInputLabel("Username:"), gbc);
        panel.add(loginUsernameField, gbc);

        // Password
        panel.add(createInputLabel("Password:"), gbc);
        panel.add(loginPasswordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(buttonPanel, gbc);

        // Forgot password
        gbc.insets = new Insets(5, 5, 0, 5);
        panel.add(showResetButton, gbc);

        return panel;
    }

    private JPanel createResetPanel() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(lighterBackground);
                g2d.fillRoundRect(20, 20, getWidth()-40, getHeight()-40, 15, 15);
            }
        };
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 15, 5);

        // Title
        JLabel titleLabel = new JLabel("RESET PASSWORD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 100, 100));
        gbc.insets = new Insets(0, 0, 30, 0);
        panel.add(titleLabel, gbc);

        // Username
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(createInputLabel("Username:"), gbc);
        panel.add(resetUsernameField, gbc);

        // New Password
        panel.add(createInputLabel("New Password:"), gbc);
        panel.add(resetNewPasswordField, gbc);

        // Confirm Password
        panel.add(createInputLabel("Confirm Password:"), gbc);
        panel.add(resetConfirmPasswordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(backToLoginButton);
        buttonPanel.add(resetPasswordButton);
        gbc.insets = new Insets(15, 5, 5, 5);
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JLabel createInputLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(textColor);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(textColor);
        field.setBackground(new Color(60, 60, 60));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80)), 
            BorderFactory.createEmptyBorder(8, 10, 8, 10))
        );
        field.setCaretColor(accentColor);
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(textColor);
        field.setBackground(new Color(60, 60, 60));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80)), 
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        field.setCaretColor(accentColor);
        return field;
    }

    private JButton createGradientButton(String text, Color startColor, Color endColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Paint gradient background
                GradientPaint gp = new GradientPaint(
                    0, 0, startColor, 
                    getWidth(), getHeight(), endColor
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Paint text
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
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        return button;
    }

    private JButton createFlatButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setForeground(new Color(180, 180, 180));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(accentColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(180, 180, 180));
            }
        });
        
        return button;
    }

    private void setupActionListeners() {
        loginButton.addActionListener(new LoginListener());
        loginPasswordField.addActionListener(new LoginListener());
        
        registerButton.addActionListener(e -> {
            new RegistrationGUI(userManager).setVisible(true);
        });

        showResetButton.addActionListener(e -> showResetPanel());
        backToLoginButton.addActionListener(e -> showLoginPanel());
        resetPasswordButton.addActionListener(e -> resetUserPassword());
        resetConfirmPasswordField.addActionListener(e -> resetUserPassword());
    }

    private void showResetPanel() {
        cardLayout.show(cardPanel, "Reset");
        resetUsernameField.setText(loginUsernameField.getText());
        resetNewPasswordField.setText("");
        resetConfirmPasswordField.setText("");
        setTitle("Hostel Management System - Reset Password");
    }

    private void showLoginPanel() {
        cardLayout.show(cardPanel, "Login");
        loginPasswordField.setText("");
        setTitle("Hostel Management System - Login");
    }

    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginUsernameField.getText().trim();
            String password = new String(loginPasswordField.getPassword());

            if (userManager.authenticate(username, password)) {
                SwingUtilities.invokeLater(() -> {
                    new MainMenuGUI().setVisible(true);
                    dispose();
                });
            } else {
                showErrorDialog("Invalid username or password.", "Login Failed");
                loginPasswordField.setText("");
            }
        }
    }

    private void resetUserPassword() {
        String username = resetUsernameField.getText().trim();
        String newPass = new String(resetNewPasswordField.getPassword());
        String confirmPass = new String(resetConfirmPasswordField.getPassword());

        if (username.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            showErrorDialog("All fields are required.", "Error");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            showErrorDialog("Passwords do not match.", "Error");
            return;
        }

        if (userManager.resetPassword(username, newPass)) {
            JOptionPane.showMessageDialog(this,
                "Password reset successfully. You can now log in with your new password.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            showLoginPanel();
            loginUsernameField.setText(username);
        } else {
            showErrorDialog("User not found.", "Error");
        }
    }

    private void showErrorDialog(String message, String title) {
        JOptionPane.showMessageDialog(this,
            message,
            title,
            JOptionPane.ERROR_MESSAGE);
    }
    }
