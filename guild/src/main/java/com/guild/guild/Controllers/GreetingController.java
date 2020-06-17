package com.guild.guild.Controllers;

import com.guild.guild.Repository.IMessageRepository;
import com.guild.guild.classes.Guild;
import com.guild.guild.classes.HelloMessage;
import com.guild.guild.classes.MessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    @Autowired
    private IMessageRepository messageRepository;

    @MessageMapping("/hello/{guildName}")
    @SendTo("/topic/greetings/{guildName}")
    public HelloMessage greeting(@DestinationVariable String guildName, MessageReceiver message) throws Exception {
        Guild guild = new Guild();
        guild.setId(message.getGuildId());
        guild.setName(message.getGuildName());
        System.out.println(message.toString());
        HelloMessage helloMessage = new HelloMessage();
        helloMessage.setMessageOwner(message.getMessageOwner());
        helloMessage.setMessage(message.getMessage().getMessage());
        helloMessage.setGuilId(guild);
//        messageRepository.save(helloMessage);
        return helloMessage;
    }
}
