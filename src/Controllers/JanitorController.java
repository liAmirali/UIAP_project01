package Controllers;

import Main.Hospital;
import User.Janitor;

import java.util.Random;

public class JanitorController {
    public static Janitor addJanitor(String fullName, String username, String password, String phoneNumber, String email, int mandatoryWorkHour, int hourlyWage) {
        Random rand = new Random();
        String personnelID = String.valueOf(Math.abs(rand.nextInt()));

        Janitor newJanitor = new Janitor(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage);
        Hospital.getInstance().getJanitors().add(newJanitor);

        return newJanitor;
    }

    public static boolean usernameExists(String username) {
        for (Janitor janitor : Hospital.getInstance().getJanitors()) {
            if (janitor.getUsername().equals(username)) return true;
        }
        return false;
    }
}
