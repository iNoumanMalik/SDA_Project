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
        // Default user for testing
        users.put("admin", "admin123");
    }

    public boolean addUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // user already exists
        }
        users.put(username, password);
        return true;
    }

    public boolean authenticate(String username, String password) {
        return password.equals(users.get(username));
    }
}
