package Console;

import Main.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public non-sealed class DoctorConsole extends HospitalConsole {
    private final Hospital hospital = Hospital.getInstance();

    public DoctorConsole() {}

    void printAllMedicines() {
        ArrayList<Medicine> allMedicines = hospital.getMedicines();
        System.out.println("*** All medicines in " + hospital.getName() + ":");
        for (Medicine medicine : allMedicines) {
            System.out.println(medicine.toString());
        }
    }

    void printAllDoctors() {
        ArrayList<Doctor> allDoctors = hospital.getDoctors();
        System.out.println("*** All Doctors in " + hospital.getName() + ":");
        for (Doctor doctor : allDoctors) {
            System.out.println(doctor.toString());
        }
    }

    void showDoctorLoginPage() {
        Scanner input = new Scanner(System.in);
        shouldKeepRendering = true;

        System.out.println("#### " + hospital.getName() + " :: Login as a Doctor ####");

        System.out.print("\nPersonnel ID: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        if (hospital.loginDoctor(username, password)) while (shouldKeepRendering) showDoctorPanel();
        else System.out.println("Username or password is incorrect!");
    }

    void showDoctorPanel() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Doctor Panel ####");
        System.out.println("\n(1) Print all appointments");
        System.out.println("(2) Print all medicines");
        System.out.println("(3) Visit a patient");
        System.out.println("(4) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();
        if (menuCode.equals("1")) printAllDoctorAppointments(hospital.getLoggedInDoctor());
        else if (menuCode.equals("2")) printAllMedicines();
        else if (menuCode.equals("3")) visitAPatient();
        else if (menuCode.equals("4")) shouldKeepRendering = false;
        else System.out.println("**** Error: Invalid menu code");
    }

    void printAllDoctorAppointments(Doctor doctor) {
        System.out.println("Here is all the appointments:");

        for (Appointment appointment : doctor.getSecretary().getGivenAppointments()) {
            System.out.println(appointment.toString());
        }
    }

    void visitAPatient() {
        LocalDateTime nowDT = LocalDateTime.now();
        boolean examined = false;
        // Finding an appointment to visit right now
        for (Appointment appointment : hospital.getLoggedInDoctor().getSecretary().getGivenAppointments()) {
            if (appointment.getStartDateTime().isBefore(nowDT) && appointment.getEndDateTime().isAfter(nowDT)) {
                System.out.println("## You a patient to visit!");
                String patientFileNumber = appointment.getPatientFileNumber();

                // Finding a patient with the same file number as the one in the appointment.
                Patient selectedPatient = null;
                for (Patient patient : hospital.getPatients())
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

                    for (Medicine medicine : hospital.getMedicines())
                        if (medicine.getID().equals(enteredID)) {
                            medicineIDFound = true;

                            if (medicine.getProductionDate().isAfter(nowDT)) {
                                System.out.println("This medicine has not been produced yet.");
                                break;
                            }

                            if (medicine.getExpirationDate().isBefore(nowDT)) {
                                System.out.println("This medicine has been expired.");
                                break;
                            }

                            selectedMedicines.add(medicine);
                            break;
                        }

                    if (!medicineIDFound) System.out.println("No medicine found with this ID");
                }

                Random rand = new Random();
                String rxId = String.valueOf(Math.abs(rand.nextInt()));
                Rx prescription = new Rx(rxId, nowDT, selectedMedicines, appointment.getDoctorPersonnelID(), selectedPatient.getFileNumber());
                selectedPatient.addRx(prescription);

                hospital.getLoggedInDoctor().getSecretary().removeAnAppointment(appointment.getNumber());

                System.out.println("You examined the patient successfully!");
                examined = true;
            }
            if (examined) break;
        }
        if (!examined) System.out.println("You don't have any patient to visit right now!");
    }
}
