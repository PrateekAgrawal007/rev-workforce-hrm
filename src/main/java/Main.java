import com.revworkforce.dao.LoginDAO;
import com.revworkforce.model.Employee;
import com.revworkforce.service.EmployeeService;
import com.revworkforce.service.ManagerService;
import com.revworkforce.service.AdminService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LoginDAO loginDAO = new LoginDAO();

        System.out.println("===== Rev Workforce Login =====");

        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();

        System.out.print("Enter Password: ");
        String password = sc.next();

        Employee emp = loginDAO.login(empId, password);

        if (emp != null) {
            System.out.println("\nLogin Successful!");
            System.out.println("Welcome " + emp.getName());

            switch (emp.getRole()) {

                case "EMPLOYEE":
                    new EmployeeService().employeeMenu(emp);
                    break;

                case "MANAGER":
                    new ManagerService().managerMenu(emp);
                    break;


                case "ADMIN":
                    new AdminService().adminMenu();
                    break;

            }

        } else {
            System.out.println("\nInvalid Credentials!");
        }
    }
}
