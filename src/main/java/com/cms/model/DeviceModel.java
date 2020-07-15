package com.cms.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class DeviceModel {
    @Id
    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "no_of_camera", insertable = false)
    private int noOfCamera;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "scan_code")
    private String scanCode;

    @Column(name = "channel_number")
    private int channelNumber;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "tcp_server_addr")
    private String tcpServerAddr;

    @Column(name = "tcp_stream_out_port")
    private int tcpStreamOutPort;

    @Column(name = "udp_server_addr")
    private String udpServerAddr;

    @Column(name = "udp_stream_out_port")
    private int udpStreamOutPort;

    @Column(name = "net_type")
    private int netType;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime update_time;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "stk_user")
    private String stkUser;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getNoOfCamera() {
        return noOfCamera;
    }

    public void setNoOfCamera(int noOfCamera) {
        this.noOfCamera = noOfCamera;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getScanCode() {
        return scanCode;
    }

    public void setScanCode(String scanCode) {
        this.scanCode = scanCode;
    }

    public int getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTcpServerAddr() {
        return tcpServerAddr;
    }

    public void setTcpServerAddr(String tcpServerAddr) {
        this.tcpServerAddr = tcpServerAddr;
    }

    public int getTcpStreamOutPort() {
        return tcpStreamOutPort;
    }

    public void setTcpStreamOutPort(int tcpStreamOutPort) {
        this.tcpStreamOutPort = tcpStreamOutPort;
    }

    public String getUdpServerAddr() {
        return udpServerAddr;
    }

    public void setUdpServerAddr(String udpServerAddr) {
        this.udpServerAddr = udpServerAddr;
    }

    public int getUdpStreamOutPort() {
        return udpStreamOutPort;
    }

    public void setUdpStreamOutPort(int udpStreamOutPort) {
        this.udpStreamOutPort = udpStreamOutPort;
    }

    public int getNetType() {
        return netType;
    }

    public void setNetType(int netType) {
        this.netType = netType;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getStkUser() {
        return stkUser;
    }

    public void setStkUser(String stkUser) {
        this.stkUser = stkUser;
    }

}