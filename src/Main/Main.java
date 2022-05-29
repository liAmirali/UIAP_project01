package Main;

import Console.*;
import Controllers.SecretaryController;
import User.Doctor;
import User.Secretary;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        HospitalConsole.clearConsole();

        AdminConsole adminConsole = new AdminConsole();
        DoctorConsole doctorConsole = new DoctorConsole();
        WatchmanConsole watchmanConsole = new WatchmanConsole();
        JanitorConsole janitorConsole = new JanitorConsole();
        SecretaryConsole secretaryConsole = new SecretaryConsole();
        PatientConsole patientConsole = new PatientConsole();

        Admin admin = Admin.getInstance("Admin", "Admin", "A00");

        HospitalConsole myConsole = new HospitalConsole(adminConsole, doctorConsole, watchmanConsole, janitorConsole, secretaryConsole, patientConsole);
        Hospital.getInstance("UI-AP Hospital", myConsole, admin);

        System.out.println("You have to add a watchman for the setup.");
        HospitalConsole.waitOnEnter();
        adminConsole.showAddWatchmanPage();

//        Doctor testDoc = new Doctor("Amirali", "Damirali", "1234", "090", "323o23g", "1234", 8, 100, "CE", "winwienwpoebnwpeobn");
//        Hospital.getInstance().getDoctors().add(testDoc);
//        Secretary testSec = new Secretary("Sepehr", "Sepehr", "1234", "2003", "225", "1234", 3, 3, "D1234");
//        testDoc.setSecretary(testSec);
//
//        Patient testPa1 = new Patient("QQQ", "P10", "1234", "09", "322sdg", "P1234", "o3voi34vo b3o4vb4b3oi b4oi n");
//        Patient testPa2 = new Patient("EEE", "P11", "1234", "09", "322sdg", "P1111", "o3voi34vo b3o4vb4b3oi b4oi n");
//        Patient testPa3 = new Patient("LLL", "P12", "1234", "09", "322sdg", "P2222", "o3voi34vo b3o4vb4b3oi b4oi n");
//
//        LocalDateTime visitTime1 = LocalDateTime.parse("2022-05-29 17:20:00");
//        LocalDateTime visitTime2 = LocalDateTime.parse("2022-06-10 18:20:00");
//        LocalDateTime visitTime3 = LocalDateTime.parse("2022-05-30 19:20:00");
//
//        SecretaryController.fixAnAppointment("P1234", visitTime1, "D1234", true);
//        SecretaryController.fixAnAppointment("P1111", visitTime2, "D1234", false);
//        SecretaryController.fixAnAppointment("P2222", visitTime3, "D1234", true);
//
//        Hospital.getInstance().getPatients().add(testPa1);
//        Hospital.getInstance().getPatients().add(testPa2);
//        Hospital.getInstance().getPatients().add(testPa3);


        while (true) {
            Hospital.getInstance().getConsole().showMainMenu();
        }
    }
}
