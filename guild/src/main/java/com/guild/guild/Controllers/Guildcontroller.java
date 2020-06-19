package com.guild.guild.Controllers;

import com.guild.guild.Repository.IGuildRepository;
import com.guild.guild.classes.User;
import com.guild.guild.classes.Guild;
import com.guild.guild.classes.GuildReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/guild")
public class Guildcontroller {

    @Autowired
    private IGuildRepository guildRepository;

    @GetMapping("/{guildName}")
    public Guild getGuild(@RequestHeader("Authorization") String token , @PathVariable String guildName){
        return guildRepository.findByName(guildName);
    }

    @PostMapping("/user")
    public Guild getUserGuild(@RequestBody User user){
        return guildRepository.findByUsers_Id(user.getId());
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
