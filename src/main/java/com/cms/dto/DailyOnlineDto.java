package com.cms.dto;

public class DailyOnlineDto {

    private String day;

    private long online;

    public DailyOnlineDto() {

    }

    public DailyOnlineDto(String day, long online) {
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
}