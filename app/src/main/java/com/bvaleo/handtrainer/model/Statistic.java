package com.bvaleo.handtrainer.model;

/**
 * Created by Valery on 18.03.2018.
 */

public class Statistic {

    private long id;
    private long userId = 0;
    private long count;
    private String comment;
    private String date;
    private String duration;

    public Statistic(long id, long userId, long count, String comment, String date, String duration) {
        this.id = id;
        this.userId = userId;
        this.count = count;
        this.comment = comment;
        this.date = date;
        this.duration = duration;
    }

    public Statistic(){};


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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
