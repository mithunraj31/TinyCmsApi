package com.cms.dao;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cms.model.EventModel;
import com.cms.model.EventStatModel;

@Repository
public interface EventDao extends JpaRepository<EventModel, Integer> {

	@Query(value = "SELECT * FROM `event` WHERE  device_id = ?1", nativeQuery = true)
	List<EventModel> findByDeviceId(@Valid int deviceId);
	
	@Query(value = "SELECT * FROM `event` WHERE  event_id = ?1", nativeQuery = true)
	EventModel findByEventId(String eventId);

	@Query(value = "SELECT * FROM `event` WHERE  username = ?1", nativeQuery = true)
	List<EventModel> findByUsername(String username);

	@Query(value = "select count(*) as total,"
	+ "sum(case when type=16 then 1 else 0 end) as accelerate,"
	+"sum(case when type=17 then 1 else 0 end) as decelerate,"
	+"sum(case when type=20 then 1 else 0 end) as impact,"
	+"sum(case when type=21 then 1 else 0 end) as turn_left,"
	+"sum(case when type=22 then 1 else 0 end) as turn_right,"
	+"sum(case when type=14 then 1 else 0 end) as button "
	+"from event where type in (14,16,17,20,21,22)", nativeQuery = true)
	Map<Object,Object> getEventStat();

	@Query(value = "select count(*) as total,"
	+ "sum(case when type=16 then 1 else 0 end) as accelerate,"
	+"sum(case when type=17 then 1 else 0 end) as decelerate,"
	+"sum(case when type=20 then 1 else 0 end) as impact,"
	+"sum(case when type=21 then 1 else 0 end) as turn_left,"
	+"sum(case when type=22 then 1 else 0 end) as turn_right,"
	+"sum(case when type=14 then 1 else 0 end) as button "
	+"from event where type in (14,16,17,20,21,22) and username = ?1", nativeQuery = true)
	Map<Object,Object> getEventStatByUser(String username);

	@Query(value = "select * from event order by time desc", nativeQuery = true)
	List<EventModel> getAllEvents();

	@Query(value = "select * from event where username = ?1 order by time desc", nativeQuery = true)
	List<EventModel> getAllEvents(String username);
}