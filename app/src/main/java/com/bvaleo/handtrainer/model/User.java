package com.bvaleo.handtrainer.model;

/**
 * Created by Valery on 19.03.2018.
 */

public class User {
    private long id;
    private String  login;
    private String password;


    public User(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
