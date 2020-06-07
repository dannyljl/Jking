package com.guild.guild.Repository;


import com.guild.guild.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {

}
