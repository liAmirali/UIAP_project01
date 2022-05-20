package Controllers;

import Exepctions.PatientFileNumberNotExistsException;
import Main.Appointment;
import Main.Hospital;
import Main.Patient;
import Main.Rx;

import java.util.Random;

public class PatientController {
    public static void editInfo(String fileNumber, String fullName, String password, String phoneNumber, String descriptionOfProblem) {
        Patient patient;
        try {
            patient = findPatientWithFileNumber(fileNumber);
        } catch (PatientFileNumberNotExistsException e) {
            return;
        }

        if (!fullName.equals("")) patient.setFullName(fullName);
        if (!password.equals("")) patient.setPassword(password);
        if (!phoneNumber.equals("")) patient.setPhoneNumber(phoneNumber);
        if (!descriptionOfProblem.equals("")) patient.setDescriptionOfProblem(descriptionOfProblem);
    }

    public static void addAppointment(String fileNumber, Appointment appointment) {
        Patient patient;
        try {
            patient = findPatientWithFileNumber(fileNumber);
        } catch (PatientFileNumberNotExistsException e) {
            return;
        }

        patient.getAppointments().add(appointment);
    }

    public static void addRx(String fileNumber, Rx newPrescription) {
        Patient patient;
        try {
            patient = findPatientWithFileNumber(fileNumber);
        } catch (PatientFileNumberNotExistsException e) {
            return;
        }

        patient.getPrescriptions().add(newPrescription);
    }

    public static Patient registerPatient(String fullName, String username, String email, String password, String phoneNumber, String descriptionOfProblem) {
        Random rand = new Random();
        String fileNumber;
        while (true) {
            fileNumber = String.valueOf(Math.abs(rand.nextInt()));
            if (patientFileNumberExists(fileNumber)) continue;
            break;
        }

        Patient newPatient = new Patient(fullName, username, email, password, phoneNumber, fileNumber, descriptionOfProblem);
        Hospital.getInstance().getPatients().add(newPatient);
        return newPatient;
    }

    public static boolean patientFileNumberExists(String fileNumberToCheck) {
        for (Patient patient : Hospital.getInstance().getPatients())
            if (patient.getFileNumber().equals(fileNumberToCheck)) return true;
        return false;
    }

    public static boolean usernameExist(String usernameToCheck) {
        for (Patient patient : Hospital.getInstance().getPatients())
            if (patient.getUsername().equals(usernameToCheck)) return true;

        return false;
    }

    public static Patient findPatientWithFileNumber(String fileNumber) throws PatientFileNumberNotExistsException {
        for (Patient patient : Hospital.getInstance().getPatients())
            if (patient.getFileNumber().equals(fileNumber)) return patient;

        throw new PatientFileNumberNotExistsException("No patient was found with this file number");
    }
}
