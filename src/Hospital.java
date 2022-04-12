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
        patients = new ArrayList<>();
        doctors = new ArrayList<>();
        medicines = new ArrayList<>();
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
    Patient registerPatient(String fullName, String password, String phoneNumber, String descriptionOfProblem) {
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
    Doctor registerDoctor(String fullName, String password, String major, String secretaryName) {
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

    boolean doctorPersonnelIDExists(String personnelIDToCheck) {
        for (Doctor doctor : doctors)
            if (doctor.getPersonnelID().equals(personnelIDToCheck)) return true;
        return false;
    }

    Medicine addNewMedicine(String name, double price, String productionDate, String expirationDate) {
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

    boolean medicineIDExists(String IDToCheck) {
        for (Medicine medicine : medicines)
            if (medicine.getID().equals(IDToCheck)) return true;
        return false;
    }

    boolean loginDoctor(String username, String password) {
        for (Doctor doctor : getDoctors())
            if (doctor.getPersonnelID().equals(username) && doctor.getPassword().equals(password)) {
                setLoggedInDoctor(doctor);
                return true;
            }

        return false;
    }

    ArrayList<Doctor> filterDoctorsByMajor(String major) {
        ArrayList<Doctor> filteredDoctors = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getMajor().equals(major)) filteredDoctors.add(doctor);
        }

        return filteredDoctors;
    }
}
