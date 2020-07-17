package com.cms.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class EventModel {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "driver_id")
    private String driverId;

    @Column(name = "type")
    private int type;

    @Column(name = "latitude")
    private float lat;

    @Column(name = "longitude")
    private float lng;

    @Column(name = "gx")
    private float gx;

    @Column(name = "gy")
    private float gy;

    @Column(name = "gz")
    private float gz;

    @Column(name = "roll")
    private float roll;

    @Column(name = "pitch")
    private float pitch;

    @Column(name = "yaw")
    private float yaw;

    @Column(name = "status")
    private int status;

    @Column(name = "direction")
    private float direction;

    @Column(name = "speed")
    private float speed;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "username")
    private String username;

    @OneToOne(mappedBy = "event", fetch = FetchType.EAGER)
    private VideoConvertedModel videoConverted;

    public VideoConvertedModel getVideoConverted() {
        return this.videoConverted;
    }

    public void setVideoConverted(VideoConvertedModel videoConverted) {
        this.videoConverted = videoConverted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getGx() {
        return gx;
    }

    public void setGx(float gx) {
        this.gx = gx;
    }

    public float getGy() {
        return gy;
    }

    public void setGy(float gy) {
        this.gy = gy;
    }

    public float getGz() {
        return gz;
    }

    public void setGz(float gz) {
        this.gz = gz;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
