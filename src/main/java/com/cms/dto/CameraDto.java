package com.cms.dto;

public class CameraDto {
   
	private long id;

    private String deviceId;

    private int rotation;

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

    public int getRotation() {
        return this.rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public String getCh() {
        return this.ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }
}