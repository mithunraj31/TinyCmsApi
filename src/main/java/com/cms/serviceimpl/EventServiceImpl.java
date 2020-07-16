package com.cms.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.dao.EventDao;
import com.cms.dto.EventDto;
import com.cms.model.EventModel;

@Service("EventServiceImpl")
public class EventServiceImpl {
	
	@Autowired
	EventDao eventDao;
	
	@Autowired
	private ModelMapper modelMapper;

	public List<EventDto> getAllEvent() {
		List<EventModel> events = eventDao.findAll();
		List<EventDto> dtoList = events.stream()
			.map(x -> modelMapper.map(x, EventDto.class))
			.collect(Collectors.toList());

		return dtoList;
	}

	public List<EventDto> getAllEvent(String stkUser) {
		if (stkUser == null || stkUser.isEmpty()) {
			return getAllEvent();
		}

		List<EventModel> events = eventDao.findAll();
		List<EventDto> dtoList = events.stream()
			.map(x -> modelMapper.map(x, EventDto.class))
			.collect(Collectors.toList());

		return dtoList;
	}

	public EventDto getByEventId(String eventId) {
		EventModel eventModel = eventDao.findByEventId(eventId);
		EventDto eventDto = modelMapper.map(eventModel, EventDto.class);
		return eventDto;
	}


}
