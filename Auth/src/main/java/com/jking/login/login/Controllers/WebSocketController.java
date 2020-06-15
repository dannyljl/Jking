package com.jking.login.login.Controllers;

import com.jking.login.login.websockets.MyStompSessionHandler;
import com.jking.login.login.websockets.WebSocketMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@RestController
@RequestMapping("/Socket")
public class WebSocketController {

    @SubscribeMapping
    @ResponseBody
    public ResponseEntity<WebSocketMessage> Connect(@RequestBody WebSocketMessage message) {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.connect("localhost:8080/chat/" + message.getFrom(), sessionHandler);
        return null;
    }
}
