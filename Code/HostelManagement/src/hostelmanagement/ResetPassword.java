package resetpassword;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ResetPassword {

    // Simulated user database (in-memory)
    static Map<String, String> userDatabase = new HashMap<>();
    static Map<String, String> otpDatabase = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Adding a dummy user account for testing
        userDatabase.put("user@example.com", "OldPassword123");

        System.out.println("===================================");
        System.out.println("      Welcome to Hostel System     ");
        System.out.println("         Password Reset Page       ");
        System.out.println("===================================");

        System.out.print("Enter your registered Email: ");
        String email = scanner.nextLine();

        // Step 1: Check if email exists
        if (!userDatabase.containsKey(email)) {
            System.out.println("No account found with this email.");
            return;
        }

        // Step 2: Generate OTP
        String generatedOtp = generateOtp();
        otpDatabase.put(email, generatedOtp);

        System.out.println("An OTP has been sent to your registered email (simulated).");
        System.out.println("Your OTP is: " + generatedOtp); // For simulation purposes only

        // Step 3: User enters OTP
        System.out.print("Enter the OTP: ");
        String enteredOtp = scanner.nextLine();

        // Step 4: Verify OTP
        if (!enteredOtp.equals(generatedOtp)) {
            System.out.println("Invalid or expired OTP. Please try again.");
            return;
        }

        // Step 5: Ask user to enter new password
        System.out.println("OTP verified successfully.");
        System.out.println("Now enter your new password.");

        String newPassword;
        while (true) {
            System.out.print("Enter new password: ");
            String pass1 = scanner.nextLine();

            System.out.print("Confirm new password: ");
            String pass2 = scanner.nextLine();

            if (!pass1.equals(pass2)) {
                System.out.println("Passwords do not match. Try again.");
                continue;
            }

            if (!isStrongPassword(pass1)) {
                System.out.println("Password must be at least 8 characters long, contain a number and a symbol.");
                continue;
            }

            newPassword = pass1;
            break;
        }

        // Step 6: Update password
        userDatabase.put(email, newPassword);
        System.out.println("Your password has been successfully updated.");
        System.out.println("You can now login with your new password.");
    }

    // Method to generate a 6-digit OTP
    public static String generateOtp() {
        Random rand = new Random();
        int otp = 100000 + rand.nextInt(900000); // Always 6-digit
        return String.valueOf(otp);
    }

    // Method to check basic password strength
    public static boolean isStrongPassword(String password) {
        boolean hasNumber = false;
        boolean hasSymbol = false;

        if (password.length() < 8) {
            return false;
        }

        for (char ch : password.toCharArray()) {
            if (Character.isDigit(ch)) {
                hasNumber = true;
            }
            if (!Character.isLetterOrDigit(ch)) {
                hasSymbol = true;
            }
        }

        return hasNumber && hasSymbol;
    }
}



