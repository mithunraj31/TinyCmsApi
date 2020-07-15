package com.cms.dao;

import com.cms.model.VideoModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoDao extends JpaRepository<VideoModel, Integer>{
    
}