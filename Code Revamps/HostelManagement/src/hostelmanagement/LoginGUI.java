package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private final UserManager userManager;
    
    // Login Panel components
    private final JTextField loginUsernameField = new JTextField();
    private final JPasswordField loginPasswordField = new JPasswordField();
    private final JButton loginButton = new JButton("Login");
    private final JButton registerButton = new JButton("Register");
    private final JButton showResetButton = new JButton("Reset Password");

    // Reset Panel components
    private final JTextField resetUsernameField = new JTextField();
    private final JPasswordField resetNewPasswordField = new JPasswordField();
    private final JPasswordField resetConfirmPasswordField = new JPasswordField();
    private final JButton resetPasswordButton = new JButton("Reset Password");
    private final JButton backToLoginButton = new JButton("Back to Login");

    // Panels
    private final JPanel cardPanel;
    private final JPanel loginPanel;
    private final JPanel resetPanel;
    private final CardLayout cardLayout = new CardLayout();

    public LoginGUI(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Hostel Management System");
        setSize(380, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize panels
        cardPanel = new JPanel(cardLayout);
        loginPanel = createLoginPanel();
        resetPanel = createResetPanel();

        // Add panels to cardPanel
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(resetPanel, "Reset");

        add(cardPanel, BorderLayout.CENTER);
        setupActionListeners();
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Username:"));
        panel.add(loginUsernameField);
        panel.add(new JLabel("Password:"));
        panel.add(loginPasswordField);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(new JLabel(""));
        panel.add(showResetButton);

        return panel;
    }

    private JPanel createResetPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Username:"));
        panel.add(resetUsernameField);
        panel.add(new JLabel("New Password:"));
        panel.add(resetNewPasswordField);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(resetConfirmPasswordField);
        panel.add(backToLoginButton);
        panel.add(resetPasswordButton);

        return panel;
    }

    private void setupActionListeners() {
        loginButton.addActionListener(new LoginListener());
        loginPasswordField.addActionListener(new LoginListener());
        
        registerButton.addActionListener(e -> 
            new RegistrationGUI(userManager).setVisible(true));

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
        setTitle("Hostel Management System");
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
                JOptionPane.showMessageDialog(LoginGUI.this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
                loginPasswordField.setText("");
            }
        }
    }

    private void resetUserPassword() {
        String username = resetUsernameField.getText().trim();
        String newPass = new String(resetNewPasswordField.getPassword());
        String confirmPass = new String(resetConfirmPasswordField.getPassword());

        if (username.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            showError("Passwords do not match.");
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
            showError("User not found.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}