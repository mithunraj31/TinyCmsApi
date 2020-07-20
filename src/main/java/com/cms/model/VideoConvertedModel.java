package com.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "video_converted")
public class VideoConvertedModel {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="url")
    private String url;
    

    @OneToOne
    @JoinColumn(name = "id")
    private EventModel event;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EventModel getEvent() {
        return this.event;
    }

    public void setEvent(EventModel event) {
        this.event = event;
    }



}