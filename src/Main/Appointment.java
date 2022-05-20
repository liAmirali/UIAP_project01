package Main;

import java.time.LocalDateTime;

public class Appointment {
    final private int number;
    final private LocalDateTime startDateTime;
    final private LocalDateTime endDateTime;
    final private String patientFileNumber;
    final private String doctorPersonnelID;
    private final boolean isEmergency;

    public Appointment(int number, LocalDateTime startDateTime, LocalDateTime endDateTime, String patientFileNumber,
                       String doctorPersonnelID, boolean isEmergency) {
        this.number = number;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.patientFileNumber = patientFileNumber;
        this.doctorPersonnelID = doctorPersonnelID;
        this.isEmergency = isEmergency;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "number=" + number +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", patientFileNumber='" + patientFileNumber + '\'' +
                ", doctorPersonnelID='" + doctorPersonnelID + '\'' +
                ", isEmergency=" + isEmergency;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getPatientFileNumber() {
        return patientFileNumber;
    }

    public String getDoctorPersonnelID() {
        return doctorPersonnelID;
    }
}