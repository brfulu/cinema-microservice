package io.fulu.bookingservice.models;

import java.util.Set;

public class UserRole {
    private long id;
    private String name;
    private String description;
    private Set<ApplicationUser> users;

    public UserRole() {

    }

    public UserRole(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
