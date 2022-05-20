package User;

import Main.Appointment;

import java.util.ArrayList;

public class Secretary extends Employee {
    private final String doctorPersonnelID;
    private final ArrayList<Appointment> givenAppointments;

    public Secretary(String fullName, String username, String password, String phoneNumber, String email, String personnelID, int mandatoryWorkHour, int hourlyWage, String doctorPersonnelID) {
        super(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage);
        this.doctorPersonnelID = doctorPersonnelID;

        givenAppointments = new ArrayList<>();
    }

    public String getDoctorPersonnelID() {
        return doctorPersonnelID;
    }

    public ArrayList<Appointment> getGivenAppointments() {
        return givenAppointments;
    }
}