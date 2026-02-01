package com.revworkforce.dao;

import com.revworkforce.db.DBConnection;
import com.revworkforce.model.Leave;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.revworkforce.model.LeaveBalance;

public class LeaveDAO {

    public boolean applyLeave(Leave leave) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO leaves (employee_id, leave_type, start_date, end_date, reason) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, leave.getEmployeeId());
            ps.setString(2, leave.getLeaveType());
            ps.setString(3, leave.getStartDate());
            ps.setString(4, leave.getEndDate());
            ps.setString(5, leave.getReason());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Leave> getLeavesByEmployee(int empId) {

        List<Leave> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM leaves WHERE employee_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, empId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Leave l = new Leave();
                l.setLeaveId(rs.getInt("leave_id"));
                l.setLeaveType(rs.getString("leave_type"));
                l.setStartDate(rs.getString("start_date"));
                l.setEndDate(rs.getString("end_date"));
                l.setReason(rs.getString("reason"));
                l.setStatus(rs.getString("status"));
                list.add(l);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public LeaveBalance getLeaveBalance(int empId) {

        LeaveBalance lb = new LeaveBalance();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM leave_balance WHERE employee_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, empId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lb.setCasualLeave(rs.getInt("casual_leave"));
                lb.setSickLeave(rs.getInt("sick_leave"));
                lb.setPaidLeave(rs.getInt("paid_leave"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lb;
    }

    public boolean cancelLeave(int leaveId, int empId) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM leaves WHERE leave_id = ? AND employee_id = ? AND status = 'PENDING'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, leaveId);
            ps.setInt(2, empId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
