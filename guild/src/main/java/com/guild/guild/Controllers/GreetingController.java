package com.guild.guild.Controllers;

import com.google.gson.Gson;
import com.guild.guild.classes.Greeting;
import com.guild.guild.classes.HelloMessage;
import com.guild.guild.classes.MessageReceiver;
import com.guild.guild.classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.UnknownServiceException;
import java.util.Arrays;

@Controller
public class GreetingController {

   /* RestTemplate restTemplate;

    @Autowired
    public GreetingController(RestTemplateBuilder builder){
        this.restTemplate = builder
                .build();
    }*/

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public HelloMessage greeting(MessageReceiver message) throws Exception {
        System.out.println(message.toString());
        HelloMessage helloMessage = new HelloMessage();
        helloMessage.setMessageOwner(message.getMessageOwner());
        helloMessage.setMessage(message.getMessage().getMessage());
        return helloMessage;
    }
}
