package Main;

import java.time.LocalDateTime;

public class Appointment implements Comparable<Appointment> {
    private final int number;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final String patientFileNumber;
    private final String doctorPersonnelID;
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

    public boolean isEmergency() {
        return isEmergency;
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

    @Override
    public int compareTo(Appointment appointment) {
        if (this.isEmergency() && !appointment.isEmergency())
            return 1;
        else if (!this.isEmergency() && appointment.isEmergency())
            return -1;
        else
            return this.getNumber() - appointment.getNumber();

    }
}