package Main;

import java.time.Duration;
import java.time.LocalDateTime;

public class ArrivalRecord {
    private final String userPersonnelID;
    private final LocalDateTime arrivalTime;
    private final LocalDateTime exitTime;

    public ArrivalRecord(String userPersonnelID, LocalDateTime arrivalTime, LocalDateTime exitTime) {
        this.arrivalTime = arrivalTime;
        this.exitTime = exitTime;
        this.userPersonnelID = userPersonnelID;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public String getUserPersonnelID() {
        return userPersonnelID;
    }

    public Duration getWorkingTime() {
        return Duration.between(arrivalTime, exitTime);
    }

    @Override
    public String toString() {
        return "PersonnelID: " + this.getUserPersonnelID() + '\n'
                + "Arrived at:  " + this.getArrivalTime() + '\n'
                + "Exited at:   " + this.getExitTime();
    }
}
