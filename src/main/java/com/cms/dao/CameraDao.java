package com.cms.dao;

import java.util.List;

import com.cms.model.CameraModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraDao extends JpaRepository<CameraModel, Integer> {
    @Query(value="select * from camera as c where c.device_id = ?1",nativeQuery = true)
    public List<CameraModel> getCamerasByDeviceId(String deviceId);

    @Query("FROM CameraModel c WHERE c.deviceId IN :ids")
    List<CameraModel> findCamerasByDeviceIds(@Param("ids") List<String> ids);
}