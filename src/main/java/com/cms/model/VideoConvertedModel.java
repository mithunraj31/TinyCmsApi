package com.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "video_converted")
public class VideoConvertedModel {
    @Id
    @Column(name="event_id")
    private String eventId;

    @Column(name="url")
    private String url;

}