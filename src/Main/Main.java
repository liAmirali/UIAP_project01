package Main;

import Console.AdminConsole;
import Console.DoctorConsole;
import Console.HospitalConsole;
import Console.PatientConsole;

public class Main {
    public static void main(String[] args) {
        HospitalConsole.clearConsole();

        AdminConsole adminConsole = new AdminConsole();
        DoctorConsole doctorConsole = new DoctorConsole();
        PatientConsole patientConsole = new PatientConsole();

        HospitalConsole myConsole = new HospitalConsole(adminConsole, doctorConsole, patientConsole);
        Hospital.getInstance("UIAP Hospital", myConsole);
        while (true) {
            Hospital.getInstance().getConsole().showLoginRegisterPage();
        }
    }
}
