package com.cms.dao;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cms.model.EventModel;

@Repository
public interface EventDao extends JpaRepository<EventModel, Integer> {

	@Query(value = "SELECT * FROM `event` WHERE  device_id = ?1", nativeQuery = true)
	List<EventModel> findByDeviceId(@Valid int deviceId);
	
	@Query(value = "SELECT * FROM `event` WHERE  event_id = ?1", nativeQuery = true)
	EventModel findByEventId(String eventId);

	@Query(value = "SELECT * FROM `event` WHERE  username = ?1", nativeQuery = true)
	List<EventModel> findByUsername(String username);
}