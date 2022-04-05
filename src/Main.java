public class Main {
    public static void main(String[] args) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.parse("2018-12-30 19:34:50", formatter);

        Hospital UIAPHospital = new Hospital();
        HospitalConsole myConsole = new HospitalConsole(UIAPHospital);
        myConsole.showLoginRegisterPage();

        System.out.println("bye!");
    }
}
