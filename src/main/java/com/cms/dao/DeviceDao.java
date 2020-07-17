package com.cms.dao;

import java.util.List;

import com.cms.model.DeviceModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceDao extends JpaRepository<DeviceModel, String>{
    @Query(value="select * from device where device_id = ?1 limit 1;",nativeQuery = true)
    public DeviceModel getDeviceById(String device_id);
    
}