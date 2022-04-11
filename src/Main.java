public class Main {
    public static void main(String[] args) {
        Hospital UIAPHospital = new Hospital("UIAP Hospital");
        HospitalConsole myConsole = new HospitalConsole(UIAPHospital);
        while (true) {
            myConsole.showLoginRegisterPage();
        }
    }
}
