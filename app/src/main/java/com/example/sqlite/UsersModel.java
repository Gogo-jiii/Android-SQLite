package com.example.sqlite;

public class UsersModel {

    private int ID;
    private String name;

    public UsersModel() {
    }

    public UsersModel(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public String toString() {
        return "UsersModel{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }
}
