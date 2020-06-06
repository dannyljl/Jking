package com.guild.guild.classes;


import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Guild {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private List<User> users = new ArrayList<User>();

    private Long leader;

    @Column(unique = true)
    private String name;

    private int averageScore = 0;

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

    public void addUser(User user){
        users.add(user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getLeader() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public Guild() {
    }

    public Guild(GuildReceiver guildReceiver){
        this.addUser(guildReceiver.getLeader());
        this.leader = guildReceiver.getLeader().getId();
        this.name = guildReceiver.getName();
    }
}
