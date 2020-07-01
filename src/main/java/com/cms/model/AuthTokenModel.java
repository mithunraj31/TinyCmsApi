package com.cms.model;

public class AuthTokenModel {

    private String token;

    public AuthTokenModel(){

    }

    public AuthTokenModel(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
