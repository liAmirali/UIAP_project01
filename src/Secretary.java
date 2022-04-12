import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class Secretary {
    private final String fullName;
    private final String personnelID;
    private final String doctorPersonnelID;
    private final ArrayList<Appointment> givenAppointments;

    public Secretary(String fullName, String personnelID, String doctorPersonnelID) {
        this.fullName = fullName;
        this.personnelID = personnelID;
        this.doctorPersonnelID = doctorPersonnelID;

        givenAppointments =  new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public String getPersonnelID() {
        return personnelID;
    }

    public String getDoctorPersonnelID() {
        return doctorPersonnelID;
    }

    public ArrayList<Appointment> getGivenAppointments() {
        return givenAppointments;
    }

    boolean appointmentTimeIsFree(String startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTimeDT = LocalDateTime.parse(startTime, formatter);
        LocalDateTime endTimeDT = startTimeDT.plusMinutes(15);
        LocalDateTime nowDT = LocalDateTime.now();

        if (nowDT.isAfter(startTimeDT)) return false;

        for (Appointment appointment : givenAppointments) {
            if (startTimeDT.isAfter(appointment.getStartDateTime()) && startTimeDT.isBefore(appointment.getEndDateTime())) {
                return false;
            }

            if (endTimeDT.isAfter(appointment.getStartDateTime()) && endTimeDT.isBefore(appointment.getEndDateTime())) {
                return false;
            }
        }

        return true;
    }

    Appointment fixAnAppointment(Patient patient, String startTime, String doctorPersonnelID) {
        int appointmentNumber = getGivenAppointments().size();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTimeDT = LocalDateTime.parse(startTime, formatter);
        LocalDateTime endTimeDT = startTimeDT.plusMinutes(15);

        Appointment newAppointment = new Appointment(appointmentNumber, startTimeDT, endTimeDT, patient.getFileNumber(), doctorPersonnelID);
        givenAppointments.add(newAppointment);
        patient.addAppointment(newAppointment);

        return newAppointment;
    }

    void removeAnAppointment(int appointmentNumber) {
        for (int i = 0; i < getGivenAppointments().size(); i++)
            if (getGivenAppointments().get(i).getNumber() == appointmentNumber) getGivenAppointments().remove(i);
    }
}