package com.cms.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class StonkamAuthUser {

    private String userName;

    private String password;

    private String version;

    private int authType;

    public StonkamAuthUser() {

    }

    public StonkamAuthUser(String userName,
     String password,
     String version,
     int authType) {
        this.setUserName(userName);
        this.setPassword(password);
        this.setVersion(version);
        this.setAuthType(authType);
    }

    @JsonGetter("Version")
    public String getVersion() {
        return this.version;
    }

    @JsonSetter("Version")
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonGetter("AuthType")
    public int getAuthType() {
        return this.authType;
    }

    @JsonSetter("AuthType")
    public void setAuthType(int authType) {
        this.authType = authType;
    }

    @JsonGetter("UserName")
    public String getUserName() {
        return this.userName;
    }

    @JsonSetter("UserName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonGetter("Password")
    public String getPassword() {
        return this.password;
    }

    @JsonSetter("Password")
    public void setPassword(String password) {
        this.password = password;
    }

}
