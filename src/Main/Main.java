package Main;

import Console.HospitalConsole;

public class Main {
    public static void main(String[] args) {
        HospitalConsole myConsole = new HospitalConsole();
        Hospital UIAPHospital = Hospital.getInstance("UIAP Hospital", myConsole);
        while (true) {
            UIAPHospital.getConsole().showLoginRegisterPage();
        }
    }
}
