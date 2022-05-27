package Console;

import Controllers.DoctorController;
import Controllers.HospitalController;
import Controllers.WatchmanController;
import Main.Hospital;
import User.Doctor;
import User.Watchman;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public non-sealed class AdminConsole extends HospitalConsole {
    void showAdminLoginPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        shouldKeepRendering = true;

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Login as Admin ####");
        System.out.print("\nEnter your username: ");
        String username = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        if (HospitalController.loginAdmin(username, password)) {
            System.out.println("Logged in successfully.");
            waitOnEnter();
            while (shouldKeepRendering) showAdminPanel();
        } else {
            System.out.println("Username or password is incorrect.");
            waitOnEnter();
        }
    }

    void showAdminPanel() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Admin Panel ####");

        // If there is no watchman available, admin has to create one
        if (Hospital.getInstance().getWatchmen().size() == 0) {
            System.out.println("** You have to add a watchman for the hospital.");

            System.out.println();
            showAddWatchmanPage();

            // Manually logging in the created watchman
            Watchman justCreatedWatchman = Hospital.getInstance().getLoggedInWatchman();
            Hospital.getInstance().setLoggedInWatchman(justCreatedWatchman);
            justCreatedWatchman.setTempCheckInTime(Hospital.getInstance().);
        }

        System.out.println("\n(1) Add a doctor");
        System.out.println("(2) Add medicine");
        System.out.println("(3) Print all doctors");
        System.out.println("(4) Print all medicines");
        System.out.println("(5) Change Date");
        System.out.println("(6) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();

        if (menuCode.equals("1")) showDoctorRegistrationPage();
        else if (menuCode.equals("2")) showAddMedicinePage();
        else if (menuCode.equals("3")) Hospital.getInstance().getConsole().doctorConsole.printAllDoctors();
        else if (menuCode.equals("4")) Hospital.getInstance().getConsole().doctorConsole.printAllMedicines();
        else if (menuCode.equals("5")) showDateChangePage();
        else if (menuCode.equals("6")) {
            HospitalController.checkoutEmployee(Hospital.getInstance().getAdmin().getPersonnelID());
            shouldKeepRendering = false;
        } else {
            System.out.println("**** Error: Invalid menu code");
            waitOnEnter();
        }
    }

    public void showDoctorRegistrationPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Registering a Doctor ####");

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        String username;
        while (true) {
            System.out.print("Username: D");
            username = 'D' + input.nextLine();

            if (DoctorController.usernameExists(username)) {
                System.out.println("This username already exists. Try another one.");
                continue;
            }
            break;
        }

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Major: ");
        String major = input.nextLine();

        System.out.println("Phone Number:");
        String phoneNumber = input.nextLine();

        System.out.println("Mandatory work time (hour): ");
        int mandatoryWorkHour = input.nextInt();
        input.nextLine();

        System.out.println("Hourly wage: ");
        int hourlyWage = input.nextInt();
        input.nextLine();

        System.out.println("Biography: ");
        String biography = input.nextLine();

        Doctor newDoctor = DoctorController.registerDoctor(fullName, username, password, phoneNumber, email, mandatoryWorkHour, hourlyWage, major, biography);
        System.out.println("New doctor was registered successfully! Personnel ID: " + newDoctor.getPersonnelID());

        waitOnEnter();
    }

    void showAddMedicinePage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Adding a new medicine ####");

        System.out.print("\nMedicine Name: ");
        String name = input.nextLine();

        System.out.print("Price: ");
        double price = input.nextDouble();
        input.nextLine();

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
            HospitalController.addNewMedicine(name, price, productionDate, expirationDate);
            System.out.println("Medicine was added successfully.");
        }

        waitOnEnter();
    }

    void showAddWatchmanPage() {
        clearConsole();

        Scanner input = new Scanner(System.in);

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Adding a watchman ####");

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        String username;
        while (true) {
            System.out.print("Username: W");
            username = 'W' + input.nextLine();

            if (WatchmanController.usernameExists(username)) {
                System.out.println("This username already exists. Try another one.");
                continue;
            }
            break;
        }

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = input.nextLine();

        System.out.print("Mandatory work time (hour): ");
        int mandatoryWorkHour = input.nextInt();
        input.nextLine();

        System.out.print("Hourly wage: ");
        int hourlyWage = input.nextInt();
        input.nextLine();

        Watchman newWatchman = HospitalController.addWatchman(fullName, username, password, phoneNumber, email, mandatoryWorkHour, hourlyWage);
        System.out.println("New watchman was hired successfully! Personnel ID: " + newWatchman.getPersonnelID());
        System.out.println();

        waitOnEnter();
    }

    void showDateChangePage() {
        clearConsole();

        Scanner input = new Scanner(System.in);

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Changing date ####");

        System.out.println("Enter the date you want to jump into (YYYY-MM-DD): ");
        String dateStringToJumpInto = input.nextLine();

//        TODO: time only goes forward

        LocalDate dateToJumpInto = LocalDate.parse(dateStringToJumpInto);
        HospitalController.changeDate(dateToJumpInto);

        System.out.println("Successfully changed date!");

        waitOnEnter();
    }
}
