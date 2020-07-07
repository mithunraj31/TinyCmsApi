package com.cms.dto;

public class VehicleDto {
	private Long id;
	private boolean isOnline;
	private boolean isActive;
	private LocationDto location;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public LocationDto getLocation() {
		return location;
	}
	public void setLocation(LocationDto location) {
		this.location = location;
	}
	
}
