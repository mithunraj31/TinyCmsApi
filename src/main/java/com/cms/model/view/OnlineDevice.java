package com.cms.model.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(name = "getOnlineDevice", query = "SELECT * FROM online_device", resultClass = OnlineDevice.class)
public class OnlineDevice implements  Serializable {
    @Id
    private long id;

    @Column(name = "device_id")
    private long deviceId;

    @Column(name = "user_name")
    private String userName;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}