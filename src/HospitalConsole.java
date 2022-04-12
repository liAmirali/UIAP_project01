import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class HospitalConsole {
    Hospital hospital;
    boolean shouldKeepRendering;

    public HospitalConsole(Hospital hospital) {
        this.hospital = hospital;
    }

    static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    void showLoginRegisterPage() {
        Scanner input = new Scanner(System.in);
        System.out.println("#### Welcome to " + hospital.getName() + " ####");
        System.out.println("(1) Login as the admin");
        System.out.println("(2) Login as a doctor");
        System.out.println("(3) Login as a patient");
        System.out.println("(4) Register as a patient");
        System.out.println("(5) Exit");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();
        if (menuCode.equals("1")) showAdminLoginPage();
        else if (menuCode.equals("2")) showDoctorLoginPage();
        else if (menuCode.equals("3")) showPatientLoginPage();
        else if (menuCode.equals("4")) showPatientRegistrationPage();
        else if (menuCode.equals("5")) System.exit(0);
        else System.out.println("**** Error: Invalid menu code");
    }

    //  Admin pages
    void showAdminLoginPage() {
        Scanner input = new Scanner(System.in);

        shouldKeepRendering = true;

        System.out.println("#### " + hospital.getName() + " :: Login as Admin ####");
        System.out.print("\nEnter your username: ");
        String username = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        if (hospital.loginAdmin(username, password)) {
            System.out.println("Logged in successfully.");
            while (shouldKeepRendering) showAdminPanel();
        } else System.out.println("Username or password is incorrect.");
    }

    void showAdminPanel() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Admin Panel ####");
        System.out.println("\n(1) Add a doctor");
        System.out.println("(2) Add medicine");
        System.out.println("(3) Print all doctors");
        System.out.println("(4) Print all medicines");
        System.out.println("(5) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();
        if (menuCode.equals("1")) showDoctorRegistrationPage();
        else if (menuCode.equals("2")) showAddMedicinePage();
        else if (menuCode.equals("3")) printAllDoctors();
        else if (menuCode.equals("4")) printAllMedicines();
        else if (menuCode.equals("5")) shouldKeepRendering = false;
        else System.out.println("**** Error: Invalid menu code");
    }

    void showAddMedicinePage() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Adding a new medicine ####");

        System.out.print("\nMedicine Name: ");
        String name = input.nextLine();

        System.out.print("Price: ");
        double price = input.nextDouble();

        // To ignore the newline
        input.nextLine();

        System.out.print("Production Date (yyyy-MM-dd HH:mm:ss): ");
        String productionDate = input.nextLine();

        System.out.print("Expiration Date (yyyy-MM-dd HH:mm:ss): ");
        String expirationDate = input.nextLine();

        // Time validation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime productionDT = LocalDateTime.parse(productionDate, formatter);
        LocalDateTime expirationDT = LocalDateTime.parse(expirationDate, formatter);
        LocalDateTime rightNowDT = LocalDateTime.now();

        if (productionDT.isAfter(expirationDT)) {
            System.out.println("Production time cannot be after the expiration time!");
        } else if (rightNowDT.isAfter(expirationDT)) {
            System.out.println("You can't add medicines that are already expired!");
        } else {
            hospital.addNewMedicine(name, price, productionDate, expirationDate);
            System.out.println("Medicine was added successfully.");
        }
    }

    //  Doctor pages
    void showDoctorRegistrationPage() {
        Scanner input = new Scanner(System.in);

        System.out.println("#### " + hospital.getName() + " :: Registering a Doctor ####");

        System.out.print("\nFull name: ");
        String fullName = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.print("Major: ");
        String major = input.nextLine();

        System.out.print("Secretary's name: ");
        String secretaryName = input.nextLine();

        Doctor newDoctor = hospital.registerDoctor(fullName, password, major, secretaryName);
        System.out.println("New doctor was registered successfully! Personnel ID: " + newDoctor.getPersonnelID());
    }

    void printAllDoctors() {
        ArrayList<Doctor> allDoctors = hospital.getDoctors();
        System.out.println("*** All Doctors in " + hospital.getName() + ":");
        for (Doctor doctor : allDoctors) {
            System.out.println(doctor.toString());
        }
    }

    void printAllMedicines() {
        ArrayList<Medicine> allMedicines = hospital.getMedicines();
        System.out.println("*** All medicines in " + hospital.getName() + ":");
        for (Medicine medicine : allMedicines) {
            System.out.println(medicine.toString());
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
        if (menuCode.equals("1")) printAllDoctorAppointments(hospital.loggedInDoctor);
        else if (menuCode.equals("2")) printAllMedicines();
        else if (menuCode.equals("3")) visitAPatient();
        else if (menuCode.equals("4")) shouldKeepRendering = false;
        else System.out.println("**** Error: Invalid menu code");
    }

    void printAllDoctorAppointments(Doctor doctor) {
        System.out.println("Here is all the appointments:");

        for (Appointment appointment : doctor.secretary.getGivenAppointments()) {
            System.out.println(appointment.toString());
        }
    }

    void visitAPatient() {
        LocalDateTime nowDT = LocalDateTime.now();
        boolean examined = false;
        // Finding an appointment to visit right now
        for (Appointment appointment : hospital.loggedInDoctor.secretary.getGivenAppointments()) {
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

                System.out.println("");

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

                hospital.loggedInDoctor.secretary.removeAnAppointment(appointment.getNumber());

                System.out.println("You examined the patient successfully!");
                examined = true;
            }
            if (examined) break;
        }
        if (!examined) System.out.println("You don't have any patient to visit right now!");
    }

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
        else if (menuCode.equals("3")) printAllDoctors();
        else if (menuCode.equals("4")) showFilterDoctorPage();
        else if (menuCode.equals("5")) printAllPatientAppointments(hospital.loggedInPatient);
        else if (menuCode.equals("6")) printAllPatientPrescriptions(hospital.loggedInPatient);
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

        hospital.loggedInPatient.editInfo(newFullName, newPassword, newPhoneNumber, descriptionOfProblem);

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

            if (!selectedDoctor.secretary.appointmentTimeIsFree(visitTime)) {
                System.out.println("Sorry! This time is already taken or the time has passed.");
                continue;
            }
            break;
        }

        Appointment fixedAppointment = selectedDoctor.secretary.fixAnAppointment(hospital.loggedInPatient, visitTime, selectedDoctor.getPersonnelID());
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