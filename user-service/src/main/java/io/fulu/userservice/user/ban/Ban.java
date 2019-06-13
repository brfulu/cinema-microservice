package io.fulu.userservice.user.ban;

import io.fulu.userservice.user.AppUser;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Ban {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private AppUser user;

    @ManyToOne
    @JoinColumn
    private AppUser admin;

    private Date begins;
    private long duration;

    public Ban() {

    }

    public Ban(AppUser user, AppUser admin, Date begins, long duration) {
        this.user = user;
        this.admin = admin;
        this.begins = begins;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public AppUser getAdmin() {
        return admin;
    }

    public void setAdmin(AppUser admin) {
        this.admin = admin;
    }

    public Date getBegins() {
        return begins;
    }

    public void setBegins(Date begins) {
        this.begins = begins;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
