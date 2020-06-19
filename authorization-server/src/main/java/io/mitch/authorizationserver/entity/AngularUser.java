package io.mitch.authorizationserver.entity;

import org.codehaus.jackson.annotate.JsonProperty;

public class AngularUser {
    private Long id;
    private String lastName;
    private String firstName;
    private String username;
    private String password;

    @JsonProperty
    private String access_token;

    public AngularUser() {
    }

    public AngularUser(UsersEntity usersEntity){
        this.id = usersEntity.getId();
        this.firstName = usersEntity.getFirstName();
        this.lastName = usersEntity.getLastName();
        this.username = usersEntity.getUsername();
    }

    public AngularUser(UsersEntity usersEntity, String token){
        this.id = usersEntity.getId();
        this.firstName = usersEntity.getFirstName();
        this.lastName = usersEntity.getLastName();
        this.username = usersEntity.getUsername();
        this.access_token = token;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
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
}
