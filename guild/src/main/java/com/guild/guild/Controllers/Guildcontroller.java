package com.guild.guild.Controllers;

import com.guild.guild.Repository.IGuildRepository;
import com.guild.guild.classes.Guild;
import com.guild.guild.classes.GuildReceiver;
import com.guild.guild.classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/guild")
public class Guildcontroller {

    @Autowired
    private IGuildRepository guildRepository;

    @GetMapping("/{guildName}")
    public Guild getGuild(@PathVariable String guildName){
        return guildRepository.findByName(guildName);
    }

    @PostMapping("/{guildName}")
    public Guild joinGuild(@RequestBody User user, @PathVariable String guildName){
        Guild guild = guildRepository.findByName(guildName);
        guild.addUser(user);
        return guildRepository.save(guild);
    }

    @PostMapping
    public Guild CreateGuild(@RequestBody GuildReceiver guild){
          return guildRepository.save(new Guild(guild));
    }
}
