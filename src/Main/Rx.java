package Main;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Rx {
    private final String rxID;
    private final LocalDateTime writtenOn;
    private final ArrayList<Medicine> medicines;
    private final String doctorPersonnelID;
    private final String patientFileNumber;

    public Rx(String rxID, LocalDateTime writtenOn, ArrayList<Medicine> medicines, String doctorPersonnelID, String patientFileNumber) {
        this.rxID = rxID;
        this.writtenOn = writtenOn;
        this.medicines = medicines;
        this.doctorPersonnelID = doctorPersonnelID;
        this.patientFileNumber = patientFileNumber;
    }

    public String getRxID() {
        return rxID;
    }

    public LocalDateTime getWrittenOn() {
        return writtenOn;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    public String getDoctorPersonnelID() {
        return doctorPersonnelID;
    }

    public String getPatientFileNumber() {
        return patientFileNumber;
    }

    @Override
    public String toString() {
        return "rxID='" + getRxID() + '\'' + ", writtenOn=" + getWrittenOn() + ", medicines=" + getMedicines() + ", doctorPersonnelID='" + getDoctorPersonnelID() + '\'' + ", patientFileNumber='" + getPatientFileNumber() + '\'';
    }
}

