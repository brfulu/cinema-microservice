package io.fulu.apigateway.model;


public class Movie {
    private long id;
    private String name;
    private String genre;
    private String description;
    private String actors;
    private int duration;

    public Movie() {

    }

    public Movie(String name, String genre, String description, String actors, int duration) {
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.actors = actors;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
