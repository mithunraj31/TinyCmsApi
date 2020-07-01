package com.cms.model;

import javax.persistence.*;

@Entity
@Table(name="role")
public class RoleEntityModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int roleId;

    @Column
    private String roleName;

    @Column
    private String description;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    

}
