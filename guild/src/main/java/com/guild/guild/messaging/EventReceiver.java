package com.guild.guild.messaging;

import com.google.gson.Gson;
import com.guild.guild.Repository.IGuildRepository;
import com.guild.guild.Repository.IUserRepository;
import com.guild.guild.classes.AngularUser;
import com.guild.guild.classes.Guild;
import com.guild.guild.classes.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class EventReceiver {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IGuildRepository guildRepository;

    private Logger log = LoggerFactory.getLogger(EventReceiver.class);

    @RabbitListener(queues = "${jking.rabbitmq.queue}")
    public void receive(String userjson) {
        Gson gson = new Gson();
        System.out.println("received the event!");
        log.info("Received event: {}", userjson);
        AngularUser user = gson.fromJson(userjson, AngularUser.class);
        if (user.getDelete()) {
            userRepository.deleteById(user.getId());
        } else {
            User savedUser = new User(user);
            if (userRepository.existsById(user.getId())) {
                User oldUser = userRepository.getOne(user.getId());
                if (oldUser.getGuild() != null) {
                    Guild guild = oldUser.getGuild();
                    savedUser.setGuildLeader(oldUser.isGuildLeader());
                    savedUser.setGuild(guild);
                }
            }
            userRepository.save(savedUser);
        }
    }
}
