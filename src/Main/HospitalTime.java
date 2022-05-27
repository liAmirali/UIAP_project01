package Main;

import java.time.Duration;
import java.time.LocalDateTime;

public class HospitalTime {
    private LocalDateTime timeOrigin;
    private LocalDateTime timeOriginSetTime;

    public HospitalTime() {
        timeOrigin = LocalDateTime.now();
        timeOriginSetTime = LocalDateTime.now();
    }

    public HospitalTime(LocalDateTime timeOrigin) {
        this.timeOrigin = timeOrigin;
        timeOriginSetTime = LocalDateTime.now();
    }

    public LocalDateTime getTimeOrigin() {
        return timeOrigin;
    }

    public void setTimeOrigin(LocalDateTime timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public LocalDateTime getTimeOriginSetTime() {
        return timeOriginSetTime;
    }

    public void setTimeOriginSetTime(LocalDateTime timeOriginSetTime) {
        this.timeOriginSetTime = timeOriginSetTime;
    }

    private Duration getForwardedDuration() {
        return Duration.between(timeOrigin, timeOriginSetTime);
    }

    public LocalDateTime getHospitalTime() {
        LocalDateTime nowNow = LocalDateTime.now();

        if (timeOrigin.isAfter(timeOriginSetTime))
            return nowNow.plus(getForwardedDuration());
        else
            return nowNow.minus(getForwardedDuration());
    }
}
