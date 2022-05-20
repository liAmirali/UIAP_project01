package Controllers;

import Exepctions.DoctorPersonnelIDNotExistsException;
import Main.Hospital;
import User.Doctor;
import User.Secretary;

import java.util.ArrayList;
import java.util.Random;

public class DoctorController {
    SecretaryController secretaryController;

    public DoctorController() {
        this.secretaryController = new SecretaryController();
    }

    public ArrayList<Doctor> filterDoctorsByMajor(String major) {
        ArrayList<Doctor> filteredDoctors = new ArrayList<>();
        for (Doctor doctor : Hospital.getInstance().getDoctors()) {
            if (doctor.getMajor().equals(major)) filteredDoctors.add(doctor);
        }

        return filteredDoctors;
    }

    public Doctor registerDoctor(String fullName, String username, String password, String phoneNumber, String email, int mandatoryWorkHour, int hourlyWage, String major, String biography) {
        Random rand = new Random();
        String personnelID;
        while (true) {
            personnelID = String.valueOf(Math.abs(rand.nextInt()));
            if (doctorPersonnelIDExists(personnelID)) continue;
            break;
        }

        Doctor newDoctor = new Doctor(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage, major, biography);
        Hospital.getInstance().getDoctors().add(newDoctor);
        return newDoctor;
    }

    public Secretary hireSecretary(String fullName, String username, String password, String phoneNumber, String email, int mandatoryWorkHour, int hourlyWage, String doctorPersonnelID) {
        Random rand = new Random();
        String personnelID;
        while (true) {
            personnelID = String.valueOf(Math.abs(rand.nextInt()));
            if (secretaryController.secretaryPersonnelIDExists(personnelID)) continue;
            break;
        }

        Secretary newSecretary = new Secretary(fullName, username, password, phoneNumber, email, personnelID, mandatoryWorkHour, hourlyWage, doctorPersonnelID);
        try {
            Doctor employerDoctor = findDoctorWithPersonnelID(doctorPersonnelID);
            employerDoctor.setSecretary(newSecretary);
            return newSecretary;
        } catch (DoctorPersonnelIDNotExistsException e) {
            return null;
        }
    }

    public boolean doctorPersonnelIDExists(String personnelIDToCheck) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getPersonnelID().equals(personnelIDToCheck)) return true;
        return false;
    }

    public Doctor findDoctorWithPersonnelID(String personnelIDToCheck) throws DoctorPersonnelIDNotExistsException {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getPersonnelID().equals(personnelIDToCheck)) return doctor;

        throw new DoctorPersonnelIDNotExistsException("No doctor was found with this personnelID");
    }

    public boolean usernameExist(String usernameToCheck) {
        for (Doctor doctor : Hospital.getInstance().getDoctors())
            if (doctor.getUsername().equals(usernameToCheck)) return true;

        return false;
    }
}
