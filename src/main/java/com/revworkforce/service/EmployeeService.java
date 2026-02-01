package com.revworkforce.service;

import com.revworkforce.model.Employee;
import com.revworkforce.dao.LoginDAO;
import com.revworkforce.dao.LeaveDAO;
import com.revworkforce.model.Leave;
import java.util.List;
import com.revworkforce.model.LeaveBalance;
import com.revworkforce.dao.NotificationDAO;
import com.revworkforce.model.Notification;
import com.revworkforce.dao.PerformanceDAO;
import com.revworkforce.model.PerformanceReview;

import java.util.Scanner;

public class EmployeeService {

    public void employeeMenu(Employee emp) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Employee Menu =====");
            System.out.println("1. View Profile");
            System.out.println("2. View Manager Details");
            System.out.println("3. Apply Leave");
            System.out.println("4. View My Leaves");
            System.out.println("5. View Leave Balance");
            System.out.println("6. Cancel Leave");
            System.out.println("7. View Notifications");
            System.out.println("8. Performance Review");

            System.out.println("0. Logout");

            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewProfile(emp);
                    break;

                case 2:
                    viewManager(emp);
                    break;

                case 3:
                    applyLeave(emp);
                    break;

                case 4:
                    viewMyLeaves(emp);
                    break;

                case 5:
                    viewLeaveBalance(emp);
                    break;

                case 6:
                    cancelLeave(emp);
                    break;
                case 7:
                    viewNotifications(emp);
                    break;

                case 8:
                    performanceReview(emp);
                    break;


                case 0:
                    System.out.println("Logged out successfully.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }

    private void viewProfile(Employee emp) {
        System.out.println("\n--- My Profile ---");
        System.out.println("Employee ID: " + emp.getEmployeeId());
        System.out.println("Name: " + emp.getName());
        System.out.println("Role: " + emp.getRole());
    }

    private void viewManager(Employee emp) {

        LoginDAO dao = new LoginDAO();
        Employee manager = dao.getManagerById(emp.getManagerId());

        if (manager != null) {
            System.out.println("\n--- Manager Details ---");
            System.out.println("Manager ID: " + manager.getEmployeeId());
            System.out.println("Name: " + manager.getName());
            System.out.println("Role: " + manager.getRole());
        } else {
            System.out.println("Manager not found.");
        }
    }

    private void applyLeave(Employee emp) {

        Scanner sc = new Scanner(System.in);
        Leave leave = new Leave();

        leave.setEmployeeId(emp.getEmployeeId());

        System.out.print("Enter Leave Type (CL/SL/PL): ");
        leave.setLeaveType(sc.next());

        System.out.print("Start Date (YYYY-MM-DD): ");
        leave.setStartDate(sc.next());

        System.out.print("End Date (YYYY-MM-DD): ");
        leave.setEndDate(sc.next());

        sc.nextLine(); // consume buffer
        System.out.print("Reason: ");
        leave.setReason(sc.nextLine());

        LeaveDAO dao = new LeaveDAO();

        if (dao.applyLeave(leave)) {
            System.out.println("Leave Applied Successfully (Pending Approval)");
        } else {
            System.out.println("Failed to Apply Leave");
        }
    }

    private void viewMyLeaves(Employee emp) {

        LeaveDAO dao = new LeaveDAO();
        List<Leave> leaves = dao.getLeavesByEmployee(emp.getEmployeeId());

        System.out.println("\n--- My Leaves ---");

        if (leaves.isEmpty()) {
            System.out.println("No leave records found.");
            return;
        }

        for (Leave l : leaves) {
            System.out.println(
                    "ID: " + l.getLeaveId() +
                            " | Type: " + l.getLeaveType() +
                            " | From: " + l.getStartDate() +
                            " | To: " + l.getEndDate() +
                            " | Status: " + l.getStatus()
            );
        }
    }
    private void viewLeaveBalance(Employee emp) {

        LeaveDAO dao = new LeaveDAO();
        LeaveBalance lb = dao.getLeaveBalance(emp.getEmployeeId());

        System.out.println("\n--- Leave Balance ---");
        System.out.println("Casual Leave: " + lb.getCasualLeave());
        System.out.println("Sick Leave: " + lb.getSickLeave());
        System.out.println("Paid Leave: " + lb.getPaidLeave());
    }

    private void cancelLeave(Employee emp) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Leave ID to cancel: ");
        int leaveId = sc.nextInt();

        LeaveDAO dao = new LeaveDAO();

        if (dao.cancelLeave(leaveId, emp.getEmployeeId())) {
            System.out.println("Leave cancelled successfully.");
        } else {
            System.out.println("Unable to cancel leave (Only PENDING leaves can be cancelled).");
        }
    }


    private void viewNotifications(Employee emp) {

        NotificationDAO dao = new NotificationDAO();
        List<Notification> notifications = dao.getUnreadNotifications(emp.getEmployeeId());

        System.out.println("\n--- Notifications ---");

        if (notifications.isEmpty()) {
            System.out.println("No new notifications.");
            return;
        }

        for (Notification n : notifications) {
            System.out.println("- " + n.getMessage());
        }

        dao.markAsRead(emp.getEmployeeId());
    }

    private void performanceReview(Employee emp) {

        Scanner sc = new Scanner(System.in);
        PerformanceDAO dao = new PerformanceDAO();
        PerformanceReview pr = new PerformanceReview();

        pr.setEmployeeId(emp.getEmployeeId());

        sc.nextLine();
        System.out.print("Achievements: ");
        pr.setAchievements(sc.nextLine());

        System.out.print("Areas of Improvement: ");
        pr.setImprovements(sc.nextLine());

        System.out.print("Self Rating (1-5): ");
        pr.setSelfRating(sc.nextInt());

        if (dao.submitReview(pr)) {
            System.out.println("Performance review submitted.");
        } else {
            System.out.println("Submission failed.");
        }
    }


}
