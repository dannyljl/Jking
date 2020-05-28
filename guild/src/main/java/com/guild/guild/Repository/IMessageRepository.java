package com.guild.guild.Repository;

import com.guild.guild.classes.Guild;
import com.guild.guild.classes.HelloMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMessageRepository extends JpaRepository<HelloMessage, Long> {
}
