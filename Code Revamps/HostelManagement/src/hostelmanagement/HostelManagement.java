


package hostelmanagement;

import javax.swing.SwingUtilities;

public class HostelManagement {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserManager userManager = new UserManager();
            new LoginGUI(userManager).setVisible(true);
        });
    }
}