package com.example.myapplication;

public class Input {

    private int id;

    private String name;

    private String date;

    public Input(int id, String name, String date){
        this.setId(id);
        this.setName(name);
        this.setDate(date);
    }

    public Input(String name, String date){
        this.setName(name);
        this.setDate(date);
    }

    public String toString() {
        return "Id: " + this.getId() + " - Name: " + this.getName() + " Date: " + this.getDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
