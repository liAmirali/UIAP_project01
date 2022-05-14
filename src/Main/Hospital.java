package Main;

import Console.HospitalConsole;

import java.util.ArrayList;
import java.util.Random;

/**
 * Hospital class is singleton
 */
public class Hospital {
    private static Hospital hospitalInstance = null;

    private final String name;
    private final ArrayList<Patient> patients;
    private final ArrayList<Doctor> doctors;
    private final ArrayList<Medicine> medicines;

    Doctor loggedInDoctor;
    Patient loggedInPatient;

    final private HospitalConsole console;

    // Constructors:
    private Hospital(String name, HospitalConsole hospitalConsole) {
        this.name = name;
        patients = new ArrayList<>();
        doctors = new ArrayList<>();
        medicines = new ArrayList<>();
        this.console = hospitalConsole;
    }

    // Getters:

    public static Hospital getInstance(String name, HospitalConsole hospitalConsole) {
        if (hospitalInstance == null)
            hospitalInstance = new Hospital(name, hospitalConsole);

        return hospitalInstance;
    }

    public static Hospital getInstance() {
        return hospitalInstance;
    }
    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }
    public String getName() {
        return name;
    }

    public Doctor getLoggedInDoctor() {
        return loggedInDoctor;
    }

    public Patient getLoggedInPatient() {
        return loggedInPatient;
    }

    public HospitalConsole getConsole() {
        return console;
    }

    //  Setters
    public void setLoggedInDoctor(Doctor loggedInDoctor) {
        this.loggedInDoctor = loggedInDoctor;
    }

    public void setLoggedInPatient(Patient loggedInPatient) {
        this.loggedInPatient = loggedInPatient;
    }

    //  Admin logics
    public boolean loginAdmin(String username, String password) {
        if (username.equals("Admin") && password.equals("Admin")) return true;
        else return false;
    }

    //  Patients logics
    public Patient registerPatient(String fullName, String password, String phoneNumber, String descriptionOfProblem) {
        Random rand = new Random();
        String fileNumber;
        while (true) {
            fileNumber = String.valueOf(Math.abs(rand.nextInt()));
            if (patientFileNumberExists(fileNumber)) continue;
            break;
        }

        Patient newPatient = new Patient(fullName, password, phoneNumber, fileNumber, descriptionOfProblem);
        patients.add(newPatient);
        return newPatient;
    }

    public boolean patientFileNumberExists(String fileNumberToCheck) {
        for (Patient patient : patients)
            if (patient.getFileNumber().equals(fileNumberToCheck)) return true;
        return false;
    }

    public boolean loginPatient(String username, String password) {
        for (Patient patient : getPatients())
            if (patient.getFileNumber().equals(username)) if (patient.getPassword().equals(password)) {
                setLoggedInPatient(patient);
                return true;
            }

        return false;
    }

    //  Doctors logics
    public Doctor registerDoctor(String fullName, String password, String major, String secretaryName) {
        Random rand = new Random();
        String personnelID;
        while (true) {
            personnelID = String.valueOf(Math.abs(rand.nextInt()));
            if (doctorPersonnelIDExists(personnelID)) continue;
            break;
        }

        Doctor newDoctor = new Doctor(fullName, password, personnelID, major, secretaryName);
        doctors.add(newDoctor);
        return newDoctor;
    }

    public boolean doctorPersonnelIDExists(String personnelIDToCheck) {
        for (Doctor doctor : doctors)
            if (doctor.getPersonnelID().equals(personnelIDToCheck)) return true;
        return false;
    }

    public Medicine addNewMedicine(String name, double price, String productionDate, String expirationDate) {
        Random rand = new Random();
        String ID;
        while (true) {
            ID = String.valueOf(Math.abs(rand.nextInt()));
            if (medicineIDExists(ID)) continue;
            break;
        }

        Medicine newMedicine = new Medicine(name, ID, price, productionDate, expirationDate);
        medicines.add(newMedicine);

        return newMedicine;
    }

    public boolean medicineIDExists(String IDToCheck) {
        for (Medicine medicine : medicines)
            if (medicine.getID().equals(IDToCheck)) return true;
        return false;
    }

    public boolean loginDoctor(String username, String password) {
        for (Doctor doctor : getDoctors())
            if (doctor.getPersonnelID().equals(username) && doctor.getPassword().equals(password)) {
                setLoggedInDoctor(doctor);
                return true;
            }

        return false;
    }

    public ArrayList<Doctor> filterDoctorsByMajor(String major) {
        ArrayList<Doctor> filteredDoctors = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getMajor().equals(major)) filteredDoctors.add(doctor);
        }

        return filteredDoctors;
    }
}
