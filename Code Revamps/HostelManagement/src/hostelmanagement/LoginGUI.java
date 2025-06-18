/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelmanagement;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private UserManager userManager;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Hostel Management Login");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new LoginListener());
        registerButton.addActionListener(e -> {
            new RegistrationGUI(userManager).setVisible(true);
        });

        // Press Enter in password field triggers login
        passwordField.addActionListener(new LoginListener());
    }

    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (userManager.authenticate(username, password)) {
                SwingUtilities.invokeLater(() -> {
                new MainMenuGUI(userManager).setVisible(true);
            });
                dispose();  // close login window
            } else {
                JOptionPane.showMessageDialog(LoginGUI.this,
                "Invalid username or password.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        }
    }
}
