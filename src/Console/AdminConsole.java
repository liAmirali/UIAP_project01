package Console;

import Controllers.DoctorController;
import Controllers.HospitalController;
import Controllers.JanitorController;
import Controllers.WatchmanController;
import Main.Hospital;
import User.Doctor;
import User.Janitor;
import User.Watchman;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public non-sealed class AdminConsole extends HospitalConsole {
    public void showAdminLoginPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        shouldKeepRendering = true;

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Login as Admin ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

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

    public void showAdminPanel() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Admin Panel ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        System.out.println("(1) Add medicine");
        System.out.println("(2) Print all doctors");
        System.out.println("(3) Print all medicines");
        System.out.println("(4) Change date");
        System.out.println("(5) Add new employee");
        System.out.println("(6) Accept breakdown fixtures");
        System.out.println("(7) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();

        if (menuCode.equals("1")) showAddMedicinePage();
        else if (menuCode.equals("2")) Hospital.getInstance().getConsole().doctorConsole.printAllDoctors();
        else if (menuCode.equals("3")) Hospital.getInstance().getConsole().doctorConsole.printAllMedicines();
        else if (menuCode.equals("4")) showDateChangePage();
        else if (menuCode.equals("5")) showAddNewEmployeePage();
        else if (menuCode.equals("6")) showAcceptBreakdownFixturePage();
        else if (menuCode.equals("7")) shouldKeepRendering = false;
        else {
            System.out.println("**** Error: Invalid menu code");
            waitOnEnter();
        }
    }

    public void showAddNewEmployeePage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Adding a New Employee ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        System.out.println("\n(1) Add a doctor");
        System.out.println("(2) Add a janitor");
        System.out.println("(3) Add a watchman");
        System.out.println("(4) Back");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();

        switch (menuCode) {
            case "1":
                showDoctorRegistrationPage();
                break;
            case "2":
                showAddJanitorPage();
                break;
            case "3":
                showAddWatchmanPage();
                break;
            case "4":
                return;
            default:
                System.out.println("**** Error: Invalid menu code");
                waitOnEnter();
        }
    }

    public void showDoctorRegistrationPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Registering a Doctor ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

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

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Phone Number:");
        String phoneNumber = input.nextLine();

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Major: ");
        String major = input.nextLine();

        System.out.print("Mandatory work time (hour per day): ");
        int mandatoryWorkHour = input.nextInt();
        input.nextLine();

        System.out.print("Hourly wage: ");
        int hourlyWage = input.nextInt();
        input.nextLine();

        System.out.print("Biography: ");
        String biography = input.nextLine();

        Doctor newDoctor = DoctorController.registerDoctor(fullName, username, password, phoneNumber, email, mandatoryWorkHour, hourlyWage, major, biography);
        System.out.println("New doctor was registered successfully! Personnel ID: " + newDoctor.getPersonnelID());

        waitOnEnter();
    }

    public void showAddMedicinePage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Adding a New Medicine ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        System.out.print("\nMedicine Name: ");
        String name = input.nextLine();

        System.out.print("Price: ");
        double price = input.nextDouble();
        input.nextLine();

        System.out.print("Production Date (yyyy-MM-dd HH:mm:ss): ");
        String productionDate = input.nextLine();

        System.out.print("Expiration Date (yyyy-MM-dd HH:mm:ss): ");
        String expirationDate = input.nextLine();

        // Time validation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime productionDT = LocalDateTime.parse(productionDate, formatter);
        LocalDateTime expirationDT = LocalDateTime.parse(expirationDate, formatter);
        LocalDateTime rightNowDT = Hospital.getInstance().getHospitalTime().getTime();

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

    public void showAddWatchmanPage() {
        clearConsole();

        Scanner input = new Scanner(System.in);

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Adding a Watchman ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

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

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = input.nextLine();

        System.out.print("Mandatory work time (hour per day): ");
        int mandatoryWorkHour = input.nextInt();
        input.nextLine();

        System.out.print("Hourly wage: ");
        int hourlyWage = input.nextInt();
        input.nextLine();

        Watchman newWatchman = WatchmanController.addWatchman(fullName, username, password, phoneNumber, email, mandatoryWorkHour, hourlyWage);
        System.out.println("New watchman was hired successfully! Personnel ID: " + newWatchman.getPersonnelID());

        waitOnEnter();
    }

    public void showAddJanitorPage() {
        clearConsole();

        Scanner input = new Scanner(System.in);

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Adding a Janitor ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        String username;
        while (true) {
            System.out.print("Username: J");
            username = 'J' + input.nextLine();

            if (JanitorController.usernameExists(username)) {
                System.out.println("This username already exists. Try another one.");
                continue;
            }
            break;
        }

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = input.nextLine();

        System.out.print("Mandatory work time (hour per day): ");
        int mandatoryWorkHour = input.nextInt();
        input.nextLine();

        System.out.print("Hourly wage: ");
        int hourlyWage = input.nextInt();
        input.nextLine();

        Janitor newJanitor = JanitorController.addJanitor(fullName, username, password, phoneNumber, email, mandatoryWorkHour, hourlyWage);
        System.out.println("New janitor was hired successfully! Personnel ID: " + newJanitor.getPersonnelID());

        waitOnEnter();
    }

    public void showDateChangePage() {
        clearConsole();

        Scanner input = new Scanner(System.in);

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Changing Date ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        LocalDate dateToJumpInto;
        while (true) {
            System.out.println("Enter the date you want to jump into (YYYY-MM-DD): ");
            String dateStringToJumpInto = input.nextLine();
            dateToJumpInto = LocalDate.parse(dateStringToJumpInto);

            if (dateToJumpInto.atStartOfDay().isBefore(LocalDateTime.now())) {
                System.out.println("You can only move time forward.");
                continue;
            }
            break;
        }

        HospitalController.changeDate(dateToJumpInto);

        System.out.println("Successfully changed date!");
        waitOnEnter();
    }

    public void showAcceptBreakdownFixturePage() {
        clearConsole();

        Scanner input = new Scanner(System.in);

        Hospital.getInstance().getConsole().janitorConsole.printReportedBreakdowns();

        System.out.print("Enter breakdown fixture code to accept: (-1 to exit)");
        int code = input.nextInt();

        if (code == -1) return;

        if (code >= 0 && code < Janitor.getHospitalBreakdowns().size()) {
            Janitor.getHospitalBreakdowns().get(code).setAccepted(true);

            System.out.println("Successfully accepted!");
            waitOnEnter();
        } else {
            System.out.println("Code not found.");

            waitOnEnter();
        }
    }
}
