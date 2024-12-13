package com.study.yana.models;


public class Report {
    private String id;
    private String userId;
    private String description;
    private String issueType;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private long timestamp;

    public Report() {
        // Default constructor required for Firebase
    }

    public Report(String id, String userId, String description, String issueType, String imageUrl, double latitude, double longitude, long timestamp) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.issueType = issueType;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIssueType() { return issueType; }
    public void setIssueType(String issueType) { this.issueType = issueType; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}

