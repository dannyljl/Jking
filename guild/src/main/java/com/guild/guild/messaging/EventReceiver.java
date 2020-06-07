package com.guild.guild.messaging;
import com.google.gson.Gson;
import com.guild.guild.Repository.IUserRepository;
import com.guild.guild.classes.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventReceiver {

    @Autowired
    private IUserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(EventReceiver.class);

    @RabbitListener(queues = "${jking.rabbitmq.queue}")
    public void receive(String userjson) {
        Gson gson = new Gson();
        System.out.println("received the event!");
        log.info("Received event in service document generation: {}", userjson);
        userRepository.save(gson.fromJson(userjson, User.class));
    }
}
