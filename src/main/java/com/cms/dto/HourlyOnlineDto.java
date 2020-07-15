package com.cms.dto;


public class HourlyOnlineDto {

    private int hour;

    private String day;

    private long online;

    public HourlyOnlineDto() {

    }

    public HourlyOnlineDto(String day, int hour, long online) {
        this.hour = hour;
        this.day = day;
        this.online = online;
    }

    public String getDay() {
        return this.day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getOnline() {
        return this.online;
    }

    public void setOnline(long online) {
        this.online = online;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}