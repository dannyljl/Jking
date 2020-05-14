package com.guild.guild.classes;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Guild {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_guild", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "guild_id"))
    private Set<User> users;

    @Column(unique = true)
    private String name;
    private int averageScore;

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
