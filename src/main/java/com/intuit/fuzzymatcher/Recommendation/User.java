package com.intuit.fuzzymatcher.Recommendation;

public class User {
    private String name;
    private int ic;
    private int age;
    private double height;
    private String civilStatus;
    private int numberOfCouples;

    public User(String name, int ic, int age, double height, String civilStatus, int numberOfCouples) {
        this.name = name;
        this.ic = ic;
        this.age = age;
        this.height = height;
        this.civilStatus = civilStatus;
        this.numberOfCouples = numberOfCouples;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIc() {
        return ic;
    }

    public void setIc(int ic) {
        this.ic = ic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public int getNumberOfCouples() {
        return numberOfCouples;
    }

    public void setNumberOfCouples(int numberOfCouples) {
        this.numberOfCouples = numberOfCouples;
    }
}

