package com.jking.login.login.Repository;


import com.jking.login.login.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

    User findBytoken(String token);

    User findByPassword(String password);
}
