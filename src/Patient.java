import java.util.ArrayList;

public class Patient {
    private String fullName;
    private String password;
    private String phoneNumber;
    private final String fileNumber;
    private final ArrayList<Rx> prescriptions;
    private final ArrayList<Appointment> appointments;
    private String descriptionOfProblem;

    public Patient(String fullName, String password, String phoneNumber, String fileNumber, String descriptionOfProblem) {
        this.fullName = fullName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.fileNumber = fileNumber;
        this.descriptionOfProblem = descriptionOfProblem;

        prescriptions = new ArrayList<>();
        appointments = new ArrayList<>();
    }

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

    public ArrayList<Rx> getPrescriptions() {
        return prescriptions;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public String getDescriptionOfProblem() {
        return descriptionOfProblem;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDescriptionOfProblem(String descriptionOfProblem) {
        this.descriptionOfProblem = descriptionOfProblem;
    }

    @Override
    public String toString() {
        return "fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", fileNumber='" + fileNumber + '\'' +
                ", descriptionOfProblem='" + descriptionOfProblem + '\'';
    }

    void editInfo(String fullName, String password, String phoneNumber, String descriptionOfProblem) {
        if (!fullName.equals(""))
            setFullName(fullName);
        if (!password.equals(""))
            setPassword(password);
        if (!phoneNumber.equals(""))
            setPhoneNumber(phoneNumber);
        if (!descriptionOfProblem.equals(""))
            setDescriptionOfProblem(descriptionOfProblem);
    }

    void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    void addRx(Rx newPrescription) {
        prescriptions.add(newPrescription);
    }
}