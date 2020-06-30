package com.guild.guild.classes;

import com.guild.guild.server.AccountType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class User {

    @Id
    private Long id;

    @Column(unique = true)
    private String username;

    private String firstName;
    private String lastName;
    private boolean guildLeader = false;
    @ManyToOne
    private Guild guild;


    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User() {
    }

    public User(AngularUser angularUser){
        this.id = angularUser.getId();
        this.firstName = angularUser.getFirstName();
        this.lastName = angularUser.getLastName();
        this.username = angularUser.getUsername();
        this.guildLeader = angularUser.isGuildLeader();
        this.guild = angularUser.getGuild();
    }

    public boolean isGuildLeader() {
        return guildLeader;
    }

    public void setGuildLeader(boolean guildLeader) {
        this.guildLeader = guildLeader;
    }

    public User(String username) {
        this.username = username;
    }
}
