package controller;

import data.entities.Message;
import data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, Message message) {
        var logger = Logger.getLogger(MessageController.class.getName());

        logger.log(Level.INFO,"handling send message: {0}", message);
        logger.log(Level.INFO," to:{0} ", to );
        var user = userRepository.findByUsername(to);
        if (user != null){
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        }
    }
}
