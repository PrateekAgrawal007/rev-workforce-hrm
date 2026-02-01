package com.revworkforce.dao;

import com.revworkforce.db.DBConnection;
import com.revworkforce.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    public Employee login(int employeeId, String password) {

        Employee employee = null;

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT employee_id, name, role, manager_id FROM employees WHERE employee_id = ? AND password = ? AND active = true";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, employeeId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                employee = new Employee();
                employee.setEmployeeId(rs.getInt("employee_id"));
                employee.setName(rs.getString("name"));
                employee.setRole(rs.getString("role"));
                employee.setManagerId(rs.getInt("manager_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employee;
    }


    public Employee getManagerById(int managerId) {

        Employee manager = null;

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT employee_id, name, role FROM employees WHERE employee_id = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, managerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                manager = new Employee();
                manager.setEmployeeId(rs.getInt("employee_id"));
                manager.setName(rs.getString("name"));
                manager.setRole(rs.getString("role"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return manager;
    }



}
