package com.guild.guild.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageReceiver {
    @JsonProperty
    private HelloMessage message;
    @JsonProperty
    private User messageOwner;
    @JsonProperty
    private Long guildId;
    @JsonProperty
    private String guildName;

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

    public Long getGuildId() {
        return guildId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public void setGuild(Long id, String name){

    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }
}
