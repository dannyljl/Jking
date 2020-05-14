package com.guild.guild.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageReceiver {
    @JsonProperty
    private HelloMessage message;
    @JsonProperty
    private User messageOwner;

    public HelloMessage getMessage() {
        return message;
    }

    public void setMessage(HelloMessage message) {
        this.message = message;
    }

    public User getMessageOwner() {
        return messageOwner;
    }

    public void setMessageOwner(User messageOwner) {
        this.messageOwner = messageOwner;
    }
}
