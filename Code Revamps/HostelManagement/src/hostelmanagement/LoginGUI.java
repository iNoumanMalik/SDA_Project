package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private UserManager userManager;

    // Components for Login Panel
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton showResetButton; // Button to switch to reset panel

    // Components for Reset Password Panel
    private JTextField resetUsernameField;
    private JPasswordField resetNewPasswordField;
    private JPasswordField resetConfirmPasswordField;
    private JButton resetPasswordButton; // The actual reset button
    private JButton backToLoginButton; // Button to switch back to login panel

    // Panels and CardLayout
    private JPanel cardPanel; // Panel that uses CardLayout
    private JPanel loginPanel;
    private JPanel resetPanel;
    private CardLayout cardLayout;

    public LoginGUI(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Hostel Management System"); // More general title
        setSize(380, 250); // Adjusted size for embedded panels
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout); // Initialize cardPanel with CardLayout

        // --- Initialize Login Panel ---
        loginPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        loginPanel.add(new JLabel("Username:"));
        loginUsernameField = new JTextField();
        loginPanel.add(loginUsernameField);

        loginPanel.add(new JLabel("Password:"));
        loginPasswordField = new JPasswordField();
        loginPanel.add(loginPasswordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        showResetButton = new JButton("Reset Password");

        loginPanel.add(loginButton);
        loginPanel.add(registerButton);
        loginPanel.add(new JLabel("")); // Empty cell for spacing
        loginPanel.add(showResetButton); // Add button to show reset panel

        // --- Initialize Reset Password Panel ---
        resetPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        resetPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resetPanel.add(new JLabel("Username:"));
        resetUsernameField = new JTextField();
        resetPanel.add(resetUsernameField);

        resetPanel.add(new JLabel("New Password:"));
        resetNewPasswordField = new JPasswordField();
        resetPanel.add(resetNewPasswordField);

        resetPanel.add(new JLabel("Confirm Password:"));
        resetConfirmPasswordField = new JPasswordField();
        resetPanel.add(resetConfirmPasswordField);

        resetPasswordButton = new JButton("Reset Password");
        backToLoginButton = new JButton("Back to Login");

        resetPanel.add(backToLoginButton); // Back button on left
        resetPanel.add(resetPasswordButton); // Reset button on right

        // Add panels to the cardPanel
        cardPanel.add(loginPanel, "Login");
        cardPanel.add(resetPanel, "Reset");

        // Add cardPanel to the JFrame
        add(cardPanel, BorderLayout.CENTER);

        // --- Action Listeners ---
        loginButton.addActionListener(new LoginListener());
        registerButton.addActionListener(e -> {
            new RegistrationGUI(userManager).setVisible(true);
        });

        showResetButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Reset"); // Switch to reset panel
            resetUsernameField.setText(loginUsernameField.getText()); // Pre-fill username
            resetNewPasswordField.setText("");
            resetConfirmPasswordField.setText("");
            setTitle("Hostel Management System - Reset Password"); // Update frame title
        });

        resetPasswordButton.addActionListener(e -> {
            resetUserPassword();
        });

        backToLoginButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Login"); // Switch back to login panel
            loginPasswordField.setText(""); // Clear password field
            setTitle("Hostel Management System"); // Restore frame title
        });

        // Allow Enter key to trigger login
        loginPasswordField.addActionListener(new LoginListener());

        // Allow Enter key to trigger reset password
        resetConfirmPasswordField.addActionListener(e -> resetUserPassword());
    }

    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginUsernameField.getText().trim();
            String password = new String(loginPasswordField.getPassword());

            if (userManager.authenticate(username, password)) {
                SwingUtilities.invokeLater(() -> {
                    new MainMenuGUI(userManager).setVisible(true);
                });
                dispose(); // Close login window
            } else {
                JOptionPane.showMessageDialog(LoginGUI.this,
                        "Invalid username or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                loginPasswordField.setText(""); // Clear password field
            }
        }
    }

    private void resetUserPassword() {
        String username = resetUsernameField.getText().trim();
        String newPass = new String(resetNewPasswordField.getPassword());
        String confirmPass = new String(resetConfirmPasswordField.getPassword());

        if (username.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userManager.resetPassword(username, newPass)) {
            JOptionPane.showMessageDialog(this, "Password reset successfully. You can now log in with your new password.", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(cardPanel, "Login"); // Switch back to login panel on success
            loginUsernameField.setText(username); // Pre-fill username for login
            loginPasswordField.setText(""); // Clear password field
            resetNewPasswordField.setText("");
            resetConfirmPasswordField.setText("");
            setTitle("Hostel Management System"); // Restore frame title
        } else {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}