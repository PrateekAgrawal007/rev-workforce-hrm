package com.revworkforce.service;

import com.revworkforce.dao.AdminDAO;
import com.revworkforce.model.Employee;

import java.util.List;
import java.util.Scanner;

public class AdminService {

    public void adminMenu() {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Admin Menu =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Activate / Deactivate Employee");
            System.out.println("4. Assign Manager");
            System.out.println("5. Update Leave Balance");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    toggleEmployeeStatus();
                    break;
                case 4:
                    assignManager();
                    break;
                case 5:
                    updateLeaveBalance();
                    break;
                case 0:
                    System.out.println("Admin logged out.");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }

    private void addEmployee() {

        Scanner sc = new Scanner(System.in);
        AdminDAO dao = new AdminDAO();

        System.out.print("Employee ID: ");
        int id = sc.nextInt();

        sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Role (EMPLOYEE/MANAGER): ");
        String role = sc.nextLine();

        System.out.print("Manager ID (0 if none): ");
        int managerId = sc.nextInt();

        boolean result = dao.addEmployee(id, name, email, password, role, managerId);

        if (result) {
            System.out.println("Employee added successfully.");
        } else {
            System.out.println("Failed to add employee.");
        }
    }

    private void viewAllEmployees() {

        AdminDAO dao = new AdminDAO();
        List<Employee> employees = dao.getAllEmployees();

        System.out.println("\n--- All Employees ---");

        for (Employee e : employees) {
            System.out.println(
                    e.getEmployeeId() + " | " +
                            e.getName() + " | " +
                            e.getRole()
            );
        }
    }

    private void toggleEmployeeStatus() {

        Scanner sc = new Scanner(System.in);
        AdminDAO dao = new AdminDAO();

        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();

        boolean result = dao.toggleEmployee(empId);

        if (result) {
            System.out.println("Employee status updated.");
        } else {
            System.out.println("Failed to update status.");
        }
    }

    private void assignManager() {

        Scanner sc = new Scanner(System.in);
        AdminDAO dao = new AdminDAO();

        System.out.print("Employee ID: ");
        int empId = sc.nextInt();

        System.out.print("New Manager ID: ");
        int managerId = sc.nextInt();

        boolean result = dao.assignManager(empId, managerId);

        if (result) {
            System.out.println("Manager assigned successfully.");
        } else {
            System.out.println("Failed to assign manager.");
        }
    }

    private void updateLeaveBalance() {

        Scanner sc = new Scanner(System.in);
        AdminDAO dao = new AdminDAO();

        System.out.print("Employee ID: ");
        int empId = sc.nextInt();

        System.out.print("Casual Leave: ");
        int cl = sc.nextInt();

        System.out.print("Sick Leave: ");
        int sl = sc.nextInt();

        System.out.print("Paid Leave: ");
        int pl = sc.nextInt();

        boolean result = dao.updateLeaveBalance(empId, cl, sl, pl);

        if (result) {
            System.out.println("Leave balance updated.");
        } else {
            System.out.println("Failed to update leave balance.");
        }
    }
}
