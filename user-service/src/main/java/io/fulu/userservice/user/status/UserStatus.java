package io.fulu.userservice.user.status;

import io.fulu.userservice.user.ApplicationUser;

import javax.persistence.*;
import java.util.Set;

@Entity
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int lowerBound;
    private int upperBound;
    private double discount;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private Set<ApplicationUser> users;

    public UserStatus() {

    }

    public UserStatus(String name, int lowerBound, int upperBound, double discount) {
        this.name = name;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.discount = discount;
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
