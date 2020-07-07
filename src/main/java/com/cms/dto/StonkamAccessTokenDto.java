package com.cms.dto;

import java.time.LocalDateTime;

public class StonkamAccessTokenDto {

    private long accessToken;

    private LocalDateTime exipreDateTime;

    public StonkamAccessTokenDto() {

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