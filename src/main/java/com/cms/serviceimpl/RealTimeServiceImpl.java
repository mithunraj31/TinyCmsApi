package com.cms.serviceimpl;

import com.cms.dto.EventDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service("RealTimeServiceImpl")
public class RealTimeServiceImpl {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private EventServiceImpl eventService;
    //  /topic/event/user/{username}
    //  /topic/event/user/admin
    // Sending Event message
    private void notifyEvent(EventDto eventDto) {
        
        this.messagingTemplate.convertAndSend("/topic/event/user/admin",eventDto);
        this.messagingTemplate.convertAndSend("/topic/event/user/"+eventDto.getUsername(), eventDto);
    }

    private void notifyEventOnVideo(EventDto eventDto) {
        this.messagingTemplate.convertAndSend("/topic/event/"+eventDto.getEventId(), eventDto);
    }

    public Boolean onEventOccur(final String eventId) {
        try {   
            EventDto event = this.eventService.getByEventId(eventId);
            this.notifyEvent(event);
            return true;
        } catch (Exception e) {
            return false;
        }
       
    }

    public Boolean onVideoStatusChanged(final String eventId){
        try {   
            EventDto event = this.eventService.getByEventId(eventId);
            this.notifyEventOnVideo(event);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}