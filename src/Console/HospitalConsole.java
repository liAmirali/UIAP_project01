package Console;

import java.util.Scanner;

import Main.Hospital;

sealed public class HospitalConsole permits AdminConsole, DoctorConsole, PatientConsole {
    boolean shouldKeepRendering;

    AdminConsole adminConsole;
    DoctorConsole doctorConsole;
    PatientConsole patientConsole;

    public HospitalConsole() {
    }

    public HospitalConsole(AdminConsole adminConsole, DoctorConsole doctorConsole, PatientConsole patientConsole) {
        this.adminConsole = adminConsole;
        this.doctorConsole = doctorConsole;
        this.patientConsole = patientConsole;
    }

    public static void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name");
            System.out.println("operatingSystem = " + operatingSystem);
            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void waitOnEnter() {
        System.out.println("\nPress enter to continue...");
        (new Scanner(System.in)).nextLine();
    }

    public void showLoginRegisterPage() {
        clearConsole();

        Scanner input = new Scanner(System.in);
        System.out.println("#### Welcome to " + Hospital.getInstance().getName() + " ####");
        System.out.println("(1) Login as the admin");
        System.out.println("(2) Login as a doctor");
        System.out.println("(3) Login as a patient");
        System.out.println("(4) Register as a patient");
        System.out.println("(5) Exit");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();
        if (menuCode.equals("1")) adminConsole.showAdminLoginPage();
        else if (menuCode.equals("2")) doctorConsole.showDoctorLoginPage();
        else if (menuCode.equals("3")) patientConsole.showPatientLoginPage();
        else if (menuCode.equals("4")) patientConsole.showPatientRegistrationPage();
        else if (menuCode.equals("5")) System.exit(0);
        else {
            System.out.println("**** Error: Invalid menu code");
            waitOnEnter();
        }
    }

}