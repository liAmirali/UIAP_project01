package Main;

import Console.HospitalConsole;
import User.Doctor;
import User.User;
import User.Watchman;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Hospital class is singleton
 */
public class Hospital {
    private static Hospital instance = null;

    private final String name;
    private final ArrayList<Doctor> doctors;
    private final ArrayList<Patient> patients;
    private final ArrayList<Medicine> medicines;
    private final ArrayList<Watchman> watchmen;

    private HospitalTime hospitalTime;

    private Doctor loggedInDoctor;
    private Patient loggedInPatient;
    private Watchman loggedInWatchman;
    private Admin admin;

    final private HospitalConsole console;

    private Hospital(String name, HospitalConsole hospitalConsole, Admin admin) {
        this.name = name;
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        medicines = new ArrayList<>();
        watchmen = new ArrayList<>();

        this.console = hospitalConsole;
        this.admin = admin;

        hospitalTime = new HospitalTime();
    }

    public static Hospital getInstance(String name, HospitalConsole hospitalConsole, Admin admin) {
        if (instance == null) instance = new Hospital(name, hospitalConsole, admin);

        return instance;
    }

    public static Hospital getInstance() {
        return instance;
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

    public Watchman getLoggedInWatchman() {
        return loggedInWatchman;
    }

    public void setLoggedInWatchman(Watchman loggedInWatchman) {
        this.loggedInWatchman = loggedInWatchman;
    }

    public HospitalConsole getConsole() {
        return console;
    }

    public ArrayList<Watchman> getWatchmen() {
        return watchmen;
    }

    public static void setHospitalInstance(Hospital instance) {
        Hospital.instance = instance;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}