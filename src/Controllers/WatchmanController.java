package Controllers;

import Main.ArrivalRecord;
import Main.Hospital;
import User.Watchman;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Random;

public class WatchmanController {
    public static void checkInEmployee(LocalDateTime checkInTime) {
        Hospital.getInstance().getLoggedInWatchman().setTempEmployeeCheckInTime(checkInTime);
    }

    public static ArrivalRecord recordEmployeeDeparture(String personnelID, LocalDateTime departureTime) {
        LocalDateTime userArrivalTime = Hospital.getInstance().getLoggedInWatchman().getTempEmployeeCheckInTime();
        ArrivalRecord arrivalRecord = new ArrivalRecord(personnelID, userArrivalTime, departureTime);
        Hospital.getInstance().getLoggedInWatchman().getArrivalRecords().add(arrivalRecord);
        return arrivalRecord;
    }

    public static void checkInWatchman(LocalDateTime checkInTime) {
        Hospital.getInstance().getLoggedInWatchman().setTempWatchmanCheckInTime(checkInTime);
    }

    public static ArrivalRecord recordWatchmanDeparture(String personnelID, LocalDateTime departureTime) {
        LocalDateTime userArrivalTime = Hospital.getInstance().getLoggedInWatchman().getTempWatchmanCheckInTime();
        ArrivalRecord arrivalRecord = new ArrivalRecord(personnelID, userArrivalTime, departureTime);
        Hospital.getInstance().getLoggedInWatchman().getArrivalRecords().add(arrivalRecord);
        return arrivalRecord;
    }

    public static ArrayList<ArrivalRecord> getArrivalRecordsByDay(LocalDate date) {
        ArrayList<ArrivalRecord> selectedRecords = new ArrayList<>();

        for (ArrivalRecord arrivalRecord : Hospital.getInstance().getLoggedInWatchman().getArrivalRecords()) {
            LocalDateTime arrivalDate = arrivalRecord.getArrivalTime();
            if (arrivalDate.getDayOfYear() == date.getDayOfYear() && arrivalDate.getYear() == date.getYear())
                selectedRecords.add(arrivalRecord);

        }

        return selectedRecords;
    }

    public static ArrayList<ArrivalRecord> getArrivalRecordsByWeek(LocalDate date) {
        ArrayList<ArrivalRecord> selectedRecords = new ArrayList<>();

        for (ArrivalRecord arrivalRecord : Hospital.getInstance().getLoggedInWatchman().getArrivalRecords()) {
            LocalDateTime arrivalDate = arrivalRecord.getArrivalTime();
            if (arrivalDate.get(WeekFields.SUNDAY_START.weekOfYear()) == date.get(WeekFields.SUNDAY_START.weekOfYear()) && arrivalDate.getDayOfYear() >= date.getDayOfYear() && arrivalDate.getYear() == date.getYear())
                selectedRecords.add(arrivalRecord);

        }

        return selectedRecords;
    }

    public static Watchman addWatchman(String fullName, String username, String password, String phoneNumber, String email, int mandatoryWorkHour, int hourlyWage) {
        Random rand = new Random();
        String personnelID = 'W' + String.valueOf(Math.abs(rand.nextInt()));

        Watchman newWatchman = new Watchman(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage);
        Hospital.getInstance().getWatchmen().add(newWatchman);

        return newWatchman;
    }

    public static boolean usernameExists(String username) {
        for (Watchman watchman : Hospital.getInstance().getWatchmen()) {
            if (watchman.getUsername().equals(username)) return true;
        }
        return false;
    }
}
