package io.fulu.userservice.user.ban;

import java.util.Date;

public class BanDto {
    private long id;
    private long userId;
    private long adminId;
    private Date begins;
    private long duration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
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
