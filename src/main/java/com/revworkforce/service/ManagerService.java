package com.revworkforce.service;

import com.revworkforce.dao.ManagerDAO;
import com.revworkforce.model.Employee;
import com.revworkforce.model.Leave;
import com.revworkforce.dao.PerformanceDAO;
import com.revworkforce.model.PerformanceReview;

import java.util.List;
import java.util.Scanner;

public class ManagerService {

    public void managerMenu(Employee manager) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Manager Menu =====");
            System.out.println("1. View My Team");
            System.out.println("2. View Pending Leave Requests");
            System.out.println("3. Approve / Reject Leave");
            System.out.println("4. Review Employee Performance");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewTeam(manager);
                    break;

                case 2:
                    viewPendingLeaves(manager);
                    break;

                case 3:
                    processLeave(manager);
                    break;

                case 4:
                    reviewPerformance();
                    break;


                case 0:
                    System.out.println("Logged out successfully.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }

    private void viewTeam(Employee manager) {
        ManagerDAO dao = new ManagerDAO();
        List<Employee> team = dao.getTeamMembers(manager.getEmployeeId());

        System.out.println("\n--- My Team ---");

        for (Employee e : team) {
            System.out.println(
                    "ID: " + e.getEmployeeId() +
                            " | Name: " + e.getName() +
                            " | Role: " + e.getRole()
            );
        }
    }

    private void viewPendingLeaves(Employee manager) {
        ManagerDAO dao = new ManagerDAO();
        List<Leave> leaves = dao.getPendingLeaves(manager.getEmployeeId());

        System.out.println("\n--- Pending Leave Requests ---");

        for (Leave l : leaves) {
            System.out.println(
                    "Leave ID: " + l.getLeaveId() +
                            " | Emp ID: " + l.getEmployeeId() +
                            " | Type: " + l.getLeaveType() +
                            " | From: " + l.getStartDate() +
                            " | To: " + l.getEndDate()
            );
        }
    }

    private void processLeave(Employee manager) {

        Scanner sc = new Scanner(System.in);
        ManagerDAO dao = new ManagerDAO();

        System.out.print("Enter Leave ID: ");
        int leaveId = sc.nextInt();

        System.out.print("Approve or Reject (A/R): ");
        String decision = sc.next();

        sc.nextLine();
        System.out.print("Manager Comment: ");
        String comment = sc.nextLine();

        boolean result;

        if (decision.equalsIgnoreCase("A")) {
            result = dao.updateLeaveStatus(leaveId, "APPROVED", comment);
        } else {
            result = dao.updateLeaveStatus(leaveId, "REJECTED", comment);
        }

        if (result) {
            System.out.println("Leave updated successfully.");
        } else {
            System.out.println("Failed to update leave.");
        }
    }
    private void reviewPerformance() {

        Scanner sc = new Scanner(System.in);
        PerformanceDAO dao = new PerformanceDAO();

        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();

        PerformanceReview pr = dao.getReviewByEmployee(empId);

        if (pr == null) {
            System.out.println("No review submitted.");
            return;
        }

        sc.nextLine();
        System.out.println("Achievements: " + pr.getAchievements());
        System.out.println("Improvements: " + pr.getImprovements());
        System.out.println("Self Rating: " + pr.getSelfRating());

        System.out.print("Manager Feedback: ");
        String feedback = sc.nextLine();

        System.out.print("Manager Rating (1-5): ");
        int rating = sc.nextInt();

        dao.giveFeedback(pr.getReviewId(), feedback, rating);
        System.out.println("Feedback submitted.");
    }

}
