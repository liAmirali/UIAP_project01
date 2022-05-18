package Console;

import Main.*;

import java.util.ArrayList;
import java.util.Scanner;

public non-sealed class PatientConsole extends HospitalConsole {
    public PatientConsole() {}

    //  Patient pages
    void showPatientRegistrationPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Registering as a Patient ####");

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Phone number: ");
        String phoneNumber = input.nextLine();

        System.out.println("How is everything about your health?");
        String descriptionOfProblem = input.nextLine();

        Patient newPatient = Hospital.getInstance().registerPatient(fullName, password, phoneNumber, descriptionOfProblem);
        System.out.println("Patient was registered successfully! File Number: " + newPatient.getFileNumber());

        waitOnEnter();
    }

    void showPatientLoginPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Logging in as a patient ####");

        System.out.print("\nFile number: ");
        String fileNumber = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        shouldKeepRendering = true;

        if (Hospital.getInstance().loginPatient(fileNumber, password)) {
            System.out.println("Logged in successfully!");
            waitOnEnter();
            while (shouldKeepRendering) showPatientPanel();
        } else {
            System.out.println("Username or password is incorrect!");
            waitOnEnter();
        }
    }

    void showPatientPanel() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Patient Panel ####");

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
        else if (menuCode.equals("3")) Hospital.getInstance().getConsole().doctorConsole.printAllDoctors();
        else if (menuCode.equals("4")) showFilterDoctorPage();
        else if (menuCode.equals("5")) printAllPatientAppointments(Hospital.getInstance().getLoggedInPatient());
        else if (menuCode.equals("6")) printAllPatientPrescriptions(Hospital.getInstance().getLoggedInPatient());
        else if (menuCode.equals("7")) shouldKeepRendering = false;
        else {
            System.out.println("*** Invalid menu code. ***");
            waitOnEnter();
        }
    }

    void showPatientProfileEdit() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("### Editing profile ###\n");

        System.out.println("Enter your new full name (leave empty to keep the old data): ");
        String newFullName = input.nextLine();

        System.out.println("Enter your new password (leave empty to keep the old data): ");
        String newPassword = input.nextLine();

        System.out.println("Enter your new phone number (leave empty to keep the old data): ");
        String newPhoneNumber = input.nextLine();

        System.out.println("Enter any update about your health condition (leave empty to keep the old data): ");
        String descriptionOfProblem = input.nextLine();

        Hospital.getInstance().getLoggedInPatient().editInfo(newFullName, newPassword, newPhoneNumber, descriptionOfProblem);

        System.out.println("Profile info was edited successfully");

        waitOnEnter();
    }

    void showFilterDoctorPage() {
        Scanner input = new Scanner(System.in);

        waitOnEnter();

        System.out.println("*** Enter the major to filter doctors:");
        String filteringMajor = input.nextLine();

        System.out.println("Result:");
        ArrayList<Doctor> filteredDoctors = Hospital.getInstance().filterDoctorsByMajor(filteringMajor);
        for (Doctor doctor : filteredDoctors) {
            System.out.println(doctor.toString());
        }

        waitOnEnter();
    }

    void showMakingAnAppointmentPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println(Hospital.getInstance().getName() + " :: Making a new appointment ####");

        String personnelID;
        Doctor selectedDoctor = null;
        boolean doctorFound = false;
        while (!doctorFound) {
            System.out.println("Enter the doctor personnel ID: ");
            personnelID = input.nextLine();
            for (Doctor doctor : Hospital.getInstance().getDoctors()) {
                if (doctor.getPersonnelID().equals(personnelID)) {
                    selectedDoctor = doctor;
                    doctorFound = true;
                    break;
                }
            }
            if (!doctorFound) {
                System.out.println("No doctor found with this personnel ID.");
                waitOnEnter();
            }
        }

        String visitTime;
        while (true) {
            System.out.println("Enter the time you want to be visited (yyyy-MM-dd HH:mm:ss): ");
            visitTime = input.nextLine();

            if (!selectedDoctor.getSecretary().appointmentTimeIsFree(visitTime)) {
                System.out.println("Sorry! This time is already taken or the time has passed.");
                waitOnEnter();
                continue;
            }
            break;
        }

        Appointment fixedAppointment = selectedDoctor.getSecretary().fixAnAppointment(Hospital.getInstance().getLoggedInPatient(), visitTime, selectedDoctor.getPersonnelID());
        System.out.println("Your appointment has been fixed! Number:" + fixedAppointment.getNumber());
        waitOnEnter();
    }

    void printAllPatientAppointments(Patient patient) {
        clearConsole();

        System.out.println("Here is all your appointments:");

        for (Appointment appointment : patient.getAppointments())
            System.out.println(appointment.toString());

        waitOnEnter();
    }

    void printAllPatientPrescriptions(Patient patient) {
        clearConsole();

        System.out.println("Here is all you prescriptions:");

        for (Rx rx : patient.getPrescriptions())
            System.out.println(rx.toString());

        waitOnEnter();
    }
}
