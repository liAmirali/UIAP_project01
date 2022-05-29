package Console;

import Controllers.EmployeeController;
import Controllers.HospitalController;
import Controllers.SecretaryController;
import Main.Appointment;
import Main.Hospital;

import java.util.ArrayList;
import java.util.Scanner;

non-sealed public class SecretaryConsole extends HospitalConsole {
    public void showSecretaryPanel() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Secretary Panel ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        Scanner input = new Scanner(System.in);

        System.out.println("(1) See sorted appointments");
        System.out.println("(2) See today appointments");
        System.out.println("(3) See this week appointments");
        System.out.println("(4) See month salary until today");
        System.out.println("(5) See worked days");
        System.out.println("(6) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();

        switch (menuCode) {
            case "1" -> printSortedAppointments();
            case "2" -> printAppointmentsByDay();
            case "3" -> printAppointmentsByWeek();
            case "4" -> {
                System.out.print("Your salary for this month until today: ");
                System.out.println(EmployeeController.getMonthSalary(Hospital.getInstance().getLoggedInSecretary().getPersonnelID()));
                waitOnEnter();
            }
            case "5" -> printWorkedDays(Hospital.getInstance().getLoggedInSecretary().getPersonnelID());
            case "6" -> {
                HospitalController.logoutEmployee(Hospital.getInstance().getLoggedInSecretary().getPersonnelID());
                shouldKeepRendering = false;
            }
            default -> {
                System.out.println("Invalid menu code. Try again.");

                waitOnEnter();
            }
        }
    }

    public void printSortedAppointments() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Sorted Appointments ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        ArrayList<Appointment> sortedAppointments = SecretaryController.getSortedAppointments(Hospital.getInstance().getLoggedInSecretary().getPersonnelID());

        for (Appointment appointment : sortedAppointments)
            System.out.println(appointment);

        waitOnEnter();
    }

    public void printAppointmentsByDay() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Today Appointments ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        ArrayList<Appointment> appointments = SecretaryController.getAppointmentsByDay(Hospital.getInstance().getLoggedInSecretary().getPersonnelID(), Hospital.getInstance().getHospitalTime().getTime().toLocalDate());

        for (Appointment appointment : appointments)
            System.out.println(appointment);

        waitOnEnter();
    }

    public void printAppointmentsByWeek() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: This week Appointments ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        ArrayList<Appointment> appointments = SecretaryController.getAppointmentsByWeek(Hospital.getInstance().getLoggedInSecretary().getPersonnelID(), Hospital.getInstance().getHospitalTime().getTime().toLocalDate());

        for (Appointment appointment : appointments)
            System.out.println(appointment);

        waitOnEnter();
    }
}
