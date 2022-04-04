import java.util.Scanner;

class HospitalConsole {
    static void showLoginRegisterPage() {
        Scanner input = new Scanner(System.in);
        clearConsole();
        System.out.println("#### Welcome to UIAP Hospital ####");
        System.out.println("(1) Login as the admin");
        System.out.println("(2) Login as a doctor");
        System.out.println("(3) Login as a patient");
        System.out.println("(4) Register as a patient");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.next();
        if (menuCode.equals("1")) {
            System.out.print("Enter your username: ");
            String username = input.next();
            System.out.print("Enter your password: ");
            String password = input.next();
            if (username.equals("Admin") && password.equals("Admin")) {
                System.out.println("Logged in successfully.");
            } else {
                System.out.println("Username or password is incorrect.");
            }
        } else if (menuCode.equals("2")) {
        } else if (menuCode.equals("3")) {
        } else if (menuCode.equals("4")) {
        } else {
            System.out.println("**** Error: Invalid menu code");
        }
    }

    static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}