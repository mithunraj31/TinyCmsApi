package com.cms.dao;

import com.cms.model.DeviceModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceDao extends JpaRepository<DeviceModel, String>{
    
}