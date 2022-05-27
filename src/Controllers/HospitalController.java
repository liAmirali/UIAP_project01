package Controllers;

import Main.Hospital;
import Main.Medicine;
import Main.Patient;
import User.Doctor;
import User.User;
import User.Watchman;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class HospitalController {
    public static boolean loginAdmin(String username, String password) {
        if (Hospital.getInstance().getAdmin().getUsername().equals(username) && Hospital.getInstance().getAdmin().getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public static boolean loginUser(String username, String password) {
        ArrayList<User> allUsers = new ArrayList<>();

        allUsers.addAll(Hospital.getInstance().getDoctors());
        allUsers.addAll(Hospital.getInstance().getPatients());
        allUsers.addAll(Hospital.getInstance().getWatchmen());
    }

    public static boolean loginWatchman(String username, String password) {
        for (Watchman watchman : Hospital.getInstance().getWatchmen()) {
            if (watchman.getUsername().equals(username) && watchman.getPassword().equals(password)) {

            }
        }
    }

    public static void checkoutEmployee(String personnelID) {
        WatchmanController.recordDeparture(personnelID, Hospital.getInstance().getCurrentTime());
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

    public static Watchman addWatchman(String fullName, String username, String password, String phoneNumber, String email, int mandatoryWorkHour, int hourlyWage) {
        Random rand = new Random();
        String personnelID = 'W' + String.valueOf(Math.abs(rand.nextInt()));

        Watchman newWatchman = new Watchman(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage);
        Hospital.getInstance().getWatchmen().add(newWatchman);

        return newWatchman;
    }

    public static void changeDate(LocalDate dateToJumpInto) {
        Hospital.getInstance().setCurrentTime(dateToJumpInto.atStartOfDay());
    }
}
