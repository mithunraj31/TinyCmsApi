package com.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="camera")
public class CameraModel {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "rotation")
    private long rotation;

    @Column(name = "ch")
    private String ch;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getRotation() {
        return this.rotation;
    }

    public void setRotation(long rotation) {
        this.rotation = rotation;
    }

    public String getCh() {
        return this.ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

}