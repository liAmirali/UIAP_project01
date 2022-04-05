import javax.print.Doc;
import java.util.ArrayList;
import java.util.Random;

public class Hospital {
    ArrayList<Patient> patients;
    ArrayList<Doctor> doctors;

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public Hospital() {
        patients = new ArrayList<Patient>();
        doctors = new ArrayList<Doctor>();
    }

    //  Patients logics
    public void registerPatient(String fullName, String password, String phoneNumber) {
        Random rand = new Random();
        String fileNumber;
        while (true) {
            fileNumber = String.valueOf(Math.abs(rand.nextInt()));
            if (patientFileNumberExists(fileNumber)) continue;
            break;
        }

        Patient newPatient = new Patient(fullName, password, phoneNumber, fileNumber);
        patients.add(newPatient);
    }

    boolean patientFileNumberExists(String fileNumberToCheck) {
        for (Patient patient : patients)
            if (patient.getFileNumber().equals(fileNumberToCheck))
                return true;
        return false;
    }

    //  Doctors logics
    public void registerDoctor(String fullName, String password, String major) {
        Random rand = new Random();
        String personnelID;
        while (true) {
            personnelID = String.valueOf(Math.abs(rand.nextInt()));
            if (doctorPersonnelIDExists(personnelID)) continue;
            break;
        }

        Doctor newDoctor = new Doctor(fullName, password, personnelID, major);
        doctors.add(newDoctor);
    }

    boolean doctorPersonnelIDExists(String personnelIDToCheck) {
        for (Doctor doctor : doctors)
            if (doctor.getPersonnelID().equals(personnelIDToCheck))
                return true;
        return false;
    }
}
