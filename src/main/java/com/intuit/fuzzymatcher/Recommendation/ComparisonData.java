package com.intuit.fuzzymatcher.Recommendation;

public class ComparisonData {
    private int age;
    private String civilStatus;
    private int numberOfCouples;

    public ComparisonData(int age, String civilStatus, int numberOfCouples) {
        this.age = age;
        this.civilStatus = civilStatus;
        this.numberOfCouples = numberOfCouples;
    }

    // Getters y Setters
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
