package com.cms.dao;


import com.cms.model.DeviceModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceDao extends JpaRepository<DeviceModel, String>{
    @Query(value="select * from device where device_id = ?1 limit 1;",nativeQuery = true)
    public DeviceModel getDeviceById(String device_id);
    
    @Query(value="select COUNT(*) as total from device where stk_user = ?", nativeQuery = true)
    public long getCountByUser(String user);
}