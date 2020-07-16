package com.cms.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "video_converted")
public class VideoConvertedModel {
    @Id
    @Column(name="event_id")
    private String eventId;

    @Column(name="url")
    private String url;

    @OneToOne(cascade = CascadeType.ALL, mappedBy="EventModel")
    private EventModel event;

}