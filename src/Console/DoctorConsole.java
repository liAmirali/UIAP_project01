package Console;

import Main.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public non-sealed class DoctorConsole extends HospitalConsole {
    public DoctorConsole() {}

    void printAllMedicines() {
        clearConsole();

        ArrayList<Medicine> allMedicines = Hospital.getInstance().getMedicines();

        System.out.println("*** All medicines in " + Hospital.getInstance().getName() + ":");
        for (Medicine medicine : allMedicines)
            System.out.println(medicine.toString());

        waitOnEnter();
    }

    void printAllDoctors() {
        clearConsole();

        ArrayList<Doctor> allDoctors = Hospital.getInstance().getDoctors();
        System.out.println("*** All Doctors in " + Hospital.getInstance().getName() + ":");
        for (Doctor doctor : allDoctors)
            System.out.println(doctor.toString());

        waitOnEnter();
    }

    void showDoctorLoginPage() {
        Scanner input = new Scanner(System.in);
        shouldKeepRendering = true;

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Login as a Doctor ####");

        System.out.print("\nPersonnel ID: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        if (Hospital.getInstance().loginDoctor(username, password)) while (shouldKeepRendering) showDoctorPanel();
        else {
            System.out.println("Username or password is incorrect!");
            waitOnEnter();
        }
    }

    void showDoctorPanel() {
        Scanner input = new Scanner(System.in);

        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Doctor Panel ####");
        System.out.println("\n(1) Print all appointments");
        System.out.println("(2) Print all medicines");
        System.out.println("(3) Visit a patient");
        System.out.println("(4) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();
        if (menuCode.equals("1")) printAllDoctorAppointments(Hospital.getInstance().getLoggedInDoctor());
        else if (menuCode.equals("2")) printAllMedicines();
        else if (menuCode.equals("3")) visitAPatient();
        else if (menuCode.equals("4")) shouldKeepRendering = false;
        else {
            System.out.println("**** Error: Invalid menu code");
            waitOnEnter();
        }
    }

    void printAllDoctorAppointments(Doctor doctor) {
        clearConsole();

        System.out.println("Here is all the appointments:");

        for (Appointment appointment : doctor.getSecretary().getGivenAppointments())
            System.out.println(appointment.toString());

        clearConsole();
    }

    void visitAPatient() {
        LocalDateTime nowDT = LocalDateTime.now();
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
                selectedPatient.addRx(prescription);

                Hospital.getInstance().getLoggedInDoctor().getSecretary().removeAnAppointment(appointment.getNumber());

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
}
