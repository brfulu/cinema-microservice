package io.fulu.apigateway.model;


import java.util.Set;

public class UserStatus {
    private long id;
    private String name;
    private int lowerBound;
    private int upperBound;
    private double discount;

    public UserStatus() {

    }

    public UserStatus(String name, int lowerBound, int upperBound, double discount) {
        this.name = name;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.discount = discount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
