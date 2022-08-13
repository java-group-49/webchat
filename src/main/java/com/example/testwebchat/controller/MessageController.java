package com.example.testwebchat.controller;

import com.example.testwebchat.model.MessageModel;
import com.example.testwebchat.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class MessageController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel message){
        System.out.println("message: " + message + " to: " + to);
        boolean isExist = UserStorage.getInstance().getUsers().contains(to);
        if(isExist){
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        }
    }
}
