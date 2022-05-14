package Console;

import java.util.Scanner;

import Main.Hospital;

sealed public class HospitalConsole permits AdminConsole, DoctorConsole, PatientConsole {
    private final Hospital hospital = Hospital.getInstance();
    boolean shouldKeepRendering;
    public HospitalConsole() {
    }

    static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public void showLoginRegisterPage() {
        Scanner input = new Scanner(System.in);
        System.out.println("#### Welcome to " + hospital.getName() + " ####");
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
        else System.out.println("**** Error: Invalid menu code");
    }

}