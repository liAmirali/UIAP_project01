package Main;

import User.User;

import java.util.ArrayList;

public class Patient extends User {
    private final String fileNumber;
    private final ArrayList<Rx> prescriptions;
    private final ArrayList<Appointment> appointments;
    private String descriptionOfProblem;

    public Patient(String fullName, String username, String password, String phoneNumber, String email, String fileNumber, String descriptionOfProblem) {
        super(fullName, username, password, phoneNumber, email);
        this.fileNumber = fileNumber;
        this.prescriptions = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.descriptionOfProblem = descriptionOfProblem;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public ArrayList<Rx> getPrescriptions() {
        return prescriptions;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public String getDescriptionOfProblem() {
        return descriptionOfProblem;
    }

    public void setDescriptionOfProblem(String descriptionOfProblem) {
        this.descriptionOfProblem = descriptionOfProblem;
    }

    @Override
    public String toString() {
        return super.toString() + "fileNumber='" + fileNumber + '\'' +
                ", prescriptions=" + prescriptions +
                ", appointments=" + appointments +
                ", descriptionOfProblem='" + descriptionOfProblem + '\'';
    }
}