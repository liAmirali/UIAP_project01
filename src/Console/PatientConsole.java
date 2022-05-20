package Console;

import Controllers.DoctorController;
import Controllers.HospitalController;
import Controllers.PatientController;
import Controllers.SecretaryController;
import Main.Appointment;
import Main.Hospital;
import Main.Patient;
import Main.Rx;
import User.Doctor;

import java.util.ArrayList;
import java.util.Scanner;

public non-sealed class PatientConsole extends HospitalConsole {
    PatientController controller;
    HospitalController hospitalController;
    DoctorController doctorController;
    SecretaryController secretaryController;

    public PatientConsole() {
        controller = new PatientController();
        hospitalController = new HospitalController();
        doctorController = new DoctorController();
        secretaryController = new SecretaryController();
    }

    void showPatientRegistrationPage() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Registering as a Patient ####");

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        String username;
        while (true) {
            System.out.println("\nUsername: P");
            username = 'P' + input.nextLine();

            if (controller.usernameExist(username)) {
                System.out.println("This username already exists. Try another one.");
                continue;
            }
            break;
        }

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Phone number: ");
        String phoneNumber = input.nextLine();

        System.out.println("How is everything about your health?");
        String descriptionOfProblem = input.nextLine();

        Patient newPatient = controller.registerPatient(fullName, username, email, password, phoneNumber, descriptionOfProblem);
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

        if (hospitalController.loginPatient(fileNumber, password)) {
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

        controller.editInfo(Hospital.getInstance().getLoggedInPatient().getFileNumber(), newFullName, newPassword, newPhoneNumber, descriptionOfProblem);

        System.out.println("Profile info was edited successfully");

        waitOnEnter();
    }

    void showFilterDoctorPage() {
        Scanner input = new Scanner(System.in);

        waitOnEnter();

        System.out.println("*** Enter the major to filter doctors:");
        String filteringMajor = input.nextLine();

        System.out.println("Result:");
        ArrayList<Doctor> filteredDoctors = doctorController.filterDoctorsByMajor(filteringMajor);
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

            if (!secretaryController.appointmentTimeIsFree(selectedDoctor.getPersonnelID(), visitTime)) {
                System.out.println("Sorry! This time is already taken or the time has passed.");
                waitOnEnter();
                continue;
            }
            break;
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

        Appointment fixedAppointment = secretaryController.fixAnAppointment(Hospital.getInstance().getLoggedInPatient().getFileNumber(), visitTime, selectedDoctor.getPersonnelID(), isEmergency);
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
