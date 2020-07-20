package com.cms.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service("RealTimeServiceImpl")
public class RealTimeServiceImpl {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    //  /topic/event/{username}
    //  /topic/event/admin
    // Sending Event message
    public void notifyEvent() {
        
        this.messagingTemplate.convertAndSend("/topic/event/admin","data");
        this.messagingTemplate.convertAndSend("/topic/event/"+"username", "data");
    }

}