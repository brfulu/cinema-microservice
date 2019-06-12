package io.fulu.bookingservice.models;

import javax.persistence.*;
import java.util.Date;

public class Ban {
    private long id;
    private ApplicationUser user;
    private ApplicationUser admin;
    private Date begins;
    private Date expires;

    public Ban() {

    }

    public Ban(ApplicationUser user, ApplicationUser admin, Date begins, Date expires) {
        this.user = user;
        this.admin = admin;
        this.begins = begins;
        this.expires = expires;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public ApplicationUser getAdmin() {
        return admin;
    }

    public void setAdmin(ApplicationUser admin) {
        this.admin = admin;
    }

    public Date getBegins() {
        return begins;
    }

    public void setBegins(Date begins) {
        this.begins = begins;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
