import java.util.Scanner;

class HospitalConsole {
    Hospital hospital;

    public HospitalConsole(Hospital hospital) {
        this.hospital = hospital;
    }

    void showLoginRegisterPage() {
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
            showAdminLoginPage();
        } else if (menuCode.equals("2")) {
        } else if (menuCode.equals("3")) {
        } else if (menuCode.equals("4")) {
            showPatientRegistrationPage();
        } else {
            System.out.println("**** Error: Invalid menu code");
        }
    }

    //  Admin pages
    void showAdminLoginPage() {
        clearConsole();
        Scanner input = new Scanner(System.in);

        System.out.println("#### UIAP Hospital :: Login as Admin ####");
        System.out.print("\nEnter your username: ");
        String username = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        if (username.equals("Admin") && password.equals("Admin")) {
            System.out.println("Logged in successfully.");
            showAdminPanel();
        } else {
            System.out.println("Username or password is incorrect.");
        }
    }

    void showAdminPanel() {
        clearConsole();
        Scanner input = new Scanner(System.in);

        System.out.println("#### UIAP Hospital :: Admin Panel");
        System.out.println("\n(1) Add a doctor");
        System.out.println("(2) Add medicine");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.next();
        if (menuCode.equals("1")) {
            showDoctorRegistrationPage();
        } else if (menuCode.equals("2")) {
        } else {
            System.out.println("**** Error: Invalid menu code");
        }
    }

    //  Patient pages
    void showPatientRegistrationPage() {
        clearConsole();

        Scanner input = new Scanner(System.in);

        System.out.println("#### UIAP Hospital :: Registering as a Patient ####");

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Phone number: ");
        String phoneNumber = input.nextLine();

        hospital.registerPatient(fullName, password, phoneNumber);
    }

    //  Doctor pages
    void showDoctorRegistrationPage() {
        clearConsole();

        Scanner input = new Scanner(System.in);

        System.out.println("#### UIAP Hospital :: Registering a Doctor ####");

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Major: ");
        String major = input.nextLine();

        hospital.registerDoctor(fullName, password, major);
    }

    static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}