package User;

import java.time.LocalDate;
import java.util.ArrayList;

public class Employee extends User {
    private String personnelID;
    private int workHourPerMonth;
    private int workDayCountInWeek;
    private int mandatoryWorkHour;
    private int hourlyWage;
    private int weeklyAbsenceCount;
    private final ArrayList<LocalDate> workedDays;

    public Employee(String fullName, String username, String password, String phoneNumber, String email, String personnelID, int mandatoryWorkHour, int hourlyWage) {
        super(fullName, username, password, phoneNumber, email);
        this.personnelID = personnelID;
        this.mandatoryWorkHour = mandatoryWorkHour;
        this.hourlyWage = hourlyWage;

        workedDays = new ArrayList<>();
    }

    public String getPersonnelID() {
        return personnelID;
    }

    public void setPersonnelID(String personnelID) {
        this.personnelID = personnelID;
    }

    public int getWorkHourPerMonth() {
        return workHourPerMonth;
    }

    public void setWorkHourPerMonth(int workHourPerMonth) {
        this.workHourPerMonth = workHourPerMonth;
    }

    public int getWorkDayCountInWeek() {
        return workDayCountInWeek;
    }

    public void setWorkDayCountInWeek(int workDayCountInWeek) {
        this.workDayCountInWeek = workDayCountInWeek;
    }

    public int getMandatoryWorkHour() {
        return mandatoryWorkHour;
    }

    public void setMandatoryWorkHour(int mandatoryWorkHour) {
        this.mandatoryWorkHour = mandatoryWorkHour;
    }

    public int getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(int hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public int getWeeklyAbsenceCount() {
        return weeklyAbsenceCount;
    }

    public void setWeeklyAbsenceCount(int weeklyAbsenceCount) {
        this.weeklyAbsenceCount = weeklyAbsenceCount;
    }

    public ArrayList<LocalDate> getWorkedDays() {
        return workedDays;
    }
}
