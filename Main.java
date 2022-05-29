import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HospitalConsole.clearConsole();

        AdminConsole adminConsole = new AdminConsole();
        DoctorConsole doctorConsole = new DoctorConsole();
        WatchmanConsole watchmanConsole = new WatchmanConsole();
        JanitorConsole janitorConsole = new JanitorConsole();
        SecretaryConsole secretaryConsole = new SecretaryConsole();
        PatientConsole patientConsole = new PatientConsole();

        Admin admin = Admin.getInstance("Admin", "Admin", "A00");

        HospitalConsole myConsole = new HospitalConsole(adminConsole, doctorConsole, watchmanConsole, janitorConsole, secretaryConsole, patientConsole);
        Hospital.getInstance("UI-AP Hospital", myConsole, admin);

        System.out.println("You have to add a watchman for the setup.");
        HospitalConsole.waitOnEnter();
        adminConsole.showAddWatchmanPage();

//        Doctor testDoc = new Doctor("Amirali", "Damirali", "1234", "090", "323o23g", "1234", 8, 100, "CE", "winwienwpoebnwpeobn");
//        Hospital.getInstance().getDoctors().add(testDoc);
//        Secretary testSec = new Secretary("Sepehr", "Sepehr", "1234", "2003", "225", "1234", 3, 3, "D1234");
//        testDoc.setSecretary(testSec);
//
//        Patient testPa1 = new Patient("QQQ", "P10", "1234", "09", "322sdg", "P1234", "o3voi34vo b3o4vb4b3oi b4oi n");
//        Patient testPa2 = new Patient("EEE", "P11", "1234", "09", "322sdg", "P1111", "o3voi34vo b3o4vb4b3oi b4oi n");
//        Patient testPa3 = new Patient("LLL", "P12", "1234", "09", "322sdg", "P2222", "o3voi34vo b3o4vb4b3oi b4oi n");
//
//        LocalDateTime visitTime1 = LocalDateTime.parse("2022-05-29 17:20:00");
//        LocalDateTime visitTime2 = LocalDateTime.parse("2022-06-10 18:20:00");
//        LocalDateTime visitTime3 = LocalDateTime.parse("2022-05-30 19:20:00");
//
//        SecretaryController.fixAnAppointment("P1234", visitTime1, "D1234", true);
//        SecretaryController.fixAnAppointment("P1111", visitTime2, "D1234", false);
//        SecretaryController.fixAnAppointment("P2222", visitTime3, "D1234", true);
//
//        Hospital.getInstance().getPatients().add(testPa1);
//        Hospital.getInstance().getPatients().add(testPa2);
//        Hospital.getInstance().getPatients().add(testPa3);


        while (true) {
            Hospital.getInstance().getConsole().showMainMenu();
        }
    }
}

non-sealed class AdminConsole extends HospitalConsole {
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


non-sealed class DoctorConsole extends HospitalConsole {
    public void showDoctorPanel() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Doctor Panel ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        if (Hospital.getInstance().getLoggedInDoctor().getSecretary() == null) {
            System.out.println("*** You have to hire a secretary!");
            waitOnEnter();
            showAddSecretaryPage();
        }

        System.out.println("\n(1) Print all appointments");
        System.out.println("(2) Print all medicines");
        System.out.println("(3) Visit a patient");
        System.out.println("(4) Get month salary until now");
        System.out.println("(5) See worked days");
        System.out.println("(6) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();
        switch (menuCode) {
            case "1" -> printAllDoctorAppointments(Hospital.getInstance().getLoggedInDoctor());
            case "2" -> printAllMedicines();
            case "3" -> visitAPatient();
            case "4" -> {
                System.out.print("Your salary for this month until today: ");
                System.out.println(EmployeeController.getMonthSalary(Hospital.getInstance().getLoggedInDoctor().getPersonnelID()));
                waitOnEnter();
            }
            case "5" -> printWorkedDays(Hospital.getInstance().getLoggedInDoctor().getPersonnelID());
            case "6" -> {
                HospitalController.logoutEmployee(Hospital.getInstance().getLoggedInDoctor().getPersonnelID());
                shouldKeepRendering = false;
            }
            default -> {
                System.out.println("**** Error: Invalid menu code");
                waitOnEnter();
            }
        }
    }

    public void printAllDoctorAppointments(Doctor doctor) {
        clearConsole();

        System.out.println("Here is all the appointments:");

        for (Appointment appointment : doctor.getSecretary().getGivenAppointments())
            System.out.println(appointment.toString());

        waitOnEnter();
    }

    public void printAllMedicines() {
        clearConsole();

        ArrayList<Medicine> allMedicines = Hospital.getInstance().getMedicines();

        System.out.println("*** All medicines in " + Hospital.getInstance().getName() + ":");
        for (Medicine medicine : allMedicines)
            System.out.println(medicine.toString());

        waitOnEnter();
    }

    public void visitAPatient() {
        LocalDateTime nowDT = Hospital.getInstance().getHospitalTime().getTime();
        boolean examined = false;

        clearConsole();

        // Finding an appointment to visit right now
        for (Appointment appointment : Hospital.getInstance().getLoggedInDoctor().getSecretary().getGivenAppointments()) {
            if (appointment.getStartDateTime().isBefore(nowDT) && appointment.getEndDateTime().isAfter(nowDT)) {
                System.out.println("## You have a patient to visit!");
                String patientFileNumber = appointment.getPatientFileNumber();

                // Finding a patient with the same file number as the one in the appointment.
                Patient selectedPatient = null;
                for (Patient patient : Hospital.getInstance().getPatients())
                    if (patient.getFileNumber().equals(patientFileNumber)) {
                        selectedPatient = patient;
                        break;
                    }

                System.out.println("Patient information:");
                System.out.println(selectedPatient.toString());

                System.out.println();

                ArrayList<Medicine> selectedMedicines = new ArrayList<>();
                Scanner input = new Scanner(System.in);

                boolean medicineIDFound = false;
                while (true) {
                    System.out.println("Enter the medicine ID (type EXIT to leave):");
                    String enteredID = input.nextLine();

                    if (enteredID.equals("EXIT")) break;

                    for (Medicine medicine : Hospital.getInstance().getMedicines())
                        if (medicine.getID().equals(enteredID)) {
                            medicineIDFound = true;

                            if (medicine.getProductionDate().isAfter(nowDT)) {
                                System.out.println("This medicine has not been produced yet.");
                                waitOnEnter();
                                break;
                            }

                            if (medicine.getExpirationDate().isBefore(nowDT)) {
                                System.out.println("This medicine has been expired.");
                                waitOnEnter();
                                break;
                            }

                            selectedMedicines.add(medicine);
                            break;
                        }

                    if (!medicineIDFound) {
                        System.out.println("No medicine found with this ID");
                        waitOnEnter();
                    }
                }

                Random rand = new Random();
                String rxId = String.valueOf(Math.abs(rand.nextInt()));
                Rx prescription = new Rx(rxId, nowDT, selectedMedicines, appointment.getDoctorPersonnelID(), selectedPatient.getFileNumber());
                PatientController.addRx(selectedPatient.getFileNumber(), prescription);
                SecretaryController.removeAnAppointment(appointment.getDoctorPersonnelID(), appointment.getNumber());

                System.out.println("You examined the patient successfully!");
                waitOnEnter();
                examined = true;
            }
            if (examined) break;
        }
        if (!examined) {
            System.out.println("You don't have any patient to visit right now!");
            waitOnEnter();
        }
    }

    public void printAllDoctors() {
        clearConsole();

        ArrayList<Doctor> allDoctors = Hospital.getInstance().getDoctors();
        System.out.println("*** All Doctors in " + Hospital.getInstance().getName() + ":");
        for (Doctor doctor : allDoctors)
            System.out.println(doctor.toString());

        waitOnEnter();
    }

    public void showAddSecretaryPage() {
        clearConsole();

        Scanner input = new Scanner(System.in);

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Adding a Secretary ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        String username;
        while (true) {
            System.out.print("Username: S");
            username = 'S' + input.nextLine();

            if (SecretaryController.usernameExists(username)) {
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

        System.out.print("Mandatory work time (hour per day): ");
        int mandatoryWorkHour = input.nextInt();
        input.nextLine();

        System.out.print("Hourly wage: ");
        int hourlyWage = input.nextInt();
        input.nextLine();

        Secretary newSecretary = DoctorController.hireSecretary(fullName, username, password, phoneNumber, email, mandatoryWorkHour, hourlyWage, Hospital.getInstance().getLoggedInDoctor().getPersonnelID());
        System.out.println("New secretary was hired successfully! Personnel ID: " + newSecretary.getPersonnelID());

        waitOnEnter();
    }
}


sealed class HospitalConsole permits AdminConsole, DoctorConsole, WatchmanConsole, JanitorConsole, SecretaryConsole, PatientConsole {
    boolean shouldKeepRendering;

    AdminConsole adminConsole;
    DoctorConsole doctorConsole;
    WatchmanConsole watchmanConsole;
    JanitorConsole janitorConsole;
    SecretaryConsole secretaryConsole;

    PatientConsole patientConsole;

    public HospitalConsole() {
    }

    public HospitalConsole(AdminConsole adminConsole, DoctorConsole doctorConsole, WatchmanConsole watchmanConsole, JanitorConsole janitorConsole, SecretaryConsole secretaryConsole, PatientConsole patientConsole) {
        this.adminConsole = adminConsole;
        this.doctorConsole = doctorConsole;
        this.watchmanConsole = watchmanConsole;
        this.janitorConsole = janitorConsole;
        this.secretaryConsole = secretaryConsole;
        this.patientConsole = patientConsole;
    }

    public static void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name");
            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            }
        } catch (Exception ignored) {
        }
    }

    public static void waitOnEnter() {
        System.out.print("\nPress enter to continue...");
        (new Scanner(System.in)).nextLine();
    }

    public void showMainMenu() {
        clearConsole();

        if (Hospital.getInstance().getLoggedInWatchman() == null) showWatchmanLoginPage();
        else showLoginRegistrationPage();
    }

    public void showLoginRegistrationPage() {
        clearConsole();

        Scanner input = new Scanner(System.in);
        System.out.println("#### Welcome to " + Hospital.getInstance().getName() + " ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        System.out.println("(1) Login as the admin");
        System.out.println("(2) Login as an employee");
        System.out.println("(3) Login as a patient");
        System.out.println("(4) Register as a patient");
        System.out.println("(5) Change watchman shift");
        System.out.println("(6) Go to watchman panel");
        System.out.println("(7) Exit");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();
        switch (menuCode) {
            case "1" -> adminConsole.showAdminLoginPage();
            case "2" -> showEmployeeLoginPage();
            case "3" -> patientConsole.showPatientLoginPage();
            case "4" -> patientConsole.showPatientRegistrationPage();
            case "5" ->
                    HospitalController.logoutWatchman(Hospital.getInstance().getLoggedInWatchman().getPersonnelID());
            case "6" -> watchmanConsole.showWatchmanPanel();
            case "7" -> System.exit(0);
            default -> {
                System.out.println("**** Error: Invalid menu code");
                waitOnEnter();
            }
        }
    }

    public void showEmployeeLoginPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Employee Login ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        System.out.print("\nUsername: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        if (HospitalController.loginUser(username, password)) {
            if (username.charAt(0) == 'D') doctorConsole.showDoctorPanel();
            else if (username.charAt(0) == 'W') watchmanConsole.showWatchmanPanel();
            else if (username.charAt(0) == 'J') janitorConsole.showJanitorPanel();
            else if (username.charAt(0) == 'S') secretaryConsole.showSecretaryPanel();
        } else {
            System.out.println("Username or password is incorrect!");
            waitOnEnter();
        }
    }

    public void showWatchmanLoginPage() {
        clearConsole();

        Scanner input = new Scanner(System.in);
        System.out.println("#### " + Hospital.getInstance().getName() + " :: Watchman Login  ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        while (true) {
            System.out.print("\nUsername: ");
            String username = input.nextLine();

            System.out.print("Password: ");
            String password = input.nextLine();

            if (HospitalController.loginWatchman(username, password)) {
                System.out.println("Successfully logged in.");
                waitOnEnter();
                break;
            } else {
                System.out.println("Incorrect login or password!");
                waitOnEnter();
            }
        }
    }

    public void printWorkedDays(String personnelID) {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Worked Days ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        for (ArrivalRecord arrivalRecord : EmployeeController.getWorkedDays(personnelID)) {
            System.out.println(arrivalRecord);
            System.out.println("-------------------------------");
        }

        waitOnEnter();
    }

}


non-sealed class JanitorConsole extends HospitalConsole {
    public void showJanitorPanel() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Janitor Panel ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        Scanner input = new Scanner(System.in);

        System.out.println("(1) Report a breakdown");
        System.out.println("(2) See reported breakdowns");
        System.out.println("(3) See month salary until today");
        System.out.println("(4) Print worked days");
        System.out.println("(5) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();

        switch (menuCode) {
            case "1" -> showReportABreakDownPage();
            case "2" -> printReportedBreakdowns();
            case "3" -> {
                System.out.print("Your salary for this month until today: ");
                System.out.println(EmployeeController.getMonthSalary(Hospital.getInstance().getLoggedInJanitor().getPersonnelID()));
                waitOnEnter();
            }
            case "4" -> printWorkedDays(Hospital.getInstance().getLoggedInJanitor().getPersonnelID());
            case "5" -> {
                HospitalController.logoutEmployee(Hospital.getInstance().getLoggedInJanitor().getPersonnelID());
                shouldKeepRendering = false;
            }
            default -> {
                System.out.println("Invalid menu code. Try again.");
                waitOnEnter();
            }
        }
    }

    public void showReportABreakDownPage() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Report a Breakdown ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        Scanner input = new Scanner(System.in);
        String newEntry = input.nextLine();

        Janitor.getHospitalBreakdowns().add(new Breakdown(newEntry, false));

        System.out.println("Successfully added!");

        waitOnEnter();
    }

    public void printReportedBreakdowns() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Hospital Breakdowns ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        int counter = 0;
        for (Breakdown breakdown : Janitor.getHospitalBreakdowns()) {
            System.out.print("(" + (++counter) + ") ");
            System.out.println("[" + (breakdown.isAccepted() ? "X" : " ") + "] ");
            System.out.println(breakdown.getTitle());
        }

        System.out.println();
        System.out.println("Legend: [X] accepted");
        System.out.println("        [ ] not accepted");

        waitOnEnter();
    }
}


non-sealed class PatientConsole extends HospitalConsole {
    public void showPatientRegistrationPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Registering as a Patient ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        String username;
        while (true) {
            System.out.print("Username: P");
            username = 'P' + input.nextLine();

            if (PatientController.usernameExists(username)) {
                System.out.println("This username already exists. Try another one.");
                continue;
            }
            break;
        }

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Phone number: ");
        String phoneNumber = input.nextLine();

        System.out.println("How is everything about your health?");
        String descriptionOfProblem = input.nextLine();

        Patient newPatient = PatientController.registerPatient(fullName, username, email, password, phoneNumber, descriptionOfProblem);
        System.out.println("Patient was registered successfully! File Number: " + newPatient.getFileNumber());

        waitOnEnter();
    }

    public void showPatientLoginPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Logging in as a Patient ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        System.out.print("\nUsername: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        shouldKeepRendering = true;

        if (HospitalController.loginUser(username, password)) {
            System.out.println("Logged in successfully!");
            waitOnEnter();
            while (shouldKeepRendering) showPatientPanel();
        } else {
            System.out.println("Username or password is incorrect!");
            waitOnEnter();
        }
    }

    public void showPatientPanel() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Patient Panel ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        System.out.println("(1) Make an appointment");
        System.out.println("(2) Edit info");
        System.out.println("(3) Print all doctors");
        System.out.println("(4) Filter doctors by major");
        System.out.println("(5) Print all appointments");
        System.out.println("(6) Print all prescriptions");
        System.out.println("(7) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();

        switch (menuCode) {
            case "1" -> showMakingAnAppointmentPage();
            case "2" -> showPatientProfileEdit();
            case "3" -> Hospital.getInstance().getConsole().doctorConsole.printAllDoctors();
            case "4" -> showFilterDoctorPage();
            case "5" -> printAllPatientAppointments(Hospital.getInstance().getLoggedInPatient());
            case "6" -> printAllPatientPrescriptions(Hospital.getInstance().getLoggedInPatient());
            case "7" -> shouldKeepRendering = false;
            default -> {
                System.out.println("*** Invalid menu code. ***");
                waitOnEnter();
            }
        }
    }

    public void showPatientProfileEdit() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### Editing Profile ####\n");

        System.out.println("Enter your new full name (leave empty to keep the old data): ");
        String newFullName = input.nextLine();

        System.out.println("Enter your new password (leave empty to keep the old data): ");
        String newPassword = input.nextLine();

        System.out.println("Enter your new phone number (leave empty to keep the old data): ");
        String newPhoneNumber = input.nextLine();

        System.out.println("Enter any update about your health condition (leave empty to keep the old data): ");
        String descriptionOfProblem = input.nextLine();

        PatientController.editInfo(Hospital.getInstance().getLoggedInPatient().getFileNumber(), newFullName, newPassword, newPhoneNumber, descriptionOfProblem);

        System.out.println("Profile info was edited successfully");

        waitOnEnter();
    }

    public void showFilterDoctorPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("*** Enter the major to filter doctors:");
        String filteringMajor = input.nextLine();

        System.out.println("Result:");
        ArrayList<Doctor> filteredDoctors = DoctorController.filterDoctorsByMajor(filteringMajor);
        for (Doctor doctor : filteredDoctors) {
            System.out.println(doctor.toString());
        }

        waitOnEnter();
    }

    public void showMakingAnAppointmentPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println(Hospital.getInstance().getName() + " :: Making a New Appointment ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        String personnelID;
        Doctor selectedDoctor = null;
        boolean doctorFound = false;
        boolean doctorHasSecretary = false;
        while (!doctorFound || !doctorHasSecretary) {
            System.out.println("Enter the doctor personnel ID: ");
            personnelID = input.nextLine();
            for (Doctor doctor : Hospital.getInstance().getDoctors()) {
                if (doctor.getPersonnelID().equals(personnelID)) {
                    if (doctor.getSecretary() != null) doctorHasSecretary = true;
                    selectedDoctor = doctor;
                    doctorFound = true;
                    break;
                }
            }
            if (!doctorFound) {
                System.out.println("No doctor found with this personnel ID.");
                waitOnEnter();
                continue;
            }
            if (!doctorHasSecretary) {
                System.out.println("This doctor doesn't have a secretary.");
                waitOnEnter();
            }
        }

        String visitTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTimeDT;
        while (true) {
            try {
                System.out.println("Enter the time you want to be visited (yyyy-MM-dd HH:mm:ss): ");
                visitTime = input.nextLine();
                startTimeDT = LocalDateTime.parse(visitTime, formatter);

                if (!SecretaryController.appointmentTimeIsFree(selectedDoctor.getPersonnelID(), visitTime)) {
                    System.out.println("Sorry! This time is already taken or the time has passed.");
                    System.out.println("Also there maybe more than 10 appointments in this week.");
                    waitOnEnter();
                    continue;
                }

                break;
            } catch (DateTimeParseException e) {
                System.out.println("You probably didn't enter time format correctly.");

            }
        }

        String isEmergencyResponse;
        boolean isEmergency;
        while (true) {
            System.out.println("Is your situation emergency? (y/n): ");
            isEmergencyResponse = input.nextLine();

            if (isEmergencyResponse.equals("y")) {
                isEmergency = true;
                break;
            } else if (isEmergencyResponse.equals("n")) {
                isEmergency = false;
                break;
            } else System.out.println("Enter either \"y\" or \"n\".");
        }

        Appointment fixedAppointment = SecretaryController.fixAnAppointment(Hospital.getInstance().getLoggedInPatient().getFileNumber(), startTimeDT, selectedDoctor.getPersonnelID(), isEmergency);
        System.out.println("Your appointment has been fixed! Number:" + fixedAppointment.getNumber());
        waitOnEnter();
    }

    public void printAllPatientAppointments(Patient patient) {
        clearConsole();

        System.out.println("Here is all your appointments:");

        for (Appointment appointment : patient.getAppointments())
            System.out.println(appointment.toString());

        waitOnEnter();
    }

    public void printAllPatientPrescriptions(Patient patient) {
        clearConsole();

        System.out.println("Here is all you prescriptions:");

        for (Rx rx : patient.getPrescriptions())
            System.out.println(rx.toString());

        waitOnEnter();
    }
}


non-sealed class SecretaryConsole extends HospitalConsole {
    public void showSecretaryPanel() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Secretary Panel ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        Scanner input = new Scanner(System.in);

        System.out.println("(1) See sorted appointments");
        System.out.println("(2) See today appointments");
        System.out.println("(3) See this week appointments");
        System.out.println("(4) See month salary until today");
        System.out.println("(5) See worked days");
        System.out.println("(6) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();

        switch (menuCode) {
            case "1" -> printSortedAppointments();
            case "2" -> printAppointmentsByDay();
            case "3" -> printAppointmentsByWeek();
            case "4" -> {
                System.out.print("Your salary for this month until today: ");
                System.out.println(EmployeeController.getMonthSalary(Hospital.getInstance().getLoggedInSecretary().getPersonnelID()));
                waitOnEnter();
            }
            case "5" -> printWorkedDays(Hospital.getInstance().getLoggedInSecretary().getPersonnelID());
            case "6" -> {
                HospitalController.logoutEmployee(Hospital.getInstance().getLoggedInSecretary().getPersonnelID());
                shouldKeepRendering = false;
            }
            default -> {
                System.out.println("Invalid menu code. Try again.");

                waitOnEnter();
            }
        }
    }

    public void printSortedAppointments() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Sorted Appointments ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        ArrayList<Appointment> sortedAppointments = SecretaryController.getSortedAppointments(Hospital.getInstance().getLoggedInSecretary().getPersonnelID());

        for (Appointment appointment : sortedAppointments)
            System.out.println(appointment);

        waitOnEnter();
    }

    public void printAppointmentsByDay() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Today Appointments ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        ArrayList<Appointment> appointments = SecretaryController.getAppointmentsByDay(Hospital.getInstance().getLoggedInSecretary().getPersonnelID(), Hospital.getInstance().getHospitalTime().getTime().toLocalDate());

        for (Appointment appointment : appointments)
            System.out.println(appointment);

        waitOnEnter();
    }

    public void printAppointmentsByWeek() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: This week Appointments ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        ArrayList<Appointment> appointments = SecretaryController.getAppointmentsByWeek(Hospital.getInstance().getLoggedInSecretary().getPersonnelID(), Hospital.getInstance().getHospitalTime().getTime().toLocalDate());

        for (Appointment appointment : appointments)
            System.out.println(appointment);

        waitOnEnter();
    }
}


non-sealed class WatchmanConsole extends HospitalConsole {
    public void showWatchmanPanel() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Watchman Panel ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        Scanner input = new Scanner(System.in);

        System.out.println("(1) List today's check-ins/departures");
        System.out.println("(2) See worked days");
        System.out.println("(3) Get month salary until today");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();

        switch (menuCode) {
            case "1" -> printTodayArrivalRecords();
            case "2" -> printWorkedDays(Hospital.getInstance().getLoggedInWatchman().getPersonnelID());
            case "3" -> {
                System.out.print("Your salary for this month until today: ");
                System.out.println(EmployeeController.getMonthSalary(Hospital.getInstance().getLoggedInWatchman().getPersonnelID()));
                waitOnEnter();
            }
            default -> {
                System.out.println("Invalid menu code. Try again.");
                waitOnEnter();
            }
        }
    }

    public void printTodayArrivalRecords() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Today Arrival Records ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        LocalDateTime hospitalTime = Hospital.getInstance().getHospitalTime().getTime();

        for (ArrivalRecord arrivalRecord : Watchman.getArrivalRecords()) {
            if (arrivalRecord.getArrivalTime().getDayOfYear() == hospitalTime.getDayOfYear() && arrivalRecord.getArrivalTime().getYear() == hospitalTime.getYear()) {
                System.out.println(arrivalRecord);
            }
        }

        waitOnEnter();
    }
}


class DoctorController {
    public static ArrayList<Doctor> filterDoctorsByMajor(String major) {
        ArrayList<Doctor> filteredDoctors = new ArrayList<>();
        for (Doctor doctor : Hospital.getInstance().getDoctors()) {
            if (doctor.getMajor().equals(major)) filteredDoctors.add(doctor);
        }

        return filteredDoctors;
    }

    public static Doctor registerDoctor(String fullName, String username, String password, String phoneNumber, String email, int mandatoryWorkHour, int hourlyWage, String major, String biography) {
        Random rand = new Random();
        String personnelID;
        while (true) {
            personnelID = String.valueOf(Math.abs(rand.nextInt()));
            if (doctorPersonnelIDExists(personnelID)) continue;
            break;
        }

        Doctor newDoctor = new Doctor(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage, major, biography);
        Hospital.getInstance().getDoctors().add(newDoctor);
        return newDoctor;
    }

    public static Secretary hireSecretary(String fullName, String username, String password, String phoneNumber, String email, int mandatoryWorkHour, int hourlyWage, String doctorPersonnelID) {
        Random rand = new Random();
        String personnelID;
        while (true) {
            personnelID = String.valueOf(Math.abs(rand.nextInt()));
            if (SecretaryController.secretaryPersonnelIDExists(personnelID)) continue;
            break;
        }

        Secretary newSecretary = new Secretary(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage, doctorPersonnelID);
        try {
            Doctor employerDoctor = findDoctorWithPersonnelID(doctorPersonnelID);
            employerDoctor.setSecretary(newSecretary);
            return newSecretary;
        } catch (DoctorPersonnelIDNotExistsException e) {
            return null;
        }
    }

    public static boolean doctorPersonnelIDExists(String personnelIDToCheck) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getPersonnelID().equals(personnelIDToCheck)) return true;
        return false;
    }

    public static Doctor findDoctorWithPersonnelID(String personnelIDToCheck) throws DoctorPersonnelIDNotExistsException {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getPersonnelID().equals(personnelIDToCheck)) return doctor;

        throw new DoctorPersonnelIDNotExistsException("No doctor was found with this personnelID");
    }

    public static boolean usernameExists(String usernameToCheck) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getUsername().equals(usernameToCheck)) return true;

        return false;
    }
}


class EmployeeController {
    public static long getMonthSalary(String personnelID) {
        ArrayList<Employee> allEmployee = Hospital.getInstance().getAllEmployees();

        for (Employee employee : allEmployee) {
            if (employee.getPersonnelID().equals(personnelID)) {
                return (long) employee.getHourlyWage() * (long) employee.getWorkHourPerMonth();
            }
        }

        return 0;
    }

    public static ArrayList<ArrivalRecord> getWorkedDays(String personnelID) {
        ArrayList<ArrivalRecord> workedDays = new ArrayList<>();

        for (ArrivalRecord arrivalRecord : Watchman.getArrivalRecords()) {
            if (arrivalRecord.getUserPersonnelID().equals(personnelID)) {
                workedDays.add(arrivalRecord);
            }
        }
        return workedDays;
    }
}


class HospitalController {
    public static boolean loginAdmin(String username, String password) {
        return Hospital.getInstance().getAdmin().getUsername().equals(username) && Hospital.getInstance().getAdmin().getPassword().equals(password);
    }

    public static boolean loginWatchman(String username, String password) {
        for (Watchman watchman : Hospital.getInstance().getWatchmen()) {
            if (watchman.getUsername().equals(username) && watchman.getPassword().equals(password)) {
                Hospital.getInstance().setLoggedInWatchman(watchman);
                WatchmanController.checkInWatchman(Hospital.getInstance().getHospitalTime().getTime());
                return true;
            }
        }
        return false;
    }

    public static boolean loginUser(String username, String password) {
        ArrayList<User> allUsers = Hospital.getInstance().getAllUsers();

        for (User user : allUsers) {
            if (user == null) continue;

            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                if (user instanceof Employee) {
                    if (((Employee) user).getWeeklyAbsenceCount() > 3) return false;
                }

                if (user instanceof Doctor) Hospital.getInstance().setLoggedInDoctor((Doctor) user);
                else if (user instanceof Patient) Hospital.getInstance().setLoggedInPatient((Patient) user);
                else if (user instanceof Janitor) Hospital.getInstance().setLoggedInJanitor((Janitor) user);
                else if (user instanceof Secretary) Hospital.getInstance().setLoggedInSecretary((Secretary) user);
                else return false;

                WatchmanController.checkInEmployee(Hospital.getInstance().getHospitalTime().getTime());
                return true;
            }
        }
        return false;
    }

    public static void logoutEmployee(String personnelID) {
        ArrivalRecord thisArrivalRecord = WatchmanController.recordEmployeeDeparture(personnelID, Hospital.getInstance().getHospitalTime().getTime());

        ArrayList<Employee> allEmployees = Hospital.getInstance().getAllEmployees();

        Employee wannaLogoutEmployee = null;

        for (Employee employee : allEmployees) {
            if (employee.getPersonnelID().equals(personnelID)) {
                wannaLogoutEmployee = employee;
                break;
            }
        }

        // Never will happen :)
        if (wannaLogoutEmployee == null) throw new RuntimeException("User not defined");

        int thisMonthWorkHourSum = 0;
        int workedWeekDays = 0;
        LocalDateTime hospitalTime = Hospital.getInstance().getHospitalTime().getTime();
        for (ArrivalRecord arrivalRecord : Watchman.getArrivalRecords()) {
            if (arrivalRecord.getUserPersonnelID().equals(wannaLogoutEmployee.getPersonnelID())) {
                if (arrivalRecord.getArrivalTime().getMonthValue() == hospitalTime.getMonthValue()) {
                    thisMonthWorkHourSum += (int) thisArrivalRecord.getWorkingTime().getSeconds() / 3600;
                }

                if (arrivalRecord.getArrivalTime().get(WeekFields.SUNDAY_START.weekOfYear()) == hospitalTime.get(WeekFields.SUNDAY_START.weekOfYear()) && arrivalRecord.getArrivalTime().getYear() == hospitalTime.getYear() && (int) arrivalRecord.getWorkingTime().getSeconds() / 3600 <= wannaLogoutEmployee.getMandatoryWorkHour()) {
                    LocalDate today = arrivalRecord.getArrivalTime().toLocalDate();
                    if (!wannaLogoutEmployee.getWorkedDays().contains(today)) {
                        wannaLogoutEmployee.getWorkedDays().add(today);
                        workedWeekDays += 1;
                    }
                }
            }
        }
        wannaLogoutEmployee.setWorkHourPerMonth(thisMonthWorkHourSum);
        wannaLogoutEmployee.setWorkDayCountInWeek(workedWeekDays);
        wannaLogoutEmployee.setWeeklyAbsenceCount(hospitalTime.getDayOfWeek().getValue() + 1 - workedWeekDays);


        Hospital.getInstance().setLoggedInDoctor(null);
        Hospital.getInstance().setLoggedInSecretary(null);
        Hospital.getInstance().setLoggedInJanitor(null);

    }

    public static void logoutWatchman(String personnelID) {
        Hospital.getInstance().setLoggedInWatchman(null);

        WatchmanController.recordWatchmanDeparture(personnelID, Hospital.getInstance().getHospitalTime().getTime());
    }

    public static Medicine addNewMedicine(String name, double price, String productionDate, String expirationDate) {
        Random rand = new Random();
        String ID;
        while (true) {
            ID = String.valueOf(Math.abs(rand.nextInt()));
            if (medicineIDExists(ID)) continue;
            break;
        }

        Medicine newMedicine = new Medicine(name, ID, price, productionDate, expirationDate);
        Hospital.getInstance().getMedicines().add(newMedicine);

        return newMedicine;
    }

    public static boolean medicineIDExists(String IDToCheck) {
        for (Medicine medicine : Hospital.getInstance().getMedicines())
            if (medicine.getID().equals(IDToCheck)) return true;
        return false;
    }

    public static void changeDate(LocalDate dateToJumpInto) {
        Hospital.getInstance().getHospitalTime().setTimeOrigin(dateToJumpInto.atStartOfDay());
        Hospital.getInstance().getHospitalTime().setTimeOriginSetTime(LocalDateTime.now());
    }
}


class JanitorController {
    public static Janitor addJanitor(String fullName, String username, String password, String phoneNumber, String email, int mandatoryWorkHour, int hourlyWage) {
        Random rand = new Random();
        String personnelID = String.valueOf(Math.abs(rand.nextInt()));

        Janitor newJanitor = new Janitor(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage);
        Hospital.getInstance().getJanitors().add(newJanitor);

        return newJanitor;
    }

    public static boolean usernameExists(String username) {
        for (Janitor janitor : Hospital.getInstance().getJanitors()) {
            if (janitor.getUsername().equals(username)) return true;
        }
        return false;
    }
}


class PatientController {
    public static void editInfo(String fileNumber, String fullName, String password, String phoneNumber, String descriptionOfProblem) {
        Patient patient;
        try {
            patient = findPatientWithFileNumber(fileNumber);
        } catch (PatientFileNumberNotExistsException e) {
            return;
        }

        if (!fullName.equals("")) patient.setFullName(fullName);
        if (!password.equals("")) patient.setPassword(password);
        if (!phoneNumber.equals("")) patient.setPhoneNumber(phoneNumber);
        if (!descriptionOfProblem.equals("")) patient.setDescriptionOfProblem(descriptionOfProblem);
    }

    public static void addAppointment(String fileNumber, Appointment appointment) {
        Patient patient;
        try {
            patient = findPatientWithFileNumber(fileNumber);
        } catch (PatientFileNumberNotExistsException e) {
            return;
        }

        patient.getAppointments().add(appointment);
    }

    public static void addRx(String fileNumber, Rx newPrescription) {
        Patient patient;
        try {
            patient = findPatientWithFileNumber(fileNumber);
        } catch (PatientFileNumberNotExistsException e) {
            return;
        }

        patient.getPrescriptions().add(newPrescription);
    }

    public static Patient registerPatient(String fullName, String username, String email, String password, String phoneNumber, String descriptionOfProblem) {
        Random rand = new Random();
        String fileNumber;
        while (true) {
            fileNumber = String.valueOf(Math.abs(rand.nextInt()));
            if (patientFileNumberExists(fileNumber)) continue;
            break;
        }

        Patient newPatient = new Patient(fullName, username, password, phoneNumber, email, fileNumber, descriptionOfProblem);
        Hospital.getInstance().getPatients().add(newPatient);
        return newPatient;
    }

    public static boolean patientFileNumberExists(String fileNumberToCheck) {
        for (Patient patient : Hospital.getInstance().getPatients())
            if (patient.getFileNumber().equals(fileNumberToCheck)) return true;
        return false;
    }

    public static boolean usernameExists(String usernameToCheck) {
        for (Patient patient : Hospital.getInstance().getPatients())
            if (patient.getUsername().equals(usernameToCheck)) return true;

        return false;
    }

    public static Patient findPatientWithFileNumber(String fileNumber) throws PatientFileNumberNotExistsException {
        for (Patient patient : Hospital.getInstance().getPatients())
            if (patient.getFileNumber().equals(fileNumber)) return patient;

        throw new PatientFileNumberNotExistsException("No patient was found with this file number");
    }
}


class SecretaryController {
    public static boolean appointmentTimeIsFree(String doctorPersonnelID, String startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTimeDT = LocalDateTime.parse(startTime, formatter);
        LocalDateTime endTimeDT = startTimeDT.plusMinutes(15);
        LocalDateTime nowDT = Hospital.getInstance().getHospitalTime().getTime();

        if (nowDT.isAfter(startTimeDT)) return false;

        Doctor doctor;
        try {
            doctor = DoctorController.findDoctorWithPersonnelID(doctorPersonnelID);
        } catch (DoctorPersonnelIDNotExistsException e) {
            return false;
        }

        int count = 0;
        for (Appointment appointment : doctor.getSecretary().getGivenAppointments()) {
            if (appointment.getStartDateTime().get(WeekFields.SUNDAY_START.weekOfYear()) == startTimeDT.get(WeekFields.SUNDAY_START.weekOfYear()))
                count++;

            if (count >= 10) return false;
        }

        for (Appointment appointment : doctor.getSecretary().getGivenAppointments()) {
            if (startTimeDT.isAfter(appointment.getStartDateTime()) && startTimeDT.isBefore(appointment.getEndDateTime())) {
                return false;
            }

            if (endTimeDT.isAfter(appointment.getStartDateTime()) && endTimeDT.isBefore(appointment.getEndDateTime())) {
                return false;
            }

            if (startTimeDT.get(WeekFields.SUNDAY_START.weekOfYear()) != Hospital.getInstance().getHospitalTime().getTime().get(WeekFields.SUNDAY_START.weekOfYear()))
                return false;
        }

        return true;
    }

    public static Appointment fixAnAppointment(String patientFileNumber, LocalDateTime startTime, String doctorPersonnelID, boolean isEmergency) {
        Doctor doctor;
        try {
            doctor = DoctorController.findDoctorWithPersonnelID(doctorPersonnelID);
        } catch (DoctorPersonnelIDNotExistsException e) {
            return null;
        }

        int appointmentNumber = doctor.getSecretary().getGivenAppointments().size();

        LocalDateTime endTimeDT = startTime.plusMinutes(15);

        Appointment newAppointment = new Appointment(appointmentNumber, startTime, endTimeDT, patientFileNumber, doctorPersonnelID, isEmergency);
        doctor.getSecretary().getGivenAppointments().add(newAppointment);
        PatientController.addAppointment(patientFileNumber, newAppointment);

        return newAppointment;
    }

    public static void removeAnAppointment(String doctorPersonnelID, int appointmentNumber) {
        Doctor doctor;
        try {
            doctor = DoctorController.findDoctorWithPersonnelID(doctorPersonnelID);
        } catch (DoctorPersonnelIDNotExistsException e) {
            return;
        }

        for (int i = 0; i < doctor.getSecretary().getGivenAppointments().size(); i++)
            if (doctor.getSecretary().getGivenAppointments().get(i).getNumber() == appointmentNumber) {
                doctor.getSecretary().getGivenAppointments().remove(i);
                break;
            }
    }

    public static ArrayList<Appointment> getAppointmentsByDay(String secretaryPersonnelID, LocalDate date) {
        ArrayList<Appointment> selectedAppointments = new ArrayList<>();

        for (Doctor doctor : Hospital.getInstance().getDoctors()) {
            Secretary secretary = doctor.getSecretary();

            if (secretary.getPersonnelID().equals(secretaryPersonnelID)) {
                for (Appointment appointment : secretary.getGivenAppointments()) {
                    LocalDateTime appointmentStartsOn = appointment.getStartDateTime();

                    if (appointmentStartsOn.getDayOfYear() == date.getDayOfYear() && appointmentStartsOn.getYear() == date.getYear())
                        selectedAppointments.add(appointment);
                }
                break;
            }
        }

        return selectedAppointments;
    }

    public static ArrayList<Appointment> getAppointmentsByWeek(String secretaryPersonnelID, LocalDate date) {
        ArrayList<Appointment> selectedAppointments = new ArrayList<>();

        for (Doctor doctor : Hospital.getInstance().getDoctors()) {
            Secretary secretary = doctor.getSecretary();

            if (secretary.getPersonnelID().equals(secretaryPersonnelID)) {
                for (Appointment appointment : secretary.getGivenAppointments()) {
                    LocalDateTime appointmentStartsOn = appointment.getStartDateTime();

                    if (appointmentStartsOn.get(WeekFields.SUNDAY_START.weekOfYear()) == date.get(WeekFields.SUNDAY_START.weekOfYear()) && appointmentStartsOn.getDayOfYear() >= date.getDayOfYear() && appointmentStartsOn.getYear() == date.getYear())
                        selectedAppointments.add(appointment);
                }
                break;
            }
        }

        return selectedAppointments;
    }

    public static ArrayList<Appointment> getSortedAppointments(String secretaryPersonnelID) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getSecretary().getPersonnelID().equals(secretaryPersonnelID)) {
                ArrayList<Appointment> sortedAppointments = new ArrayList<>(doctor.getSecretary().getGivenAppointments());
                sortedAppointments.sort(Collections.reverseOrder());
                return sortedAppointments;
            }

        throw new RuntimeException("secretaryPersonnelID (" + secretaryPersonnelID + ") does not exist");
    }

    public static boolean secretaryPersonnelIDExists(String personnelIDToCheck) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getSecretary() != null && doctor.getSecretary().getPersonnelID().equals(personnelIDToCheck))
                return true;
        return false;
    }

    public static boolean usernameExists(String usernameToCheck) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getSecretary() != null && doctor.getSecretary().getUsername().equals(usernameToCheck))
                return true;

        return false;
    }
}


class WatchmanController {
    public static void checkInEmployee(LocalDateTime checkInTime) {
        Hospital.getInstance().getLoggedInWatchman().setTempEmployeeCheckInTime(checkInTime);
    }

    public static ArrivalRecord recordEmployeeDeparture(String personnelID, LocalDateTime departureTime) {
        LocalDateTime userArrivalTime = Hospital.getInstance().getLoggedInWatchman().getTempEmployeeCheckInTime();
        ArrivalRecord arrivalRecord = new ArrivalRecord(personnelID, userArrivalTime, departureTime);
        Hospital.getInstance().getLoggedInWatchman().getArrivalRecords().add(arrivalRecord);
        return arrivalRecord;
    }

    public static void checkInWatchman(LocalDateTime checkInTime) {
        Hospital.getInstance().getLoggedInWatchman().setTempWatchmanCheckInTime(checkInTime);
    }

    public static ArrivalRecord recordWatchmanDeparture(String personnelID, LocalDateTime departureTime) {
        LocalDateTime userArrivalTime = Hospital.getInstance().getLoggedInWatchman().getTempWatchmanCheckInTime();
        ArrivalRecord arrivalRecord = new ArrivalRecord(personnelID, userArrivalTime, departureTime);
        Hospital.getInstance().getLoggedInWatchman().getArrivalRecords().add(arrivalRecord);
        return arrivalRecord;
    }

    public static ArrayList<ArrivalRecord> getArrivalRecordsByDay(LocalDate date) {
        ArrayList<ArrivalRecord> selectedRecords = new ArrayList<>();

        for (ArrivalRecord arrivalRecord : Hospital.getInstance().getLoggedInWatchman().getArrivalRecords()) {
            LocalDateTime arrivalDate = arrivalRecord.getArrivalTime();
            if (arrivalDate.getDayOfYear() == date.getDayOfYear() && arrivalDate.getYear() == date.getYear())
                selectedRecords.add(arrivalRecord);

        }

        return selectedRecords;
    }

    public static ArrayList<ArrivalRecord> getArrivalRecordsByWeek(LocalDate date) {
        ArrayList<ArrivalRecord> selectedRecords = new ArrayList<>();

        for (ArrivalRecord arrivalRecord : Hospital.getInstance().getLoggedInWatchman().getArrivalRecords()) {
            LocalDateTime arrivalDate = arrivalRecord.getArrivalTime();
            if (arrivalDate.get(WeekFields.SUNDAY_START.weekOfYear()) == date.get(WeekFields.SUNDAY_START.weekOfYear()) && arrivalDate.getDayOfYear() >= date.getDayOfYear() && arrivalDate.getYear() == date.getYear())
                selectedRecords.add(arrivalRecord);

        }

        return selectedRecords;
    }

    public static Watchman addWatchman(String fullName, String username, String password, String phoneNumber, String email, int mandatoryWorkHour, int hourlyWage) {
        Random rand = new Random();
        String personnelID = 'W' + String.valueOf(Math.abs(rand.nextInt()));

        Watchman newWatchman = new Watchman(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage);
        Hospital.getInstance().getWatchmen().add(newWatchman);

        return newWatchman;
    }

    public static boolean usernameExists(String username) {
        for (Watchman watchman : Hospital.getInstance().getWatchmen()) {
            if (watchman.getUsername().equals(username)) return true;
        }
        return false;
    }
}


class DoctorPersonnelIDNotExistsException extends Exception {
    public DoctorPersonnelIDNotExistsException(String message) {
        super(message);
    }
}


class PatientFileNumberNotExistsException extends Exception {
    public PatientFileNumberNotExistsException(String message) {
        super(message);
    }
}


/*
 * Admin is a singleton class
 * */
class Admin {
    private static Admin instance = null;

    private final String username;
    private final String password;
    private final String personnelID;

    private Admin(String username, String password, String personnelID) {
        this.username = username;
        this.password = password;
        this.personnelID = personnelID;
    }

    public static Admin getInstance(String username, String password, String personnelID) {
        if (instance == null) return new Admin(username, password, personnelID);
        return instance;
    }

    public static Admin getInstance() {
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPersonnelID() {
        return personnelID;
    }
}


class Appointment implements Comparable<Appointment> {
    private final int number;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final String patientFileNumber;
    private final String doctorPersonnelID;
    private final boolean isEmergency;

    public Appointment(int number, LocalDateTime startDateTime, LocalDateTime endDateTime, String patientFileNumber, String doctorPersonnelID, boolean isEmergency) {
        this.number = number;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.patientFileNumber = patientFileNumber;
        this.doctorPersonnelID = doctorPersonnelID;
        this.isEmergency = isEmergency;
    }

    public int getNumber() {
        return number;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getPatientFileNumber() {
        return patientFileNumber;
    }

    public String getDoctorPersonnelID() {
        return doctorPersonnelID;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    @Override
    public String toString() {
        return "number=" + number + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + ", patientFileNumber='" + patientFileNumber + '\'' + ", doctorPersonnelID='" + doctorPersonnelID + '\'' + ", isEmergency=" + isEmergency;
    }

    @Override
    public int compareTo(Appointment appointment) {
        if (this.isEmergency() && !appointment.isEmergency()) return 1;
        else if (!this.isEmergency() && appointment.isEmergency()) return -1;
        else return this.getNumber() - appointment.getNumber();

    }
}


class ArrivalRecord {
    private final String userPersonnelID;
    private final LocalDateTime arrivalTime;
    private final LocalDateTime exitTime;

    public ArrivalRecord(String userPersonnelID, LocalDateTime arrivalTime, LocalDateTime exitTime) {
        this.arrivalTime = arrivalTime;
        this.exitTime = exitTime;
        this.userPersonnelID = userPersonnelID;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public String getUserPersonnelID() {
        return userPersonnelID;
    }

    public Duration getWorkingTime() {
        return Duration.between(arrivalTime, exitTime);
    }

    @Override
    public String toString() {
        return "PersonnelID: " + this.getUserPersonnelID() + '\n' + "Arrived at:  " + this.getArrivalTime() + '\n' + "Exited at:   " + this.getExitTime();
    }
}


class Breakdown {
    private final String title;
    private boolean isAccepted;

    public Breakdown(String title, boolean isAccepted) {
        this.title = title;
        this.isAccepted = isAccepted;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}


/**
 * Hospital class is singleton
 */
class Hospital {
    private static Hospital instance = null;

    private final String name;

    // Users
    private final ArrayList<Doctor> doctors;
    private final ArrayList<Patient> patients;
    private final ArrayList<Watchman> watchmen;
    private final ArrayList<Janitor> janitors;

    private final ArrayList<Medicine> medicines;

    private final HospitalTime hospitalTime;

    private Patient loggedInPatient;
    private Doctor loggedInDoctor;
    private Secretary loggedInSecretary;
    private Watchman loggedInWatchman;
    private Janitor loggedInJanitor;
    private Admin admin;

    final private HospitalConsole console;

    private Hospital(String name, HospitalConsole hospitalConsole, Admin admin) {
        this.name = name;

        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        watchmen = new ArrayList<>();
        janitors = new ArrayList<>();

        medicines = new ArrayList<>();

        this.console = hospitalConsole;
        this.admin = admin;

        hospitalTime = new HospitalTime();
    }

    public static Hospital getInstance(String name, HospitalConsole hospitalConsole, Admin admin) {
        if (instance == null) instance = new Hospital(name, hospitalConsole, admin);

        return instance;
    }

    public static Hospital getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    public HospitalTime getHospitalTime() {
        return hospitalTime;
    }

    public Doctor getLoggedInDoctor() {
        return loggedInDoctor;
    }

    public void setLoggedInDoctor(Doctor loggedInDoctor) {
        this.loggedInDoctor = loggedInDoctor;
    }

    public Patient getLoggedInPatient() {
        return loggedInPatient;
    }

    public void setLoggedInPatient(Patient loggedInPatient) {
        this.loggedInPatient = loggedInPatient;
    }

    public Watchman getLoggedInWatchman() {
        return loggedInWatchman;
    }

    public void setLoggedInWatchman(Watchman loggedInWatchman) {
        this.loggedInWatchman = loggedInWatchman;
    }

    public Janitor getLoggedInJanitor() {
        return loggedInJanitor;
    }

    public void setLoggedInJanitor(Janitor loggedInJanitor) {
        this.loggedInJanitor = loggedInJanitor;
    }

    public Secretary getLoggedInSecretary() {
        return loggedInSecretary;
    }

    public void setLoggedInSecretary(Secretary loggedInSecretary) {
        this.loggedInSecretary = loggedInSecretary;
    }

    public HospitalConsole getConsole() {
        return console;
    }

    public ArrayList<Watchman> getWatchmen() {
        return watchmen;
    }

    public ArrayList<Janitor> getJanitors() {
        return janitors;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> allEmployees = new ArrayList<>();

        allEmployees.addAll(Hospital.getInstance().getDoctors());
        allEmployees.addAll(Hospital.getInstance().getWatchmen());
        allEmployees.addAll(Hospital.getInstance().getJanitors());
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            allEmployees.add(doctor.getSecretary());

        return allEmployees;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();

        allUsers.addAll(Hospital.getInstance().getDoctors());
        allUsers.addAll(Hospital.getInstance().getPatients());
        allUsers.addAll(Hospital.getInstance().getWatchmen());
        allUsers.addAll(Hospital.getInstance().getJanitors());
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            allUsers.add(doctor.getSecretary());

        return allUsers;
    }
}


class HospitalTime {
    private LocalDateTime timeOrigin;
    private LocalDateTime timeOriginSetTime;

    public HospitalTime() {
        LocalDateTime now = LocalDateTime.now();
        timeOrigin = now;
        timeOriginSetTime = now;
    }

    public HospitalTime(LocalDateTime timeOrigin) {
        this.timeOrigin = timeOrigin;
        timeOriginSetTime = LocalDateTime.now();
    }

    public void setTimeOrigin(LocalDateTime timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public void setTimeOriginSetTime(LocalDateTime timeOriginSetTime) {
        this.timeOriginSetTime = timeOriginSetTime;
    }

    private Duration getForwardedDuration() {
        return Duration.between(timeOriginSetTime, timeOrigin);
    }

    public LocalDateTime getTime() {
        LocalDateTime nowNow = LocalDateTime.now();

        if (timeOrigin.isAfter(timeOriginSetTime)) return nowNow.plus(getForwardedDuration());
        else return nowNow.minus(getForwardedDuration());
    }
}

class Medicine {
    private final String name;
    private final String ID;
    private final double price;
    private final LocalDateTime productionDate;
    private final LocalDateTime expirationDate;

    public Medicine(String name, String ID, double price, String productionDate, String expirationDate) {
        this.name = name;
        this.ID = ID;
        this.price = price;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.productionDate = LocalDateTime.parse(productionDate, formatter);
        this.expirationDate = LocalDateTime.parse(expirationDate, formatter);
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getProductionDate() {
        return productionDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return "name='" + getName() + '\'' + ", price=" + getPrice() + ", productionDate=" + getProductionDate() + ", expirationDate=" + getExpirationDate() + ", ID='" + getID() + '\'';
    }
}


class Patient extends User {
    private final String fileNumber;
    private final ArrayList<Rx> prescriptions;
    private final ArrayList<Appointment> appointments;
    private String descriptionOfProblem;

    public Patient(String fullName, String username, String password, String phoneNumber, String email, String fileNumber, String descriptionOfProblem) {
        super(fullName, username, password, phoneNumber, email);
        this.fileNumber = fileNumber;
        this.prescriptions = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.descriptionOfProblem = descriptionOfProblem;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public ArrayList<Rx> getPrescriptions() {
        return prescriptions;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public String getDescriptionOfProblem() {
        return descriptionOfProblem;
    }

    public void setDescriptionOfProblem(String descriptionOfProblem) {
        this.descriptionOfProblem = descriptionOfProblem;
    }

    @Override
    public String toString() {
        return super.toString() + "fileNumber='" + fileNumber + '\'' + ", prescriptions=" + prescriptions + ", appointments=" + appointments + ", descriptionOfProblem='" + descriptionOfProblem + '\'';
    }
}


class Rx {
    private final String rxID;
    private final LocalDateTime writtenOn;
    private final ArrayList<Medicine> medicines;
    private final String doctorPersonnelID;
    private final String patientFileNumber;

    public Rx(String rxID, LocalDateTime writtenOn, ArrayList<Medicine> medicines, String doctorPersonnelID, String patientFileNumber) {
        this.rxID = rxID;
        this.writtenOn = writtenOn;
        this.medicines = medicines;
        this.doctorPersonnelID = doctorPersonnelID;
        this.patientFileNumber = patientFileNumber;
    }

    public String getRxID() {
        return rxID;
    }

    public LocalDateTime getWrittenOn() {
        return writtenOn;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    public String getDoctorPersonnelID() {
        return doctorPersonnelID;
    }

    public String getPatientFileNumber() {
        return patientFileNumber;
    }

    @Override
    public String toString() {
        return "rxID='" + getRxID() + '\'' + ", writtenOn=" + getWrittenOn() + ", medicines=" + getMedicines() + ", doctorPersonnelID='" + getDoctorPersonnelID() + '\'' + ", patientFileNumber='" + getPatientFileNumber() + '\'';
    }
}


class Doctor extends Employee {
    private final String major;
    private String biography;
    private Secretary secretary;

    public Doctor(String fullName, String username, String password, String phoneNumber, String email, String personnelID, int mandatoryWorkHour, int hourlyWage, String major, String biography) {
        super(fullName, username, password, phoneNumber, email, 'D' + personnelID, mandatoryWorkHour, hourlyWage);
        this.major = major;
        this.biography = biography;
        this.secretary = null;
    }

    public String getMajor() {
        return major;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Secretary getSecretary() {
        return secretary;
    }

    public void setSecretary(Secretary secretary) {
        this.secretary = secretary;
    }

    @Override
    public String toString() {
        return "fullName='" + getFullName() + '\'' + ", personnelID='" + getPersonnelID() + '\'' + ", major='" + getMajor() + '\'';
    }
}


class Employee extends User {
    private String personnelID;
    private int workHourPerMonth;
    private int workDayCountInWeek;
    private int mandatoryWorkHour;
    private int hourlyWage;
    private int weeklyAbsenceCount;
    private final ArrayList<LocalDate> workedDays;

    public Employee(String fullName, String username, String password, String phoneNumber, String email, String personnelID, int mandatoryWorkHour, int hourlyWage) {
        super(fullName, username, password, phoneNumber, email);
        this.personnelID = personnelID;
        this.mandatoryWorkHour = mandatoryWorkHour;
        this.hourlyWage = hourlyWage;

        workedDays = new ArrayList<>();
    }

    public String getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(String personnelID) {
        this.personnelID = personnelID;
    }

    public int getWorkHourPerMonth() {
        return workHourPerMonth;
    }

    public void setWorkHourPerMonth(int workHourPerMonth) {
        this.workHourPerMonth = workHourPerMonth;
    }

    public int getWorkDayCountInWeek() {
        return workDayCountInWeek;
    }

    public void setWorkDayCountInWeek(int workDayCountInWeek) {
        this.workDayCountInWeek = workDayCountInWeek;
    }

    public int getMandatoryWorkHour() {
        return mandatoryWorkHour;
    }

    public void setMandatoryWorkHour(int mandatoryWorkHour) {
        this.mandatoryWorkHour = mandatoryWorkHour;
    }

    public int getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(int hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public int getWeeklyAbsenceCount() {
        return weeklyAbsenceCount;
    }

    public void setWeeklyAbsenceCount(int weeklyAbsenceCount) {
        this.weeklyAbsenceCount = weeklyAbsenceCount;
    }

    public ArrayList<LocalDate> getWorkedDays() {
        return workedDays;
    }
}


class Janitor extends Employee {
    private final static ArrayList<Breakdown> hospitalBreakdowns = new ArrayList<>();

    public Janitor(String fullName, String username, String password, String phoneNumber, String email, String personnelID, int mandatoryWorkHour, int hourlyWage) {
        super(fullName, username, password, phoneNumber, email, 'J' + personnelID, mandatoryWorkHour, hourlyWage);
    }

    public static ArrayList<Breakdown> getHospitalBreakdowns() {
        return hospitalBreakdowns;
    }
}


class Secretary extends Employee {
    private final String doctorPersonnelID;
    private final ArrayList<Appointment> givenAppointments;

    public Secretary(String fullName, String username, String password, String phoneNumber, String email, String personnelID, int mandatoryWorkHour, int hourlyWage, String doctorPersonnelID) {
        super(fullName, username, password, phoneNumber, email, 'S' + personnelID, mandatoryWorkHour, hourlyWage);
        this.doctorPersonnelID = doctorPersonnelID;

        givenAppointments = new ArrayList<>();
    }

    public String getDoctorPersonnelID() {
        return doctorPersonnelID;
    }

    public ArrayList<Appointment> getGivenAppointments() {
        return givenAppointments;
    }
}

abstract class User {
    private String fullName;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;

    public User(String fullName, String username, String password, String phoneNumber, String email) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "fullName='" + fullName + '\'' + ", username='" + username + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", email='" + email + '\'';
    }
}


class Watchman extends Employee {
    private static final ArrayList<ArrivalRecord> arrivalRecords = new ArrayList<>();
    private static LocalDateTime tempEmployeeCheckInTime;
    private static LocalDateTime tempWatchmanCheckInTime; // Because a watchman can simultaneously log in with another employee

    public Watchman(String fullName, String username, String password, String phoneNumber, String email, String personnelID, int mandatoryWorkHour, int hourlyWage) {
        super(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage);
    }

    public static ArrayList<ArrivalRecord> getArrivalRecords() {
        return arrivalRecords;
    }

    public LocalDateTime getTempEmployeeCheckInTime() {
        return tempEmployeeCheckInTime;
    }

    public void setTempEmployeeCheckInTime(LocalDateTime tempEmployeeCheckInTime) {
        this.tempEmployeeCheckInTime = tempEmployeeCheckInTime;
    }

    public LocalDateTime getTempWatchmanCheckInTime() {
        return tempWatchmanCheckInTime;
    }

    public void setTempWatchmanCheckInTime(LocalDateTime tempWatchmanCheckInTime) {
        this.tempWatchmanCheckInTime = tempWatchmanCheckInTime;
    }
}
