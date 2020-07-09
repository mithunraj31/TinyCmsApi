package com.cms.dto;

public class VehicleDto {

	private Long id;

	private boolean isOnline;

	private boolean isActive;

	private LocationDto location;

	private VehicleDetailDto detail;

	public VehicleDetailDto getDetail() {
		return this.detail;
	}

	public void setDetail(VehicleDetailDto detail) {
		this.detail = detail;
	}


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
