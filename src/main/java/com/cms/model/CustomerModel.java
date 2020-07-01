package com.cms.model;

public class CustomerModel {
	
	private CustomerList customers;

	public CustomerList getCustomers() {
		return customers;
	}

	public void setCustomers(CustomerList customers) {
		this.customers = customers;
	}
	
	
}

class CustomerList{
	
	private int id; 
	
	private String sid;
	
	private String description;
	
	private String stkUser;
	
	public int getId() {
		return id;
	}

	public String getSid() {
		return sid;
	}

	public String getDescription() {
		return description;
	}


	public void setId(int id) {
		this.id = id;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStkUser() {
		return stkUser;
	}

	public void setStkUser(String stkUser) {
		this.stkUser = stkUser;
	}


	
}
