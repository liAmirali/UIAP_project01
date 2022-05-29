package Controllers;

import Main.ArrivalRecord;
import Main.Hospital;
import User.Employee;
import User.Watchman;

import java.util.ArrayList;

public class EmployeeController {
    public static long getMonthSalary(String personnelID) {
        ArrayList<Employee> allEmployee = Hospital.getInstance().getAllEmployees();

        for (Employee employee : allEmployee) {
            if (employee.getPersonnelID().equals(personnelID)) {
                return (long) employee.getHourlyWage() * (long) employee.getWorkHourPerMonth();
            }
        }

        return 0;
    }

    public static ArrayList<ArrivalRecord> getWorkedDays(String personnelID) {
        ArrayList<ArrivalRecord> workedDays = new ArrayList<>();

        for (ArrivalRecord arrivalRecord : Watchman.getArrivalRecords()) {
            if (arrivalRecord.getUserPersonnelID().equals(personnelID)) {
                workedDays.add(arrivalRecord);
            }
        }
        return workedDays;
    }
}
