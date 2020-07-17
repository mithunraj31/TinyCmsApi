package com.cms.dao;

import com.cms.model.VideoConvertedModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoConvertedDao extends JpaRepository<VideoConvertedModel, String>{
    
}