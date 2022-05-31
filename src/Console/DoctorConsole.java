package Console;

import Controllers.*;
import Main.*;
import User.Doctor;
import User.Secretary;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public non-sealed class DoctorConsole extends HospitalConsole {
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
                shouldKeepRendering = false;
                HospitalController.logoutEmployee(Hospital.getInstance().getLoggedInDoctor().getPersonnelID());
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
