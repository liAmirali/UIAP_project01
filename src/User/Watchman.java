package User;

import Main.ArrivalRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Watchman extends Employee {
    private static final ArrayList<ArrivalRecord> arrivalRecords = new ArrayList<>();
    private static LocalDateTime tempEmployeeCheckInTime;
    private static LocalDateTime tempWatchmanCheckInTime; // Because a watchman can simultaneously log in with another employee

    public Watchman(String fullName, String username, String password, String phoneNumber, String email, String personnelID, int mandatoryWorkHour, int hourlyWage) {
        super(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage);
    }

    public static ArrayList<ArrivalRecord> getArrivalRecords() {
        return arrivalRecords;
    }

    public LocalDateTime getTempEmployeeCheckInTime() {
        return tempEmployeeCheckInTime;
    }

    public void setTempEmployeeCheckInTime(LocalDateTime tempEmployeeCheckInTime) {
        this.tempEmployeeCheckInTime = tempEmployeeCheckInTime;
    }

    public LocalDateTime getTempWatchmanCheckInTime() {
        return tempWatchmanCheckInTime;
    }

    public void setTempWatchmanCheckInTime(LocalDateTime tempWatchmanCheckInTime) {
        this.tempWatchmanCheckInTime = tempWatchmanCheckInTime;
    }
}
