package com.cms.dao;

import java.util.List;

import com.cms.model.VideoModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface VideoDao extends JpaRepository<VideoModel, Integer>{
    @Query("FROM VideoModel v WHERE v.deviceId IN :ids")
    List<VideoModel> findVideosByDeviceIds(@Param("ids") List<String> ids);

    @Query(value="select * from video as v where v.event_id = ?1",nativeQuery = true)
    public List<VideoModel> getVideoByEventId(String event_id);
}