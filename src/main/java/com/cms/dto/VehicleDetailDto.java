package com.cms.dto;

import com.google.gson.annotations.SerializedName;

public class VehicleDetailDto {
    
    @SerializedName("DeviceId")
    private long deviceId;

    @SerializedName("Latitude")
    private String latitude;

    @SerializedName("Longitude")
    private String longitude;

    @SerializedName("PlateNumber")
    private String plateNumber;

    @SerializedName("ScanCode")
    private String scanCode;

    @SerializedName("ChannelNumber")
    private int channelNumber;

    @SerializedName("GroupName")
    private String groupName;

    @SerializedName("TcpServerAddr")
    private String tcpServerAddr;

    @SerializedName("TcpStreamOutPort")
    private int tcpStreamOutPort;

    @SerializedName("UdpServerAddr")
    private String udpServerAddr;

    @SerializedName("UdpStreamOutPort")
    private int udpStreamOutPort;

    @SerializedName("NetType")
    private int netType;

    @SerializedName("DeviceType")
    private String deviceType;

    @SerializedName("CreateTime")
    private String createTime;

    @SerializedName("IsActive")
    private boolean isActive;

    @SerializedName("IsOnline")
    private boolean isOnline;

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

	public boolean isIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isIsOnline() {
		return this.isOnline;
	}

	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}


    public String getPlateNumber() {
        return this.plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getScanCode() {
        return this.scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }

    public int getChannelNumber() {
        return this.channelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTcpServerAddr() {
        return this.tcpServerAddr;
    }

    public void setTcpServerAddr(String tcpServerAddr) {
        this.tcpServerAddr = tcpServerAddr;
    }

    public int getTcpStreamOutPort() {
        return this.tcpStreamOutPort;
    }

    public void setTcpStreamOutPort(int tcpStreamOutPort) {
        this.tcpStreamOutPort = tcpStreamOutPort;
    }

    public String getUdpServerAddr() {
        return this.udpServerAddr;
    }

    public void setUdpServerAddr(String udpServerAddr) {
        this.udpServerAddr = udpServerAddr;
    }

    public int getUdpStreamOutPort() {
        return this.udpStreamOutPort;
    }

    public void setUdpStreamOutPort(int udpStreamOutPort) {
        this.udpStreamOutPort = udpStreamOutPort;
    }

    public int getNetType() {
        return this.netType;
    }

    public void setNetType(int netType) {
        this.netType = netType;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }
}