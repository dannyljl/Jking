package com.guild.guild.classes;

import org.codehaus.jackson.annotate.JsonProperty;

public class AngularUser {
    private Long id;
    private String lastName;
    private String firstName;
    private String username;

    private boolean delete = false;

    private boolean guildLeader = false;

    private Guild guild;

    public AngularUser() {
    }

    public AngularUser(User usersEntity){
        this.id = usersEntity.getId();
        this.firstName = usersEntity.getFirstName();
        this.lastName = usersEntity.getLastName();
        this.username = usersEntity.getUsername();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean getDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isDelete() {
        return delete;
    }

    public boolean isGuildLeader() {
        return guildLeader;
    }

    public void setGuildLeader(boolean guildLeader) {
        this.guildLeader = guildLeader;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }
}
