package com.cms.serviceimpl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.dao.EventDao;
import com.cms.model.EventModel;

@Service("EventServiceImpl")
public class EventServiceImpl {
	
	@Autowired
	EventDao eventDao;
	

	public List<EventModel> getAllEvent() {
		return eventDao.findAll();
	}


	public List<EventModel> getEventById(@Valid int deviceId) {
		// TODO Auto-generated method stub
		return eventDao.findByDeviceId(deviceId);
	}
}
