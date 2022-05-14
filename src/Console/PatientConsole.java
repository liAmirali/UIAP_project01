package Console;

import Main.*;

import java.util.ArrayList;
import java.util.Scanner;

public non-sealed class PatientConsole extends HospitalConsole {
    private final Hospital hospital = Hospital.getInstance();

    public PatientConsole() {}

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

        System.out.println("How is everything about your health?");
        String descriptionOfProblem = input.nextLine();

        Patient newPatient = hospital.registerPatient(fullName, password, phoneNumber, descriptionOfProblem);
        System.out.println("Patient was registered successfully! File Number: " + newPatient.getFileNumber());
    }

    void showPatientLoginPage() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Logging in as a patient ####");

        System.out.print("\nFile number: ");
        String fileNumber = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        shouldKeepRendering = true;

        if (hospital.loginPatient(fileNumber, password)) {
            System.out.println("Logged in successfully!");
            while (shouldKeepRendering) showPatientPanel();
        } else System.out.println("Username or password is incorrect!");
    }

    void showPatientPanel() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Patient Panel ####");

        System.out.println("(1) Make an appointment");
        System.out.println("(2) Edit info");
        System.out.println("(3) Print all doctors");
        System.out.println("(4) Filter doctors by major");
        System.out.println("(5) Print all appointments");
        System.out.println("(6) Print all prescriptions");
        System.out.println("(7) Logout");

        System.out.println("\nEnter menu code: ");
        String menuCode = input.nextLine();

        if (menuCode.equals("1")) showMakingAnAppointmentPage();
        else if (menuCode.equals("2")) showPatientProfileEdit();
        else if (menuCode.equals("3")) hospital.getConsole().doctorConsole.printAllDoctors();
        else if (menuCode.equals("4")) showFilterDoctorPage();
        else if (menuCode.equals("5")) printAllPatientAppointments(hospital.getLoggedInPatient());
        else if (menuCode.equals("6")) printAllPatientPrescriptions(hospital.getLoggedInPatient());
        else if (menuCode.equals("7")) shouldKeepRendering = false;
        else System.out.println("*** Invalid menu code. ***");
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

        System.out.println("Enter any update about your health condition (leave empty to keep the old data): ");
        String descriptionOfProblem = input.nextLine();

        hospital.getLoggedInPatient().editInfo(newFullName, newPassword, newPhoneNumber, descriptionOfProblem);

        System.out.println("Profile info was edited successfully");
    }

    void showFilterDoctorPage() {
        Scanner input = new Scanner(System.in);

        System.out.println("*** Enter the major to filter doctors:");
        String filteringMajor = input.nextLine();

        System.out.println("Result:");
        ArrayList<Doctor> filteredDoctors = hospital.filterDoctorsByMajor(filteringMajor);
        for (Doctor doctor : filteredDoctors) {
            System.out.println(doctor.toString());
        }
    }

    void showMakingAnAppointmentPage() {
        Scanner input = new Scanner(System.in);
        System.out.println(hospital.getName() + " :: Making a new appointment ####");

        String personnelID;
        Doctor selectedDoctor = null;
        boolean doctorFound = false;
        while (!doctorFound) {
            System.out.println("Enter the doctor personnel ID: ");
            personnelID = input.nextLine();
            for (Doctor doctor : hospital.getDoctors()) {
                if (doctor.getPersonnelID().equals(personnelID)) {
                    selectedDoctor = doctor;
                    doctorFound = true;
                    break;
                }
            }
            if (!doctorFound) System.out.println("No doctor found with this personnel ID.");
        }

        String visitTime;
        while (true) {
            System.out.println("Enter the time you want to be visited (yyyy-MM-dd HH:mm:ss): ");
            visitTime = input.nextLine();

            if (!selectedDoctor.getSecretary().appointmentTimeIsFree(visitTime)) {
                System.out.println("Sorry! This time is already taken or the time has passed.");
                continue;
            }
            break;
        }

        Appointment fixedAppointment = selectedDoctor.getSecretary().fixAnAppointment(hospital.getLoggedInPatient(), visitTime, selectedDoctor.getPersonnelID());
        System.out.println("Your appointment has been fixed! Number:" + fixedAppointment.getNumber());
    }

    void printAllPatientAppointments(Patient patient) {
        System.out.println("Here is all your appointments:");

        for (Appointment appointment : patient.getAppointments())
            System.out.println(appointment.toString());
    }

    void printAllPatientPrescriptions(Patient patient) {
        System.out.println("Here is all you prescriptions:");

        for (Rx rx : patient.getPrescriptions())
            System.out.println(rx.toString());
    }
}
