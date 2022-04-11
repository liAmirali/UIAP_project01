import java.util.ArrayList;

class Patient {
    private String fullName;
    private String password;
    private String phoneNumber;
    private final String fileNumber;
    private ArrayList<Rx> prescriptions;
    private ArrayList<Appointment> appointments;

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public Patient(String fullName, String password, String phoneNumber, String fileNumber) {
        this.fullName = fullName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.fileNumber = fileNumber;

        prescriptions = new ArrayList<Rx>();
        appointments = new ArrayList<Appointment>();
    }
}