package Controllers;

import Main.Hospital;
import Main.Medicine;
import Main.Patient;
import User.Doctor;

import java.util.Random;

public class HospitalController {
    public static boolean loginAdmin(String username, String password) {
        return username.equals("Admin") && password.equals("Admin");
    }

    public static boolean loginDoctor(String username, String password) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getPersonnelID().equals(username) && doctor.getPassword().equals(password)) {
                Hospital.getInstance().setLoggedInDoctor(doctor);
                return true;
            }

        return false;
    }

    public static boolean loginPatient(String username, String password) {
        for (Patient patient : Hospital.getInstance().getPatients())
            if (patient.getFileNumber().equals(username) && patient.getPassword().equals(password)) {
                Hospital.getInstance().setLoggedInPatient(patient);
                return true;
            }

        return false;
    }

    public static Medicine addNewMedicine(String name, double price, String productionDate, String expirationDate) {
        Random rand = new Random();
        String ID;
        while (true) {
            ID = String.valueOf(Math.abs(rand.nextInt()));
            if (medicineIDExists(ID)) continue;
            break;
        }

        Medicine newMedicine = new Medicine(name, ID, price, productionDate, expirationDate);
        Hospital.getInstance().getMedicines().add(newMedicine);

        return newMedicine;
    }

    public static boolean medicineIDExists(String IDToCheck) {
        for (Medicine medicine : Hospital.getInstance().getMedicines())
            if (medicine.getID().equals(IDToCheck)) return true;
        return false;
    }
}
