package com.guild.guild.classes;


import java.util.List;

public class Guild {

    private List<Long> users;
    private String name;
    private int averageScore;

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }

    public Guild() {
    }
}
