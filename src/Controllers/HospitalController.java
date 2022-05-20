package Controllers;

import Main.Hospital;
import Main.Medicine;
import Main.Patient;
import User.Doctor;

import java.util.Random;

public class HospitalController {
    private final Hospital hospital;

    public HospitalController() {
        hospital = Hospital.getInstance();
    }

    public boolean loginAdmin(String username, String password) {
        return username.equals("Admin") && password.equals("Admin");
    }

    public boolean loginDoctor(String username, String password) {
        for (Doctor doctor : hospital.getDoctors())
            if (doctor.getPersonnelID().equals(username) && doctor.getPassword().equals(password)) {
                hospital.setLoggedInDoctor(doctor);
                return true;
            }

        return false;
    }

    public boolean loginPatient(String username, String password) {
        for (Patient patient : hospital.getPatients())
            if (patient.getFileNumber().equals(username) && patient.getPassword().equals(password)) {
                hospital.setLoggedInPatient(patient);
                return true;
            }

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
        hospital.getMedicines().add(newMedicine);

        return newMedicine;
    }

    public boolean medicineIDExists(String IDToCheck) {
        for (Medicine medicine : hospital.getMedicines())
            if (medicine.getID().equals(IDToCheck)) return true;
        return false;
    }
}
