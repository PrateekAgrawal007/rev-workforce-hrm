package com.revworkforce.dao;

import com.revworkforce.db.DBConnection;
import com.revworkforce.model.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public void addNotification(int empId, String message) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO notifications (employee_id, message) VALUES (?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, empId);
            ps.setString(2, message);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Notification> getUnreadNotifications(int empId) {

        List<Notification> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM notifications WHERE employee_id = ? AND is_read = false";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, empId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Notification n = new Notification();
                n.setNotificationId(rs.getInt("notification_id"));
                n.setMessage(rs.getString("message"));
                n.setRead(rs.getBoolean("is_read"));
                list.add(n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void markAsRead(int empId) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE notifications SET is_read = true WHERE employee_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, empId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
