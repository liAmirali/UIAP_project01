package Console;

import Main.Doctor;
import Main.Hospital;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public non-sealed class AdminConsole extends HospitalConsole {
    private final Hospital hospital = Hospital.getInstance();

    public AdminConsole() {}

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

        System.out.println("#### " + hospital.getName() + " :: Admin Panel ####");
        System.out.println("\n(1) Add a doctor");
        System.out.println("(2) Add medicine");
        System.out.println("(3) Print all doctors");
        System.out.println("(4) Print all medicines");
        System.out.println("(5) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();
        if (menuCode.equals("1")) showDoctorRegistrationPage();
        else if (menuCode.equals("2")) showAddMedicinePage();
        else if (menuCode.equals("3")) hospital.getConsole().doctorConsole.printAllDoctors();
        else if (menuCode.equals("4")) hospital.getConsole().doctorConsole.printAllMedicines();
        else if (menuCode.equals("5")) shouldKeepRendering = false;
        else System.out.println("**** Error: Invalid menu code");
    }

    public void showDoctorRegistrationPage() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Registering a Doctor ####");

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Major: ");
        String major = input.nextLine();

        System.out.print("Secretary's name: ");
        String secretaryName = input.nextLine();

        Doctor newDoctor = hospital.registerDoctor(fullName, password, major, secretaryName);
        System.out.println("New doctor was registered successfully! Personnel ID: " + newDoctor.getPersonnelID());
    }

    void showAddMedicinePage() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Adding a new medicine ####");

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
}
