package com.guild.guild.Controllers;

import com.guild.guild.Repository.IGuildRepository;
import com.guild.guild.Repository.IUserRepository;
import com.guild.guild.classes.User;
import com.guild.guild.classes.Guild;
import com.guild.guild.classes.GuildReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/guild")
public class Guildcontroller {

    @Autowired
    private IGuildRepository guildRepository;

    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/{guildName}")
    public Guild getGuild(@PathVariable String guildName){
        return guildRepository.findByName(guildName);
    }

    @PostMapping("/user")
    public Guild getUserGuild(@RequestBody Long id){
        return userRepository.getOne(id).getGuild();
    }

    @PostMapping("/{guildName}")
    public User joinGuild(@RequestBody Long id, @PathVariable String guildName){
        Guild guild = guildRepository.findByName(guildName);
        User newUser = userRepository.getOne(id);
        newUser.setGuild(guild);
        return userRepository.save(newUser);
    }

    //create guild
    @PostMapping
    public Guild CreateGuild(@RequestBody GuildReceiver guild){
        User guildleader = userRepository.getOne(guild.getLeader().getId());
        Guild newGuild = guildRepository.save(new Guild(guild));
        guildleader.setGuild(newGuild);
        guildleader.setGuildLeader(true);
        userRepository.save(guildleader);
        return newGuild;
    }
}
