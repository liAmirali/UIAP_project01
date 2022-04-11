import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

class HospitalConsole {
    Hospital hospital;
    boolean shouldKeepRendering;

    public HospitalConsole(Hospital hospital) {
        this.hospital = hospital;
    }

    static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    void showLoginRegisterPage() {
        Scanner input = new Scanner(System.in);
        System.out.println("#### Welcome to " + hospital.getName() + " ####");
        System.out.println("(1) Login as the admin");
        System.out.println("(2) Login as a doctor");
        System.out.println("(3) Login as a patient");
        System.out.println("(4) Register as a patient");
        System.out.println("(5) Exit");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();
        if (menuCode.equals("1")) showAdminLoginPage();
        else if (menuCode.equals("2")) showDoctorLoginPage();
        else if (menuCode.equals("3")) showPatientLoginPage();
        else if (menuCode.equals("4")) showPatientRegistrationPage();
        else if (menuCode.equals("5")) System.exit(0);
        else System.out.println("**** Error: Invalid menu code");
    }

    //  Admin pages
    void showAdminLoginPage() {
        Scanner input = new Scanner(System.in);

        shouldKeepRendering = true;

        System.out.println("#### " + hospital.getName() + " :: Login as Admin ####");
        System.out.print("\nEnter your username: ");
        String username = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        if (hospital.loginAdmin(username, password)) {
            System.out.println("Logged in successfully.");
            while (shouldKeepRendering) showAdminPanel();
        } else System.out.println("Username or password is incorrect.");
    }

    void showAdminPanel() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Admin Panel");
        System.out.println("\n(1) Add a doctor");
        System.out.println("(2) Add medicine");
        System.out.println("(3) Print all doctors");
        System.out.println("(4) Print all  medicines");
        System.out.println("(5) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();
        if (menuCode.equals("1")) showDoctorRegistrationPage();
        else if (menuCode.equals("2")) showAddMedicinePage();
        else if (menuCode.equals("3")) printAllDoctors();
        else if (menuCode.equals("4")) printAllMedicines();
        else if (menuCode.equals("5")) shouldKeepRendering = false;
        else System.out.println("**** Error: Invalid menu code");
    }

    void showAddMedicinePage() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Adding a new medicine");

        System.out.print("\nMedicine Name: ");
        String name = input.nextLine();

        System.out.print("Price: ");
        double price = input.nextDouble();

        // To ignore the newline
        input.nextLine();

        System.out.print("Production Date (yyyy-MM-dd HH:mm:ss): ");
        String productionDate = input.nextLine();

        System.out.print("Expiration Date (yyyy-MM-dd HH:mm:ss): ");
        String expirationDate = input.nextLine();

        // Time validation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime productionDT = LocalDateTime.parse(productionDate, formatter);
        LocalDateTime expirationDT = LocalDateTime.parse(expirationDate, formatter);
        LocalDateTime rightNowDT = LocalDateTime.now();

        if (productionDT.isAfter(expirationDT)) {
            System.out.println("Production time cannot be after the expiration time!");
        } else if (rightNowDT.isAfter(expirationDT)) {
            System.out.println("You can't add medicines that are already expired!");
        } else {
            hospital.addNewMedicine(name, price, productionDate, expirationDate);
            System.out.println("Medicine was added successfully.");
        }
    }

    //  Doctor pages
    void printAllDoctors() {
        ArrayList<Doctor> allDoctors = hospital.getDoctors();
        System.out.println("*** All Doctors in " + hospital.getName() + ":");
        for (Doctor doctor : allDoctors) {
            System.out.println(doctor.toString());
        }
    }

    void printAllMedicines() {
        ArrayList<Medicine> allMedicines = hospital.getMedicines();
        System.out.println("*** All medicines in " + hospital.getName() + ":");
        for (Medicine medicine : allMedicines) {
            System.out.println(medicine.toString());
        }
    }

    void showDoctorLoginPage() {
        Scanner input = new Scanner(System.in);
        shouldKeepRendering = true;

        System.out.println("#### " + hospital.getName() + " :: Login as a Doctor ####");

        System.out.print("\nPersonnel ID: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        if (hospital.loginDoctor(username, password)) while (shouldKeepRendering) showDoctorPanel();
        else System.out.println("Username or password is incorrect!");
    }

    void showDoctorPanel() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Doctor Panel");
        System.out.println("\n(1) Print all patients");
        System.out.println("(2) Print all medicines");
        System.out.println("(3) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();
        if (menuCode.equals("1")) {
        } else if (menuCode.equals("2")) printAllMedicines();
        else if (menuCode.equals("3")) shouldKeepRendering = false;
        else System.out.println("**** Error: Invalid menu code");
    }

    //  Patient pages
    void showPatientRegistrationPage() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Registering as a Patient ####");

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Phone number: ");
        String phoneNumber = input.nextLine();

        String fileNumber = hospital.registerPatient(fullName, password, phoneNumber);
        System.out.println("Patient was registered successfully! File Number: " + fileNumber);
    }

    void showPatientLoginPage() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Logging in as a patient ####");

        System.out.println("\nFile number: ");
        String fileNumber = input.nextLine();

        System.out.println("Password: ");
        String password = input.nextLine();

        if (hospital.loginPatient(fileNumber, password)) {
            System.out.println("Logged in successfully!");
            while (shouldKeepRendering) showPatientPanel();
        } else System.out.println("Username or password is incorrect!");
    }

    void showPatientPanel() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Patient Panel ####");

        System.out.println("(1) Take an appointment");
        System.out.println("(2) Edit info");
        System.out.println("(3) Print and filter doctors");
        System.out.println("(4) Make an appointment");
        System.out.println("(5) Print all prescriptions");
        System.out.println("(6) Logout");

        System.out.println("\nEnter menu code: ");
        String menuCode = input.nextLine();

        if (menuCode.equals("1")) {
        } else if (menuCode.equals("2")) showPatientProfileEdit();
        else if (menuCode.equals("3")) {
        } else if (menuCode.equals("4")) {
        } else if (menuCode.equals("5")) {
        } else if (menuCode.equals("6")) {
        }
    }

    void showPatientProfileEdit() {
        Scanner input = new Scanner(System.in);
        System.out.println("Editing profile:\n");

        System.out.println("Enter your new full name (leave empty to keep the old data): ");
        String newFullName = input.nextLine();

        System.out.println("Enter your new password (leave empty to keep the old data): ");
        String newPassword = input.nextLine();

        System.out.println("Enter your new phone number (leave empty to keep the old data): ");
        String newPhoneNumber = input.nextLine();


    }

    //  Doctor pages
    void showDoctorRegistrationPage() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Registering a Doctor ####");

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Major: ");
        String major = input.nextLine();

        String personnelIDOfNewDoctor = hospital.registerDoctor(fullName, password, major);
        System.out.println("New doctor was registered successfully! Personnel ID: " + personnelIDOfNewDoctor);
    }
}