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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Scanner;

public non-sealed class PatientConsole extends HospitalConsole {
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
