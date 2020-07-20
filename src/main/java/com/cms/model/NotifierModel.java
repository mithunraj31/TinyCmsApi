package com.cms.model;

import javax.validation.constraints.NotEmpty;

public class NotifierModel {
    
    @NotEmpty(message = "deviceId: deviceId is required")
    private String deviceId;

    @NotEmpty(message = "eventId: eventId is required")
    private String eventId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
}