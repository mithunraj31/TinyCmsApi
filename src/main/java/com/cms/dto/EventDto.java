package com.cms.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

public class EventDto {

    private String id;

    private String eventId;

    private String deviceId;

    private String driverId;

    private int type;

    private float lat;

    private float lng;

    private float gx;

    private float gy;

    private float gz;

    private float roll;

    private float pitch;

    private float yaw;

    private int status;

    private float direction;

    private float speed;

    private String videoId;

    private LocalDateTime time;

    private String username;

    private String videoUrl;

    private VideoDto video;

    public VideoDto getVideo() {
        return this.video;
    }

    public void setVideo(VideoDto video) {
        this.video = video;
    }

    @JsonIgnore
    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String VideoUrl) {
        this.videoUrl = VideoUrl;
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

    @JsonIgnore
    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    @JsonIgnore
    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    @JsonIgnore
    public float getGx() {
        return gx;
    }

    public void setGx(float gx) {
        this.gx = gx;
    }

    @JsonIgnore
    public float getGy() {
        return gy;
    }

    public void setGy(float gy) {
        this.gy = gy;
    }

    @JsonIgnore
    public float getGz() {
        return gz;
    }

    public void setGz(float gz) {
        this.gz = gz;
    }

    @JsonIgnore
    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    @JsonIgnore
    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    @JsonIgnore
    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    @JsonIgnore
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonIgnore
    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    @JsonIgnore
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @JsonIgnore
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

    @JsonGetter("userName")
    public String getUsername() {
        return username;
    }

    @JsonSetter("userName")
    public void setUsername(String username) {
        this.username = username;
    }

    public SensorDto getSensorValue() {
        SensorDto dto = new SensorDto();
        dto.setDirection(this.direction + "");
        dto.setGx(this.gx + "");
        dto.setGy(this.gy + "");
        dto.setGz(this.gz + "");
        dto.setLat(this.lat + "");
        dto.setLng(this.lng + "");
        dto.setPitch(this.pitch + "");
        dto.setRoll(this.roll + "");
        dto.setSpeed(this.speed);
        dto.setStatus(this.status);
        dto.setYaw(this.yaw + "");
        return dto;
    }
}