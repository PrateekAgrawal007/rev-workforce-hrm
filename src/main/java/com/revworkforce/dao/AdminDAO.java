package com.revworkforce.dao;

import com.revworkforce.db.DBConnection;
import com.revworkforce.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    public boolean addEmployee(int id, String name, String email, String password, String role, int managerId) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = """
                INSERT INTO employees 
                (employee_id, name, email, password, role, manager_id, active)
                VALUES (?, ?, ?, ?, ?, ?, true)
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, role);
            ps.setInt(6, managerId == 0 ? null : managerId);

            ps.executeUpdate();

            String balanceSql = "INSERT INTO leave_balance (employee_id) VALUES (?)";
            PreparedStatement bps = con.prepareStatement(balanceSql);
            bps.setInt(1, id);
            bps.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Employee> getAllEmployees() {

        List<Employee> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT employee_id, name, role FROM employees";

            ResultSet rs = con.prepareStatement(sql).executeQuery();

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

    public boolean toggleEmployee(int empId) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE employees SET active = NOT active WHERE employee_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, empId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean assignManager(int empId, int managerId) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE employees SET manager_id = ? WHERE employee_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, managerId);
            ps.setInt(2, empId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateLeaveBalance(int empId, int cl, int sl, int pl) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = """
                UPDATE leave_balance 
                SET casual_leave = ?, sick_leave = ?, paid_leave = ?
                WHERE employee_id = ?
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cl);
            ps.setInt(2, sl);
            ps.setInt(3, pl);
            ps.setInt(4, empId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
