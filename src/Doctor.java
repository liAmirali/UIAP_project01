class Doctor {
    private String fullName;
    private String password;
    private final String personnelID;
    private String major;

    public Doctor(String fullName, String password, String personnelID, String major) {
        this.fullName = fullName;
        this.password = password;
        this.personnelID = personnelID;
        this.major = major;
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
                ", password='" + getPassword() + '\'' +
                ", personnelID='" + getPersonnelID() + '\'' +
                ", major='" + getMajor() + '\'';
    }
}