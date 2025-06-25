


package hostelmanagement;

import javax.swing.SwingUtilities;

public class HostelManagement {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//             MainMenuGUI mainMenu = new MainMenuGUI();
//             mainMenu.setVisible(true);
            UserManager userManager = new UserManager();
            new LoginGUI(userManager).setVisible(true);
        });
    }
}