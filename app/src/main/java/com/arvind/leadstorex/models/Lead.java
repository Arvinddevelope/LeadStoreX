package com.arvind.leadstorex.models;

public class Lead {
    private String id;
    private String name;
    private String number;
    private String notes;
    private String date;

    // Required no-argument constructor for Firebase
    public Lead() {
    }

    // Full constructor
    public Lead(String id, String name, String number, String notes, String date) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.notes = notes;
        this.date = date;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getNotes() {
        return notes;
    }

    public String getDate() {
        return date;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
