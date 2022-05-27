package Main;

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
}
