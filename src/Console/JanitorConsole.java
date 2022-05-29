package Console;

import Controllers.EmployeeController;
import Controllers.HospitalController;
import Main.Breakdown;
import Main.Hospital;
import User.Janitor;

import java.util.Scanner;

non-sealed public class JanitorConsole extends HospitalConsole {
    public void showJanitorPanel() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Janitor Panel ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        Scanner input = new Scanner(System.in);

        System.out.println("(1) Report a breakdown");
        System.out.println("(2) See reported breakdowns");
        System.out.println("(3) See month salary until today");
        System.out.println("(4) Print worked days");
        System.out.println("(5) Logout");

        System.out.print("\nEnter menu code: ");
        String menuCode = input.nextLine();

        switch (menuCode) {
            case "1" -> showReportABreakDownPage();
            case "2" -> printReportedBreakdowns();
            case "3" -> {
                System.out.print("Your salary for this month until today: ");
                System.out.println(EmployeeController.getMonthSalary(Hospital.getInstance().getLoggedInJanitor().getPersonnelID()));
                waitOnEnter();
            }
            case "4" -> printWorkedDays(Hospital.getInstance().getLoggedInJanitor().getPersonnelID());
            case "5" -> {
                HospitalController.logoutEmployee(Hospital.getInstance().getLoggedInJanitor().getPersonnelID());
                shouldKeepRendering = false;
            }
            default -> {
                System.out.println("Invalid menu code. Try again.");
                waitOnEnter();
            }
        }
    }

    public void showReportABreakDownPage() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Report a Breakdown ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        Scanner input = new Scanner(System.in);
        String newEntry = input.nextLine();

        Janitor.getHospitalBreakdowns().add(new Breakdown(newEntry, false));

        System.out.println("Successfully added!");

        waitOnEnter();
    }

    public void printReportedBreakdowns() {
        clearConsole();

        System.out.println("#### " + Hospital.getInstance().getName() + " :: Hospital Breakdowns ####");
        System.out.println("Time: " + Hospital.getInstance().getHospitalTime().getTime());

        int counter = 0;
        for (Breakdown breakdown : Janitor.getHospitalBreakdowns()) {
            System.out.print("(" + (++counter) + ") ");
            System.out.println("[" + (breakdown.isAccepted() ? "X" : " ") + "] ");
            System.out.println(breakdown.getTitle());
        }

        System.out.println();
        System.out.println("Legend: [X] accepted");
        System.out.println("        [ ] not accepted");

        waitOnEnter();
    }
}
