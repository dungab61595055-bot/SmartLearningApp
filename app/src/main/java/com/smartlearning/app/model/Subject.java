package com.smartlearning.app.model;

public class Subject {
    private int id;
    private int userId;
    private String name;
    private String description;
    private String color;
    private String createdAt;

    public Subject() {}

    public Subject(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public Subject(int id, int userId, String name, String description, String color, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.color = color;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
