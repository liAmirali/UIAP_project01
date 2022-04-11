import java.util.ArrayList;
import java.util.Random;

public class Hospital {
    private final String name;
    private final ArrayList<Patient> patients;
    private final ArrayList<Doctor> doctors;
    private final ArrayList<Medicine> medicines;

    Doctor loggedInDoctor;
    Patient loggedInPatient;

    // Constructors:
    public Hospital(String name) {
        this.name = name;
        patients = new ArrayList<Patient>();
        doctors = new ArrayList<Doctor>();
        medicines = new ArrayList<Medicine>();
    }

    // Getters:
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

    //  Setters
    public void setLoggedInDoctor(Doctor loggedInDoctor) {
        this.loggedInDoctor = loggedInDoctor;
    }

    public void setLoggedInPatient(Patient loggedInPatient) {
        this.loggedInPatient = loggedInPatient;
    }

    //  Admin logics
    boolean loginAdmin(String username, String password) {
        if (username.equals("Admin") && password.equals("Admin")) return true;
        else return false;
    }

    //  Patients logics
    String registerPatient(String fullName, String password, String phoneNumber) {
        Random rand = new Random();
        String fileNumber;
        while (true) {
            fileNumber = String.valueOf(Math.abs(rand.nextInt()));
            if (patientFileNumberExists(fileNumber)) continue;
            break;
        }

        Patient newPatient = new Patient(fullName, password, phoneNumber, fileNumber);
        patients.add(newPatient);
        return fileNumber;
    }

    boolean patientFileNumberExists(String fileNumberToCheck) {
        for (Patient patient : patients)
            if (patient.getFileNumber().equals(fileNumberToCheck)) return true;
        return false;
    }

    boolean loginPatient(String username, String password) {
        for (Patient patient : getPatients())
            if (patient.getFileNumber().equals(username)) if (patient.getPassword().equals(password)) {
                setLoggedInPatient(patient);
                return true;
            }

        return false;
    }

    //  Doctors logics
    String registerDoctor(String fullName, String password, String major) {
        Random rand = new Random();
        String personnelID;
        while (true) {
            personnelID = String.valueOf(Math.abs(rand.nextInt()));
            if (doctorPersonnelIDExists(personnelID)) continue;
            break;
        }

        Doctor newDoctor = new Doctor(fullName, password, personnelID, major);
        doctors.add(newDoctor);
        return personnelID;
    }

    boolean doctorPersonnelIDExists(String personnelIDToCheck) {
        for (Doctor doctor : doctors)
            if (doctor.getPersonnelID().equals(personnelIDToCheck)) return true;
        return false;
    }

    void addNewMedicine(String name, double price, String productionDate, String expirationDate) {
        Medicine newMedicine = new Medicine(name, price, productionDate, expirationDate);
        medicines.add(newMedicine);
    }

    boolean loginDoctor (String username, String password) {
        for (Doctor doctor : getDoctors())
            if (doctor.getPersonnelID().equals(username) && doctor.getPassword().equals(password)) {
                setLoggedInDoctor(doctor);
                return true;
            }

        return false;
    }
}
