package Console;

import Controllers.EmployeeController;
import Controllers.HospitalController;
import Main.ArrivalRecord;
import Main.Hospital;

import java.util.Scanner;

sealed public class HospitalConsole permits AdminConsole, DoctorConsole, WatchmanConsole, JanitorConsole, SecretaryConsole, PatientConsole {
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