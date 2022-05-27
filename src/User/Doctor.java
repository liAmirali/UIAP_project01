package User;

public class Doctor extends Employee {
    private final String major;
    private String biography;
    private Secretary secretary;

    public Doctor(String fullName, String username, String password, String phoneNumber, String email, String personnelID, int mandatoryWorkHour, int hourlyWage, String major, String biography) {
        super(fullName, username, password, phoneNumber, email, 'D' + personnelID, mandatoryWorkHour, hourlyWage);
        this.major = major;
        this.biography = biography;
        this.secretary = null;
    }

    public String getMajor() {
        return major;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Secretary getSecretary() {
        return secretary;
    }

    public void setSecretary(Secretary secretary) {
        this.secretary = secretary;
    }

    @Override
    public String toString() {
        return "fullName='" + getFullName() + '\'' + ", personnelID='" + getPersonnelID() + '\'' + ", major='" + getMajor() + '\'';
    }
}