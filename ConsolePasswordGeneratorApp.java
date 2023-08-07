import java.security.SecureRandom;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsolePasswordGeneratorApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("************************************");
            System.out.println("** * Password Generator App * * **");
            System.out.println("************************************");
            System.out.println("* 1. Generate Password");
            System.out.println("* 2. Check Password Strength");
            System.out.println("* 3. Calculate Time to Crack Password");
            System.out.println("* 4. Exit");
            System.out.print("* Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    generatePassword(scanner);
                    break;
                case 2:
                    checkPasswordStrength(scanner);
                    break;
                case 3:
                    calculateTimeToCrack(scanner);
                    break;
                case 4:
                    System.out.println("* Exiting the application. Goodbye! *");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("* Invalid choice. Please try again. *");
            }
        }
    }

    private static void generatePassword(Scanner scanner) {
        System.out.print("* Enter password length: ");
        int passwordLength = scanner.nextInt();

        String generatedPassword = generateRandomPassword(passwordLength);
        System.out.println("* Generated Password: " + generatedPassword);
    }

    private static String generateRandomPassword(int length) {
        final String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+";
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(charset.length());
            char randomChar = charset.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }

    private static void checkPasswordStrength(Scanner scanner) {
        System.out.print("* Enter password: ");
        String password = scanner.nextLine();

        String strength = checkPasswordStrength(password);
        System.out.println("* Password Strength: " + strength);
    }

    private static String checkPasswordStrength(String password) {
        int score = 0;

        if (password.length() >= 8) {
            score += 2;
            if (password.length() >= 12) {
                score += 3;
            }
        }

        if (Pattern.matches(".*[a-z].*", password)) {
            score++;
        }
        if (Pattern.matches(".*[A-Z].*", password)) {
            score++;
        }
        if (Pattern.matches(".*\\d.*", password)) {
            score++;
        }
        if (Pattern.matches(".*[@$!%*?&].*", password)) {
            score += 2;
        }

        if (score >= 8) {
            return "Very Strong";
        } else if (score >= 6) {
            return "Strong";
        } else if (score >= 4) {
            return "Medium";
        } else {
            return "Weak";
        }
    }

    private static void calculateTimeToCrack(Scanner scanner) {
        System.out.print("* Enter password: ");
        String password = scanner.nextLine();

        String timeToCrack = estimateTimeToCrack(password);
        System.out.println("* Estimated Time to Crack: " + timeToCrack);
    }

    private static String estimateTimeToCrack(String password) {
        int length = password.length();
        int years = 0;

        if (length < 10) {
            return "* Password can be cracked in milliseconds. *";
        } else {
            if (Pattern.matches("^[a-z]+$", password) || Pattern.matches("^\\d+$", password)) {
                return "* Password can be cracked in milliseconds. *";
            }

            if (Pattern.matches(".*[A-Z].*", password)) {
                years += 100;
            }

            if (Pattern.matches(".*[@$!%*?&].*", password)) {
                years += 500;
            }

            years += (length - 10) * 100;

            return "* Password can be cracked in approximately " + years + " years. *";
        }
    }
}
