package hostelmanagement;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class UserManager {
    private static final String USER_DATA_FILENAME = "userdata.dat";
    private static final ReentrantLock fileLock = new ReentrantLock();
    private final Path userDataPath;
    private final String salt;

    public UserManager() {
        this.userDataPath = Paths.get(System.getProperty("user.home"), 
                                  ".hostelmanagement", 
                                  USER_DATA_FILENAME);
        this.salt = "static-salt-value"; // In production, use per-user salt
        
        // Ensure directory exists
        try {
            Files.createDirectories(userDataPath.getParent());
        } catch (IOException e) {
            throw new RuntimeException("Could not create user data directory", e);
        }
    }

    // Helper method to hash passwords
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }

    public boolean addUser(String username, String password) {
        fileLock.lock();
        try {
            if (usernameExists(username)) {
                return false;
            }
            
            try (BufferedWriter writer = Files.newBufferedWriter(userDataPath, 
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                writer.write(username + ":" + hashPassword(password) + "\n");
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to add user", e);
        } finally {
            fileLock.unlock();
        }
    }

    public boolean usernameExists(String username) {
        fileLock.lock();
        try {
            if (!Files.exists(userDataPath)) {
                return false;
            }
            
            try (BufferedReader reader = Files.newBufferedReader(userDataPath)) {
                return reader.lines()
                    .map(line -> line.split(":"))
                    .anyMatch(parts -> parts.length > 0 && 
                             parts[0].equalsIgnoreCase(username));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to check username", e);
        } finally {
            fileLock.unlock();
        }
    }

    public boolean authenticate(String username, String password) {
        fileLock.lock();
        try {
            if (!Files.exists(userDataPath)) {
                return false;
            }
            
            try (BufferedReader reader = Files.newBufferedReader(userDataPath)) {
                return reader.lines()
                    .map(line -> line.split(":"))
                    .filter(parts -> parts.length == 2)
                    .anyMatch(parts -> parts[0].equalsIgnoreCase(username) && 
                             parts[1].equals(hashPassword(password)));
            }
        } catch (IOException e) {
            throw new RuntimeException("Authentication failed", e);
        } finally {
            fileLock.unlock();
        }
    }

    public boolean resetPassword(String username, String newPassword) {
        fileLock.lock();
        try {
            List<String> updatedLines = new ArrayList<>();
            boolean found = false;
            
            if (Files.exists(userDataPath)) {
                try (BufferedReader reader = Files.newBufferedReader(userDataPath)) {
                    for (String line : reader.lines().toArray(String[]::new)) {
                        String[] parts = line.split(":");
                        if (parts.length == 2 && parts[0].equalsIgnoreCase(username)) {
                            updatedLines.add(username + ":" + hashPassword(newPassword));
                            found = true;
                        } else {
                            updatedLines.add(line);
                        }
                    }
                }
            }
            
            if (!found) {
                return false;
            }
            
            try (BufferedWriter writer = Files.newBufferedWriter(userDataPath, 
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                for (String line : updatedLines) {
                    writer.write(line + "\n");
                }
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException("Password reset failed", e);
        } finally {
            fileLock.unlock();
        }
    }
}