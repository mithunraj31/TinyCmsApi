package com.cms.model;

import java.util.Set;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user")
public class UserEntityModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int userId;
    @Column
    private String firstName;
    
    @Column
    private String lastName;
    
    @Column
    private String email;
    
    @Column
    private String stkUser;
    
    @Column
    @JsonIgnore
    private String password;

	@Column
    private int roleId;
    

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "USER_USERID") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ROLEID") })
    private Set<RoleEntityModel> roles;


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Set<RoleEntityModel> getRoles() {
		return roles;
	}


	public void setRoles(Set<RoleEntityModel> roles) {
		this.roles = roles;
	}


	public int getRoleId() {
		return roleId;
	}


	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


	public String getStkUser() {
		return stkUser;
	}


	public void setStkUser(String stkUser) {
		this.stkUser = stkUser;
	}

 
}
