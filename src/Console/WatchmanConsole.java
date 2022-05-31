package Console;

import Controllers.EmployeeController;
import Main.ArrivalRecord;
import Main.Hospital;
import User.Watchman;

import java.time.LocalDateTime;
import java.util.Scanner;

non-sealed public class WatchmanConsole extends HospitalConsole {
    public void showWatchmanPanel() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Watchman Panel ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        Scanner input = new Scanner(System.in);

        System.out.println("(1) List today's check-ins/departures");
        System.out.println("(2) See worked days");
        System.out.println("(3) Get month salary until today");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();

        switch (menuCode) {
            case "1" -> printTodayArrivalRecords();
            case "2" -> printWorkedDays(Hospital.getInstance().getLoggedInWatchman().getPersonnelID());
            case "3" -> {
                System.out.print("Your salary for this month until today: ");
                System.out.println(EmployeeController.getMonthSalary(Hospital.getInstance().getLoggedInWatchman().getPersonnelID()));
                waitOnEnter();
            }
            default -> {
                System.out.println("Invalid menu code.");
                waitOnEnter();
            }
        }
    }

    public void printTodayArrivalRecords() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Today Arrival Records ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        LocalDateTime hospitalTime = Hospital.getInstance().getHospitalTime().getTime();

        for (ArrivalRecord arrivalRecord : Watchman.getArrivalRecords()) {
            if (arrivalRecord.getArrivalTime().getDayOfYear() == hospitalTime.getDayOfYear() && arrivalRecord.getArrivalTime().getYear() == hospitalTime.getYear()) {
                System.out.println(arrivalRecord);
            }
        }

        waitOnEnter();
    }
}
