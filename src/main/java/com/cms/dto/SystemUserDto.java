package com.cms.dto;

public class SystemUserDto {
	private String Username;
	private int SessionId;	
	private String Result;
	private String Reason;
	private int UserType;
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public int getSessionId() {
		return SessionId;
	}
	public void setSessionId(int sessionId) {
		SessionId = sessionId;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	public String getReason() {
		return Reason;
	}
	public void setReason(String reason) {
		Reason = reason;
	}
	public int getUserType() {
		return UserType;
	}
	public void setUserType(int userType) {
		UserType = userType;
	}	
	
}
