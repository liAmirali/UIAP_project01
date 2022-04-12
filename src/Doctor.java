import java.util.ArrayList;
import java.util.Random;

class Doctor {
    private final String fullName;
    private final String password;
    private final String personnelID;
    private final String major;
    final Secretary secretary;

    public Doctor(String fullName, String password, String personnelID, String major, String secretaryName) {
        this.fullName = fullName;
        this.password = password;
        this.personnelID = personnelID;
        this.major = major;

        Random rand = new Random();
        String secretaryPersonnelID = String.valueOf(Math.abs(rand.nextInt()));

        Secretary newSecretary = new Secretary(secretaryName, secretaryPersonnelID, personnelID);
        this.secretary = newSecretary;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getPersonnelID() {
        return personnelID;
    }

    public String getMajor() {
        return major;
    }

    @Override
    public String toString() {
        return "fullName='" + getFullName() + '\'' +
                ", personnelID='" + getPersonnelID() + '\'' +
                ", major='" + getMajor() + '\'';
    }
}