package com.cea.cares;

public class User {
    private int id,target,week;
    private String username, email;

    public User(int id, int target, String username, String email, int week) {
        this.id = id;
        this.target = target;
        this.username = username;
        this.email = email;
        this.week = week;
    }

    public int getId() {
        return id;
    }

    public int getTarget() {
        return target;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getWeek() { return week; }
}
