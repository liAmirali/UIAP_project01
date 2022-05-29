package Controllers;

import Main.ArrivalRecord;
import Main.Hospital;
import Main.Medicine;
import Main.Patient;
import User.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Random;

public class HospitalController {
    public static boolean loginAdmin(String username, String password) {
        return Hospital.getInstance().getAdmin().getUsername().equals(username) && Hospital.getInstance().getAdmin().getPassword().equals(password);
    }

    public static boolean loginWatchman(String username, String password) {
        for (Watchman watchman : Hospital.getInstance().getWatchmen()) {
            if (watchman.getUsername().equals(username) && watchman.getPassword().equals(password)) {
                Hospital.getInstance().setLoggedInWatchman(watchman);
                WatchmanController.checkInWatchman(Hospital.getInstance().getHospitalTime().getTime());
                return true;
            }
        }
        return false;
    }

    public static boolean loginUser(String username, String password) {
        ArrayList<User> allUsers = Hospital.getInstance().getAllUsers();

        for (User user : allUsers) {
            if (user == null) continue;

            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                if (user instanceof Employee) {
                    if (((Employee) user).getWeeklyAbsenceCount() > 3) return false;
                }

                if (user instanceof Doctor) Hospital.getInstance().setLoggedInDoctor((Doctor) user);
                else if (user instanceof Patient) Hospital.getInstance().setLoggedInPatient((Patient) user);
                else if (user instanceof Janitor) Hospital.getInstance().setLoggedInJanitor((Janitor) user);
                else if (user instanceof Secretary) Hospital.getInstance().setLoggedInSecretary((Secretary) user);
                else return false;

                WatchmanController.checkInEmployee(Hospital.getInstance().getHospitalTime().getTime());
                return true;
            }
        }
        return false;
    }

    public static void logoutEmployee(String personnelID) {
        ArrivalRecord thisArrivalRecord = WatchmanController.recordEmployeeDeparture(personnelID, Hospital.getInstance().getHospitalTime().getTime());

        ArrayList<Employee> allEmployees = Hospital.getInstance().getAllEmployees();

        Employee wannaLogoutEmployee = null;

        for (Employee employee : allEmployees) {
            if (employee.getPersonnelID().equals(personnelID)) {
                wannaLogoutEmployee = employee;
                break;
            }
        }

        // Never will happen :)
        if (wannaLogoutEmployee == null) throw new RuntimeException("User not defined");

        int thisMonthWorkHourSum = 0;
        int workedWeekDays = 0;
        LocalDateTime hospitalTime = Hospital.getInstance().getHospitalTime().getTime();
        for (ArrivalRecord arrivalRecord : Watchman.getArrivalRecords()) {
            if (arrivalRecord.getUserPersonnelID().equals(wannaLogoutEmployee.getPersonnelID())) {
                if (arrivalRecord.getArrivalTime().getMonthValue() == hospitalTime.getMonthValue()) {
                    thisMonthWorkHourSum += (int) thisArrivalRecord.getWorkingTime().getSeconds() / 3600;
                }

                if (arrivalRecord.getArrivalTime().get(WeekFields.SUNDAY_START.weekOfYear()) == hospitalTime.get(WeekFields.SUNDAY_START.weekOfYear())
                        && arrivalRecord.getArrivalTime().getYear() == hospitalTime.getYear()
                        && (int) arrivalRecord.getWorkingTime().getSeconds() / 3600 <= wannaLogoutEmployee.getMandatoryWorkHour()
                ) {
                    LocalDate today = arrivalRecord.getArrivalTime().toLocalDate();
                    if (!wannaLogoutEmployee.getWorkedDays().contains(today)) {
                        wannaLogoutEmployee.getWorkedDays().add(today);
                        workedWeekDays += 1;
                    }
                }
            }
        }
        wannaLogoutEmployee.setWorkHourPerMonth(thisMonthWorkHourSum);
        wannaLogoutEmployee.setWorkDayCountInWeek(workedWeekDays);
        wannaLogoutEmployee.setWeeklyAbsenceCount(hospitalTime.getDayOfWeek().getValue() + 1 - workedWeekDays);


        Hospital.getInstance().setLoggedInDoctor(null);
        Hospital.getInstance().setLoggedInSecretary(null);
        Hospital.getInstance().setLoggedInJanitor(null);

    }

    public static void logoutWatchman(String personnelID) {
        Hospital.getInstance().setLoggedInWatchman(null);

        WatchmanController.recordWatchmanDeparture(personnelID, Hospital.getInstance().getHospitalTime().getTime());
    }

    public static Medicine addNewMedicine(String name, double price, String productionDate, String expirationDate) {
        Random rand = new Random();
        String ID;
        while (true) {
            ID = String.valueOf(Math.abs(rand.nextInt()));
            if (medicineIDExists(ID)) continue;
            break;
        }

        Medicine newMedicine = new Medicine(name, ID, price, productionDate, expirationDate);
        Hospital.getInstance().getMedicines().add(newMedicine);

        return newMedicine;
    }

    public static boolean medicineIDExists(String IDToCheck) {
        for (Medicine medicine : Hospital.getInstance().getMedicines())
            if (medicine.getID().equals(IDToCheck)) return true;
        return false;
    }

    public static void changeDate(LocalDate dateToJumpInto) {
        Hospital.getInstance().getHospitalTime().setTimeOrigin(dateToJumpInto.atStartOfDay());
        Hospital.getInstance().getHospitalTime().setTimeOriginSetTime(LocalDateTime.now());
    }
}
