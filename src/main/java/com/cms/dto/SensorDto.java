package com.cms.dto;

public class SensorDto {
    private String lat;
    private String lng;
    private String gx;
    private String gy;
    private String gz;
    private String roll;
    private String pitch;
    private String yaw;
    private int status;
    private String direction;
    private float speed;

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return this.lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getGx() {
        return this.gx;
    }

    public void setGx(String gx) {
        this.gx = gx;
    }

    public String getGy() {
        return this.gy;
    }

    public void setGy(String gy) {
        this.gy = gy;
    }

    public String getGz() {
        return this.gz;
    }

    public void setGz(String gz) {
        this.gz = gz;
    }

    public String getRoll() {
        return this.roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getPitch() {
        return this.pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getYaw() {
        return this.yaw;
    }

    public void setYaw(String yaw) {
        this.yaw = yaw;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}