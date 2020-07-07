package com.cms.dto;

public class CustomerStatusDto {
private int id;
private String name;
private int totalVehicle;
private int runningVehicle;
private String stk_user;
public String getStk_user() {
	return stk_user;
}
public void setStk_user(String stk_user) {
	this.stk_user = stk_user;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getTotalVehicle() {
	return totalVehicle;
}
public void setTotalVehicle(int totalVehicle) {
	this.totalVehicle = totalVehicle;
}
public int getRunningVehicle() {
	return runningVehicle;
}
public void setRunningVehicle(int runningVehicle) {
	this.runningVehicle = runningVehicle;
}

}
