package com.revworkforce.dao;

import com.revworkforce.db.DBConnection;
import com.revworkforce.model.Employee;
import com.revworkforce.model.Leave;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.revworkforce.dao.NotificationDAO;

public class ManagerDAO {

    public List<Employee> getTeamMembers(int managerId) {

        List<Employee> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT employee_id, name, role FROM employees WHERE manager_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, managerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Employee e = new Employee();
                e.setEmployeeId(rs.getInt("employee_id"));
                e.setName(rs.getString("name"));
                e.setRole(rs.getString("role"));
                list.add(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Leave> getPendingLeaves(int managerId) {

        List<Leave> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = """
                    SELECT l.*
                    FROM leaves l
                    JOIN employees e ON l.employee_id = e.employee_id
                    WHERE e.manager_id = ? AND l.status = 'PENDING'
                    """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, managerId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Leave l = new Leave();
                l.setLeaveId(rs.getInt("leave_id"));
                l.setEmployeeId(rs.getInt("employee_id"));
                l.setLeaveType(rs.getString("leave_type"));
                l.setStartDate(rs.getString("start_date"));
                l.setEndDate(rs.getString("end_date"));
                list.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


//    public boolean updateLeaveStatus(int leaveId, String status, String comment) {
//
//        try {
//            Connection con = DBConnection.getConnection();
//
//            String sql = "UPDATE leaves SET status = ?, manager_comment = ? WHERE leave_id = ?";
//
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setString(1, status);
//            ps.setString(2, comment);
//            ps.setInt(3, leaveId);
//
//            return ps.executeUpdate() > 0;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
public boolean updateLeaveStatus(int leaveId, String status, String comment) {

    try {
        Connection con = DBConnection.getConnection();

        String getEmpSql = "SELECT employee_id FROM leaves WHERE leave_id = ?";
        PreparedStatement getPs = con.prepareStatement(getEmpSql);
        getPs.setInt(1, leaveId);
        ResultSet rs = getPs.executeQuery();

        int empId = 0;
        if (rs.next()) {
            empId = rs.getInt("employee_id");
        }

        String sql = "UPDATE leaves SET status = ?, manager_comment = ? WHERE leave_id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, status);
        ps.setString(2, comment);
        ps.setInt(3, leaveId);

        boolean updated = ps.executeUpdate() > 0;

        if (updated) {
            NotificationDAO ndao = new NotificationDAO();
            ndao.addNotification(
                    empId,
                    "Your leave request (ID " + leaveId + ") was " + status
            );
        }

        return updated;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

}
