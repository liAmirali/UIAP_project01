package Controllers;

import Exepctions.DoctorPersonnelIDNotExistsException;
import Main.Appointment;
import Main.Hospital;
import User.Doctor;
import User.Secretary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;

public class SecretaryController {
    public static boolean appointmentTimeIsFree(String doctorPersonnelID, String startTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTimeDT = LocalDateTime.parse(startTime, formatter);
        LocalDateTime endTimeDT = startTimeDT.plusMinutes(15);
        LocalDateTime nowDT = Hospital.getInstance().getHospitalTime().getTime();

        if (nowDT.isAfter(startTimeDT)) return false;

        Doctor doctor;
        try {
            doctor = DoctorController.findDoctorWithPersonnelID(doctorPersonnelID);
        } catch (DoctorPersonnelIDNotExistsException e) {
            return false;
        }

        int count = 0;
        for (Appointment appointment : doctor.getSecretary().getGivenAppointments()) {
            if (appointment.getStartDateTime().get(WeekFields.SUNDAY_START.weekOfYear()) == startTimeDT.get(WeekFields.SUNDAY_START.weekOfYear())) count++;

            if (count >= 10) return false;
        }

        for (Appointment appointment : doctor.getSecretary().getGivenAppointments()) {
            if (startTimeDT.isAfter(appointment.getStartDateTime()) && startTimeDT.isBefore(appointment.getEndDateTime())) {
                return false;
            }

            if (endTimeDT.isAfter(appointment.getStartDateTime()) && endTimeDT.isBefore(appointment.getEndDateTime())) {
                return false;
            }

            if (startTimeDT.get(WeekFields.SUNDAY_START.weekOfYear()) != Hospital.getInstance().getHospitalTime().getTime().get(WeekFields.SUNDAY_START.weekOfYear()))
                return false;
        }

        return true;
    }

    public static Appointment fixAnAppointment(String patientFileNumber, LocalDateTime startTime, String doctorPersonnelID, boolean isEmergency) {
        Doctor doctor;
        try {
            doctor = DoctorController.findDoctorWithPersonnelID(doctorPersonnelID);
        } catch (DoctorPersonnelIDNotExistsException e) {
            return null;
        }

        int appointmentNumber = doctor.getSecretary().getGivenAppointments().size();

        LocalDateTime endTimeDT = startTime.plusMinutes(15);

        Appointment newAppointment = new Appointment(appointmentNumber, startTime, endTimeDT, patientFileNumber, doctorPersonnelID, isEmergency);
        doctor.getSecretary().getGivenAppointments().add(newAppointment);
        PatientController.addAppointment(patientFileNumber, newAppointment);

        return newAppointment;
    }

    public static void removeAnAppointment(String doctorPersonnelID, int appointmentNumber) {
        Doctor doctor;
        try {
            doctor = DoctorController.findDoctorWithPersonnelID(doctorPersonnelID);
        } catch (DoctorPersonnelIDNotExistsException e) {
            return;
        }

        for (int i = 0; i < doctor.getSecretary().getGivenAppointments().size(); i++)
            if (doctor.getSecretary().getGivenAppointments().get(i).getNumber() == appointmentNumber) {
                doctor.getSecretary().getGivenAppointments().remove(i);
                break;
            }
    }

    public static ArrayList<Appointment> getAppointmentsByDay(String secretaryPersonnelID, LocalDate date) {
        ArrayList<Appointment> selectedAppointments = new ArrayList<>();

        for (Doctor doctor : Hospital.getInstance().getDoctors()) {
            Secretary secretary = doctor.getSecretary();

            if (secretary.getPersonnelID().equals(secretaryPersonnelID)) {
                for (Appointment appointment : secretary.getGivenAppointments()) {
                    LocalDateTime appointmentStartsOn = appointment.getStartDateTime();

                    if (appointmentStartsOn.getDayOfYear() == date.getDayOfYear() && appointmentStartsOn.getYear() == date.getYear())
                        selectedAppointments.add(appointment);
                }
                break;
            }
        }

        return selectedAppointments;
    }

    public static ArrayList<Appointment> getAppointmentsByWeek(String secretaryPersonnelID, LocalDate date) {
        ArrayList<Appointment> selectedAppointments = new ArrayList<>();

        for (Doctor doctor : Hospital.getInstance().getDoctors()) {
            Secretary secretary = doctor.getSecretary();

            if (secretary.getPersonnelID().equals(secretaryPersonnelID)) {
                for (Appointment appointment : secretary.getGivenAppointments()) {
                    LocalDateTime appointmentStartsOn = appointment.getStartDateTime();

                    if (appointmentStartsOn.get(WeekFields.SUNDAY_START.weekOfYear()) == date.get(WeekFields.SUNDAY_START.weekOfYear()) && appointmentStartsOn.getDayOfYear() >= date.getDayOfYear() && appointmentStartsOn.getYear() == date.getYear())
                        selectedAppointments.add(appointment);
                }
                break;
            }
        }

        return selectedAppointments;
    }

    public static ArrayList<Appointment> getSortedAppointments(String secretaryPersonnelID) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getSecretary().getPersonnelID().equals(secretaryPersonnelID)) {
                ArrayList<Appointment> sortedAppointments = new ArrayList<>(doctor.getSecretary().getGivenAppointments());
                sortedAppointments.sort(Collections.reverseOrder());
                return sortedAppointments;
            }

        throw new RuntimeException("secretaryPersonnelID (" + secretaryPersonnelID + ") does not exist");
    }

    public static boolean secretaryPersonnelIDExists(String personnelIDToCheck) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getSecretary() != null && doctor.getSecretary().getPersonnelID().equals(personnelIDToCheck))
                return true;
        return false;
    }

    public static boolean usernameExists(String usernameToCheck) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getSecretary() != null && doctor.getSecretary().getUsername().equals(usernameToCheck))
                return true;

        return false;
    }
}
