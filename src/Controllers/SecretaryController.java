package Controllers;

import Exepctions.DoctorPersonnelIDNotExistsException;
import Main.Appointment;
import Main.Hospital;
import User.Doctor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SecretaryController {
    DoctorController doctorController;
    PatientController patientController;

    public SecretaryController() {
        this.doctorController = new DoctorController();
        this.patientController = new PatientController();
    }

    public boolean appointmentTimeIsFree(String doctorPersonnelID, String startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTimeDT = LocalDateTime.parse(startTime, formatter);
        LocalDateTime endTimeDT = startTimeDT.plusMinutes(15);
        LocalDateTime nowDT = LocalDateTime.now();

        if (nowDT.isAfter(startTimeDT)) return false;

        Doctor doctor;
        try {
            doctor = doctorController.findDoctorWithPersonnelID(doctorPersonnelID);
        } catch (DoctorPersonnelIDNotExistsException e) {
            return false;
        }

        for (Appointment appointment : doctor.getSecretary().getGivenAppointments()) {
            if (startTimeDT.isAfter(appointment.getStartDateTime()) && startTimeDT.isBefore(appointment.getEndDateTime())) {
                return false;
            }

            if (endTimeDT.isAfter(appointment.getStartDateTime()) && endTimeDT.isBefore(appointment.getEndDateTime())) {
                return false;
            }
        }

        return true;
    }

    public Appointment fixAnAppointment(String patientFileNumber, String startTime, String doctorPersonnelID, boolean isEmergency) {
        Doctor doctor;
        try {
            doctor = doctorController.findDoctorWithPersonnelID(doctorPersonnelID);
        } catch (DoctorPersonnelIDNotExistsException e) {
            return null;
        }

        int appointmentNumber = doctor.getSecretary().getGivenAppointments().size();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTimeDT = LocalDateTime.parse(startTime, formatter);
        LocalDateTime endTimeDT = startTimeDT.plusMinutes(15);

        Appointment newAppointment = new Appointment(appointmentNumber, startTimeDT, endTimeDT, patientFileNumber, doctorPersonnelID, isEmergency);
        doctor.getSecretary().getGivenAppointments().add(newAppointment);
        patientController.addAppointment(patientFileNumber, newAppointment);

        return newAppointment;
    }

    public void removeAnAppointment(String doctorPersonnelID, int appointmentNumber) {
        Doctor doctor;
        try {
            doctor = doctorController.findDoctorWithPersonnelID(doctorPersonnelID);
        } catch (DoctorPersonnelIDNotExistsException e) {
            return;
        }

        for (int i = 0; i < doctor.getSecretary().getGivenAppointments().size(); i++)
            if (doctor.getSecretary().getGivenAppointments().get(i).getNumber() == appointmentNumber)
                doctor.getSecretary().getGivenAppointments().remove(i);
    }

    public boolean secretaryPersonnelIDExists(String personnelIDToCheck) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getSecretary().getPersonnelID().equals(personnelIDToCheck)) return true;
        return false;
    }

    public boolean usernameExist(String usernameToCheck) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getSecretary().getUsername().equals(usernameToCheck)) return true;

        return false;
    }
}
