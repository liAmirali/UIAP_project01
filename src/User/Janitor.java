package User;

import java.util.ArrayList;

public class Janitor extends Employee {
    private final ArrayList<String> hospitalBreakdowns;

    public Janitor(String fullName, String username, String password, String phoneNumber, String email, String personnelID, int mandatoryWorkHour, int hourlyWage) {
        super(fullName, username, password, phoneNumber, email, 'J' + personnelID, mandatoryWorkHour, hourlyWage);
        this.hospitalBreakdowns = new ArrayList<>();
    }

    public ArrayList<String> getHospitalBreakdowns() {
        return hospitalBreakdowns;
    }
}
