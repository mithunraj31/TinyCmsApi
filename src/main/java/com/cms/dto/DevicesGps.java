package com.cms.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class DevicesGps {

    @SerializedName("DevicesGps")
    private List<VehicleDetailDto> devicesGps;

    @SerializedName("LongConnectionSendFlag")
    private boolean longConnectionSendFlag;

    public List<VehicleDetailDto> getDevicesGps() {
        return this.devicesGps;
    }

    public void setDevicesGps(List<VehicleDetailDto> devicesGps) {
        this.devicesGps = devicesGps;
    }

    public boolean isLongConnectionSendFlag() {
        return this.longConnectionSendFlag;
    }

    public void setLongConnectionSendFlag(boolean longConnectionSendFlag) {
        this.longConnectionSendFlag = longConnectionSendFlag;
    }
}