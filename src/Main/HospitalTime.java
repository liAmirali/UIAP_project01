package Main;

import java.time.Duration;
import java.time.LocalDateTime;

public class HospitalTime {
    private LocalDateTime timeOrigin;
    private LocalDateTime timeOriginSetTime;

    public HospitalTime() {
        LocalDateTime now = LocalDateTime.now();
        timeOrigin = now;
        timeOriginSetTime = now;
    }

    public HospitalTime(LocalDateTime timeOrigin) {
        this.timeOrigin = timeOrigin;
        timeOriginSetTime = LocalDateTime.now();
    }

    public void setTimeOrigin(LocalDateTime timeOrigin) {
        this.timeOrigin = timeOrigin;
    }

    public void setTimeOriginSetTime(LocalDateTime timeOriginSetTime) {
        this.timeOriginSetTime = timeOriginSetTime;
    }

    private Duration getForwardedDuration() {
        return Duration.between(timeOriginSetTime, timeOrigin);
    }

    public LocalDateTime getTime() {
        LocalDateTime nowNow = LocalDateTime.now();

        if (timeOrigin.isAfter(timeOriginSetTime))
            return nowNow.plus(getForwardedDuration());
        else
            return nowNow.minus(getForwardedDuration());
    }
}
