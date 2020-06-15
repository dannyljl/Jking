package com.guild.guild.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class HelloMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User messageOwner;

    @ManyToOne
    @JoinColumn(name= "guild_id")
    private Guild guilId;


    public HelloMessage() {}

    public HelloMessage(String name) {
        this.message = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getMessageOwner() {
        return messageOwner;
    }

    public void setMessageOwner(User messageOwner) {
        this.messageOwner = messageOwner;
    }

    public Guild getGuilId() {
        return guilId;
    }

    public void setGuilId(Guild guilId) {
        this.guilId = guilId;
    }
}
