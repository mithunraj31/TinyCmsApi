package com.cms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.model.EventModel;
import com.cms.serviceimpl.EventServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class EventController {
	
	@Autowired
	EventServiceImpl eventServiceImpl;
	
	
	@GetMapping("/event")
	public List<EventModel> getAllEvent() {
		return eventServiceImpl.getAllEvent();
		
	}
	
	@GetMapping("/event/{deviceId}")
	public List<EventModel> getEventById(@PathVariable (value="deviceId") @Valid int deviceId) {
		return eventServiceImpl.getEventById(deviceId);
		
	}
	
	
	

}
