package Controllers;

import Main.ArrivalRecord;
import Main.Hospital;
import User.Watchman;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;

public class WatchmanController {
    public static void checkInUser(LocalDateTime checkInTime) {
        Hospital.getInstance().getLoggedInWatchman().setTempCheckInTime(checkInTime);
    }

    public static void recordDeparture(String personnelID, LocalDateTime departureTime) {
        LocalDateTime userArrivalTime = Hospital.getInstance().getLoggedInWatchman().getTempCheckInTime();
        ArrivalRecord arrivalRecord = new ArrivalRecord(personnelID, userArrivalTime, departureTime);
        Hospital.getInstance().getLoggedInWatchman().getArrivalRecords().add(arrivalRecord);
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
            if (arrivalDate.get(WeekFields.SUNDAY_START.weekOfYear()) == date.get(WeekFields.SUNDAY_START.weekOfYear())
                    && arrivalDate.getDayOfYear() >= date.getDayOfYear()
                    && arrivalDate.getYear() == date.getYear())
                selectedRecords.add(arrivalRecord);

        }

        return selectedRecords;
    }

    public static boolean usernameExists(String username) {
        for (Watchman watchman : Hospital.getInstance().getWatchmen()) {
            if (watchman.getUsername().equals(username)) return true;
        }
        return false;
    }
}
