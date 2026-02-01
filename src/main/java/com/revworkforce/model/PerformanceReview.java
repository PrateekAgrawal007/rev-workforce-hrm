package com.revworkforce.model;

public class PerformanceReview {

    private int reviewId;
    private int employeeId;
    private String achievements;
    private String improvements;
    private int selfRating;
    private String managerFeedback;
    private int managerRating;

    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getAchievements() { return achievements; }
    public void setAchievements(String achievements) { this.achievements = achievements; }

    public String getImprovements() { return improvements; }
    public void setImprovements(String improvements) { this.improvements = improvements; }

    public int getSelfRating() { return selfRating; }
    public void setSelfRating(int selfRating) { this.selfRating = selfRating; }

    public String getManagerFeedback() { return managerFeedback; }
    public void setManagerFeedback(String managerFeedback) { this.managerFeedback = managerFeedback; }

    public int getManagerRating() { return managerRating; }
    public void setManagerRating(int managerRating) { this.managerRating = managerRating; }
}
