package com.cms.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class StonkamAuthDto {

    private String userName;

    private boolean result;

    private String reson;

    private int userType;

    private long sessionId;

    @JsonGetter("UserName")
    public String getUserName() {
        return this.userName;
    }

    @JsonSetter("UserName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonGetter("Result")
    public boolean isResult() {
        return this.result;
    }

    @JsonSetter("Result")
    public void setResult(boolean result) {
        this.result = result;
    }

    @JsonGetter("Reson")
    public String getReson() {
        return this.reson;
    }

    @JsonSetter("Reson")
    public void setReson(String reson) {
        this.reson = reson;
    }

    @JsonGetter("UserType")
    public int getUserType() {
        return this.userType;
    }

    @JsonSetter("UserType")
    public void setUserType(int userType) {
        this.userType = userType;
    }

    @JsonGetter("SessionId")
    public long getSessionId() {
        return this.sessionId;
    }

    @JsonSetter("SessionId")
    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

}