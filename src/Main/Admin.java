package Main;

/*
 * Admin is a singleton class
 * */
public class Admin {
    private static Admin instance = null;

    private final String username;
    private final String password;
    private final String personnelID;

    private Admin(String username, String password, String personnelID) {
        this.username = username;
        this.password = password;
        this.personnelID = personnelID;
    }

    public static Admin getInstance(String username, String password, String personnelID) {
        if (instance == null) return new Admin(username, password, personnelID);
        return instance;
    }

    public static Admin getInstance() {
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPersonnelID() {
        return personnelID;
    }
}
