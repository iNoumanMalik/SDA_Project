package hostelmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class EmployeeManagementGUI extends JFrame {
    private final EmployeeManager employeeManager;
    private DefaultListModel<Employee> employeeListModel;
    private JList<Employee> employeeJList;

    public EmployeeManagementGUI(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
        initializeUI();
        loadEmployees();
    }

    private void initializeUI() {
        setTitle("Employee Management");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(20, 30, 48), getWidth(), getHeight(), new Color(36, 59, 85));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("EMPLOYEE MANAGEMENT", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        mainPanel.add(title, BorderLayout.NORTH);

        employeeListModel = new DefaultListModel<>();
        employeeJList = new JList<>(employeeListModel);
        employeeJList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        employeeJList.setBackground(new Color(50, 60, 70));
        employeeJList.setForeground(Color.WHITE);
        mainPanel.add(new JScrollPane(employeeJList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.setOpaque(false);

        buttonPanel.add(createButton("Add", new Color(106, 17, 203), new Color(37, 117, 252), e -> addEmployee()));
        buttonPanel.add(createButton("Update", new Color(131, 58, 180), new Color(193, 53, 132), e -> updateEmployee()));
        buttonPanel.add(createButton("Remove", new Color(194, 21, 0), new Color(255, 197, 0), e -> removeEmployee()));
        buttonPanel.add(createButton("Close", new Color(0, 0, 0), new Color(50, 50, 50), e -> dispose()));

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JButton createButton(String text, Color start, Color end, ActionListener action) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(0, 0, start, getWidth(), getHeight(), end);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont().deriveFont(Font.BOLD, 14f));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        button.setPreferredSize(new Dimension(150, 40));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addActionListener(action);
        return button;
    }

    private void loadEmployees() {
        employeeListModel.clear();
        for (Employee emp : employeeManager.getAllEmployees()) {
            employeeListModel.addElement(emp);
        }
    }

    private void addEmployee() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField experienceField = new JTextField();

        Object[] fields = {
            "Name:", nameField,
            "Email:", emailField,
            "Phone:", phoneField,
            "Experience:", experienceField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Employee newEmp = new Employee(
                nameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                experienceField.getText()
            );
            if (employeeManager.addEmployee(newEmp)) {
                loadEmployees();
            } else {
                JOptionPane.showMessageDialog(this, "Employee already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateEmployee() {
        Employee selected = employeeJList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select an employee to update.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(selected.getName());
        JTextField emailField = new JTextField(selected.getEmail());
        JTextField phoneField = new JTextField(selected.getPhone());
        JTextField experienceField = new JTextField(selected.getExperience());

        Object[] fields = {
            "Name:", nameField,
            "Email:", emailField,
            "Phone:", phoneField,
            "Experience:", experienceField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Update Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            boolean updated = employeeManager.updateEmployee(
                selected.getName(),
                nameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                experienceField.getText()
            );
            if (updated) {
                loadEmployees();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. Duplicate name?", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeEmployee() {
        Employee selected = employeeJList.getSelectedValue();
        if (selected != null) {
            employeeManager.removeEmployee(selected);
            loadEmployees();
        } else {
            JOptionPane.showMessageDialog(this, "Select an employee to remove.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}
