

/*    
                             STUDENT DATA

                            NAME: SARDAR ZAIN  
                            REGIS NO: SP23-BSE-013

                            USECASE: LOGIN

                            Login (Java)

                            This is a simple Java-based user login system that handles user 
                            authentication, failed login attempts, and role-based redirection.

                            ----------------------------------------

                            HOW THE COMPILE AND RUN

                            1. 
                            Save the file with the name:
                            LoginSystem.java

                            2. 
                            Compile the file using:
                            javac LoginSystem.java

                            3. 
                            Run the program using:
                            java LoginSystem

   */







import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class User {
    private String username;
    private String passwordHash;
    private String role;
    private int failedLoginAttempts;
    private boolean isLocked;
    private long lastLoginTimestamp;

    public User(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.failedLoginAttempts = 0;
        this.isLocked = false;
        this.lastLoginTimestamp = 0;
    }

    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }
    public int getFailedLoginAttempts() { return failedLoginAttempts; }
    public boolean isLocked() { return isLocked; }
    public long getLastLoginTimestamp() { return lastLoginTimestamp; }

    public void setFailedLoginAttempts(int attempts) { this.failedLoginAttempts = attempts; }
    public void setLocked(boolean locked) { this.isLocked = locked; }
    public void setLastLoginTimestamp(long timestamp) { this.lastLoginTimestamp = timestamp; }
}



class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public UserRepository() {
        // Sample users
        users.put("student1@example.com", new User("student1@example.com", "password123", "student"));
        users.put("admin@example.com", new User("admin@example.com", "adminpass", "admin"));
    }

    public User findByUsername(String username) {
        return users.get(username);
    }

    public void update(User user) {
        users.put(user.getUsername(), user);
    }
}


class AuthService {
    private static final int MAX_ATTEMPTS = 5;
    private final UserRepository repository = new UserRepository();

    public String login(String username, String password) {
        User user = repository.findByUsername(username);

        if (user == null) {
            return "User does not exist.";
        }

        if (user.isLocked()) {
            return "Your account is locked due to multiple failed login attempts.";
        }

        if (!user.getPasswordHash().equals(password)) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            if (user.getFailedLoginAttempts() >= MAX_ATTEMPTS) {
                user.setLocked(true);
                repository.update(user);
                return "Too many failed attempts. Your account is now locked.";
            }

            repository.update(user);
            return "Your password is incorrect.";
        }

        user.setFailedLoginAttempts(0);
        user.setLocked(false);
        user.setLastLoginTimestamp(System.currentTimeMillis());
        repository.update(user);

        return "Login successful. Redirecting to " + user.getRole() + " dashboard...";
    }
}


public class Login {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== User Login System ===");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String result = authService.login(username, password);
        System.out.println(result);
    }
}
