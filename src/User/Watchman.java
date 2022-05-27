package User;

import Main.ArrivalRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Watchman extends Employee {
    private static final ArrayList<ArrivalRecord> arrivalRecords = new ArrayList<>();
    private LocalDateTime tempCheckInTime;

    public Watchman(String fullName, String username, String password, String phoneNumber, String email, String personnelID, int mandatoryWorkHour, int hourlyWage) {
        super(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage);
    }

    public ArrayList<ArrivalRecord> getArrivalRecords() {
        return arrivalRecords;
    }

    public LocalDateTime getTempCheckInTime() {
        return tempCheckInTime;
    }

    public void setTempCheckInTime(LocalDateTime tempCheckInTime) {
        this.tempCheckInTime = tempCheckInTime;
    }
}
