package Main;

import Console.HospitalConsole;
import User.Doctor;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Hospital class is singleton
 */
public class Hospital {
    private static Hospital hospitalInstance = null;

    private final String name;
    private final ArrayList<Doctor> doctors;
    private final ArrayList<Patient> patients;
    private final ArrayList<Medicine> medicines;

    private LocalDateTime currentTime;

    private Doctor loggedInDoctor;
    private Patient loggedInPatient;

    final private HospitalConsole console;

    private Hospital(String name, HospitalConsole hospitalConsole) {
        this.name = name;
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        medicines = new ArrayList<>();
        this.console = hospitalConsole;

        currentTime = LocalDateTime.now();
    }

    public static Hospital getInstance(String name, HospitalConsole hospitalConsole) {
        if (hospitalInstance == null) hospitalInstance = new Hospital(name, hospitalConsole);

        return hospitalInstance;
    }

    public static Hospital getInstance() {
        return hospitalInstance;
    }

    public String getName() {
        return name;
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

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public Doctor getLoggedInDoctor() {
        return loggedInDoctor;
    }

    public void setLoggedInDoctor(Doctor loggedInDoctor) {
        this.loggedInDoctor = loggedInDoctor;
    }

    public Patient getLoggedInPatient() {
        return loggedInPatient;
    }

    public void setLoggedInPatient(Patient loggedInPatient) {
        this.loggedInPatient = loggedInPatient;
    }

    public HospitalConsole getConsole() {
        return console;
    }
}