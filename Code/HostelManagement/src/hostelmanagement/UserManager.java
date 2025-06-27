/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostelmanagement;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.util.*;

public class UserManager {
    private Map<String, String> users = new HashMap<>();

    public UserManager() {
        // Default users for initial testing
        users.put("admin", "admin123");
        users.put("john", "oldpass123"); // Dummy user for reset functionality
    }

//    public boolean addUser(String username, String password) {
//        if (users.containsKey(username)) {
//            return false; // User already exists
//        }
//        users.put(username, password);
//        return true;
//    }
    
    
        public boolean addUser(String username, String password) {
    // First check if username already exists
    if (usernameExists(username)) {
        return false;
    }
    
    // If username doesn't exist, add it to the file
    try (PrintWriter writer = new PrintWriter(new FileWriter("D:\\Programs\\SDA Project\\data.txt", true))) {
        writer.println(username + ":" + password);
        return true;
    } catch (IOException ee) {
        return false;
    }
}

public boolean usernameExists(String username) {
    String filePath = "D:\\\\Programs\\\\SDA Project\\\\data.txt";
    
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length >= 1 && parts[0].trim().equalsIgnoreCase(username)) {
                return true; // Username found
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading user file: " + e.getMessage());
    }
    return false; // Username not found
}
    
    
    
    
    
    
    
    
    
    
    
    


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    public boolean authenticate(String username, String password) {
//        // Authenticate by checking if the username exists and password matches
//        return password.equals(users.get(username));
//    }
    

    public boolean authenticate(String username, String password) {
    String filePath = "D:\\Programs\\SDA Project\\data.txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;

        while ((line = reader.readLine()) != null) {
            // Each line is in format: username:password
            String[] parts = line.split(":");
            
            if (parts.length == 2) {
                String storedUsername = parts[0].trim().toLowerCase();
                String storedPassword = parts[1].trim();

                if (storedUsername.equals(username.toLowerCase()) && storedPassword.equals(password)) {
                    return true; // Match found
                }
            }
        }
    } catch (IOException ee) {
        System.err.println("Error reading user file: " + ee.getMessage());
    }

    return false; // No match found after checking all lines
}

    
    
    
    
    
    
    // PREVIOUS DANIYALS METHOD
/*
    // THIS IS THE MISSING METHOD THAT NEEDS TO BE INCLUDED
    public boolean resetPassword(String username, String newPassword) {
        if (!users.containsKey(username)) {
            return false; // User not found, cannot reset password
        }
        users.put(username, newPassword); // Update the password
        return true;
    }
*/
   
    public boolean resetPassword(String username, String newPassword) {
    String filePath = "E:/users/users.txt";
    List<String> updatedLines = new ArrayList<>();
    boolean found = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                String fileUsername = parts[0].trim();

                if (fileUsername.equals(username)) {
                    // Username found, update the password
                    updatedLines.add(username + ":" + newPassword);
                    found = true;
                } else {
                    // Keep existing user record
                    updatedLines.add(line);
                }
            } else {
                // In case of badly formatted line, just keep it
                updatedLines.add(line);
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
        return false;
    }

    if (!found) {
        return false; // Username not found
    }

    // Write updated data back to the file
    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
        for (String updatedLine : updatedLines) {
            writer.println(updatedLine);
        }
    } catch (IOException e) {
        System.err.println("Error writing to file: " + e.getMessage());
        return false;
    }

    return true; // Password reset and file updated at end
}

 
    
    
    
    
    
    
    
    
    
}
