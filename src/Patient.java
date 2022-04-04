import java.util.ArrayList;

class Patient {
    private String fullName;
    private String password;
    private String phoneNumber;
    private String fileNumber;

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

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }
    ArrayList<Rx> prescriptions = new ArrayList<Rx>();
    ArrayList<Appointment> appointments = new ArrayList<Appointment>();
}