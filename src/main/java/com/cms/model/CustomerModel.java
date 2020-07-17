package com.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class CustomerModel {
    @Id
    @Column(name= "id")
    private int id;

    @Column(name= "stk_user")
    private String stkUser;

    @Column(name= "sid")
    private String sid;

    @Column(name= "description")
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStkUser() {
        return stkUser;
    }

    public void setStkUser(String stkUser) {
        this.stkUser = stkUser;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}