package User;

import Main.Breakdown;

import java.util.ArrayList;

public class Janitor extends Employee {
    private final static ArrayList<Breakdown> hospitalBreakdowns = new ArrayList<>();

    public Janitor(String fullName, String username, String password, String phoneNumber, String email, String personnelID, int mandatoryWorkHour, int hourlyWage) {
        super(fullName, username, password, phoneNumber, email, 'J' + personnelID, mandatoryWorkHour, hourlyWage);
    }

    public static ArrayList<Breakdown> getHospitalBreakdowns() {
        return hospitalBreakdowns;
    }
}
