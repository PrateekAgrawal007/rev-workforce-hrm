package com.revworkforce.dao;

import com.revworkforce.db.DBConnection;
import com.revworkforce.model.PerformanceReview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PerformanceDAO {

    public boolean submitReview(PerformanceReview pr) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = """
                INSERT INTO performance_reviews 
                (employee_id, achievements, improvements, self_rating)
                VALUES (?, ?, ?, ?)
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, pr.getEmployeeId());
            ps.setString(2, pr.getAchievements());
            ps.setString(3, pr.getImprovements());
            ps.setInt(4, pr.getSelfRating());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public PerformanceReview getReviewByEmployee(int empId) {

        PerformanceReview pr = null;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM performance_reviews WHERE employee_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, empId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pr = new PerformanceReview();
                pr.setReviewId(rs.getInt("review_id"));
                pr.setAchievements(rs.getString("achievements"));
                pr.setImprovements(rs.getString("improvements"));
                pr.setSelfRating(rs.getInt("self_rating"));
                pr.setManagerFeedback(rs.getString("manager_feedback"));
                pr.setManagerRating(rs.getInt("manager_rating"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pr;
    }

    public boolean giveFeedback(int reviewId, String feedback, int rating) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = """
                UPDATE performance_reviews
                SET manager_feedback = ?, manager_rating = ?, status = 'REVIEWED'
                WHERE review_id = ?
            """;

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, feedback);
            ps.setInt(2, rating);
            ps.setInt(3, reviewId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
