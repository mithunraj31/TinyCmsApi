package com.cms.dao;

import java.time.LocalDateTime;

public class StonkamAccessToken {

    private long accessToken;

    private LocalDateTime exipreDateTime;

    public StonkamAccessToken() {

    }

    public long getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(long accessToken) {
        this.accessToken = accessToken;
    }

    public LocalDateTime getExipreDateTime() {
        return this.exipreDateTime;
    }

    public void setExipreDateTime(LocalDateTime exipreDateTime) {
        this.exipreDateTime = exipreDateTime;
    }
    
}