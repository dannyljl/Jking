package com.guild.guild.Repository;

import com.guild.guild.classes.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGuildRepository extends JpaRepository<Guild, Long> {
    Guild findByName(String name);
}
