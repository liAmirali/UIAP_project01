package Main;

import Console.HospitalConsole;
import User.*;

import java.util.ArrayList;

/**
 * Hospital class is singleton
 */
public class Hospital {
    private static Hospital instance = null;

    private final String name;

    // Users
    private final ArrayList<Doctor> doctors;
    private final ArrayList<Patient> patients;
    private final ArrayList<Watchman> watchmen;
    private final ArrayList<Janitor> janitors;

    private final ArrayList<Medicine> medicines;

    private final HospitalTime hospitalTime;

    private Patient loggedInPatient;
    private Doctor loggedInDoctor;
    private Secretary loggedInSecretary;
    private Watchman loggedInWatchman;
    private Janitor loggedInJanitor;
    private Admin admin;

    final private HospitalConsole console;

    private Hospital(String name, HospitalConsole hospitalConsole, Admin admin) {
        this.name = name;

        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        watchmen = new ArrayList<>();
        janitors = new ArrayList<>();

        medicines = new ArrayList<>();

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

    public HospitalTime getHospitalTime() {
        return hospitalTime;
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

    public Janitor getLoggedInJanitor() {
        return loggedInJanitor;
    }

    public void setLoggedInJanitor(Janitor loggedInJanitor) {
        this.loggedInJanitor = loggedInJanitor;
    }

    public Secretary getLoggedInSecretary() {
        return loggedInSecretary;
    }

    public void setLoggedInSecretary(Secretary loggedInSecretary) {
        this.loggedInSecretary = loggedInSecretary;
    }

    public HospitalConsole getConsole() {
        return console;
    }

    public ArrayList<Watchman> getWatchmen() {
        return watchmen;
    }

    public ArrayList<Janitor> getJanitors() {
        return janitors;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> allEmployees = new ArrayList<>();

        allEmployees.addAll(Hospital.getInstance().getDoctors());
        allEmployees.addAll(Hospital.getInstance().getWatchmen());
        allEmployees.addAll(Hospital.getInstance().getJanitors());
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            allEmployees.add(doctor.getSecretary());

        return allEmployees;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();

        allUsers.addAll(Hospital.getInstance().getDoctors());
        allUsers.addAll(Hospital.getInstance().getPatients());
        allUsers.addAll(Hospital.getInstance().getWatchmen());
        allUsers.addAll(Hospital.getInstance().getJanitors());
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            allUsers.add(doctor.getSecretary());

        return allUsers;
    }
}