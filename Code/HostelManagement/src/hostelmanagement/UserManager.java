package hostelmanagement;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String USER_FILE = "users.txt"; // Relative path
    private static final String CONFIG_FILE = "config.properties"; // Configuration file

    public UserManager() {
        // Create the users file if it doesn't exist
        createUserFileIfNotExists();
    }

    private void createUserFileIfNotExists() {
        File file = new File(getUserFilePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
                // Add default admin user
                addUser("admin", "admin123");
            } catch (IOException e) {
                System.err.println("Error creating user file: " + e.getMessage());
            }
        }
    }

    private String getUserFilePath() {
        // Check for custom path in config file
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("user.file.path", USER_FILE);
        } catch (IOException e) {
            return USER_FILE; // Default if config doesn't exist
        }
    }

    public boolean addUser(String username, String password) {
        if (usernameExists(username)) {
            return false;
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(getUserFilePath(), true))) {
            writer.println(username + ":" + password);
            return true;
        } catch (IOException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    public boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(getUserFilePath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 1 && parts[0].trim().equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }
        return false;
    }

    public boolean authenticate(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(getUserFilePath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String storedUsername = parts[0].trim();
                    String storedPassword = parts[1].trim();
                    if (storedUsername.equalsIgnoreCase(username) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }
        return false;
    }

    public boolean resetPassword(String username, String newPassword) {
        String filePath = getUserFilePath();
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String fileUsername = parts[0].trim();
                    if (fileUsername.equalsIgnoreCase(username)) {
                        updatedLines.add(username + ":" + newPassword);
                        found = true;
                    } else {
                        updatedLines.add(line);
                    }
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }

        if (!found) return false;

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String updatedLine : updatedLines) {
                writer.println(updatedLine);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }
}