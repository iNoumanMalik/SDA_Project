/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelmanagement;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, String> users = new HashMap<>();

    public UserManager() {
        // Default users for initial testing
        users.put("admin", "admin123");
        users.put("john", "oldpass123"); // Dummy user for reset functionality
    }

    public boolean addUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // User already exists
        }
        users.put(username, password);
        return true;
    }

    public boolean authenticate(String username, String password) {
        // Authenticate by checking if the username exists and password matches
        return password.equals(users.get(username));
    }

    // THIS IS THE MISSING METHOD THAT NEEDS TO BE INCLUDED
    public boolean resetPassword(String username, String newPassword) {
        if (!users.containsKey(username)) {
            return false; // User not found, cannot reset password
        }
        users.put(username, newPassword); // Update the password
        return true;
    }
}
