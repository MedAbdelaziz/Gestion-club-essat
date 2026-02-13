package com.example.class_projet_app;

public class ActivityModel {

    private String id;
    private String title;
    private String date;
    private String description;

    public ActivityModel(String id,String title, String date, String description) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public String getId() { return id;}
    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
}
