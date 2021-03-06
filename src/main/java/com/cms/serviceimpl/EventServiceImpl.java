package com.cms.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cms.dao.CameraDao;
import com.cms.dao.EventDao;
import com.cms.dao.VideoConvertedDao;
import com.cms.dao.VideoDao;
import com.cms.dto.EventDto;
import com.cms.dto.EventStatDto;
import com.cms.dto.VideoDto;
import com.cms.model.CameraModel;
import com.cms.model.EventModel;
import com.cms.model.EventStatModel;
import com.cms.model.VideoConvertedModel;
import com.cms.model.VideoModel;

@Service("EventServiceImpl")
public class EventServiceImpl {

	private VideoDao videoDao;

	private EventDao eventDao;

	private CameraDao cameraDao;

	private ModelMapper modelMapper;

	private VideoConvertedDao videoConvertedDao;

	@Value("${vt.url.prefix}")
	private String videoUrlPrefix;

	@Autowired
	public EventServiceImpl(ModelMapper modelMapper, EventDao eventDao, VideoDao videoDao, CameraDao cameraDao,
			VideoConvertedDao videoConvertedDao) {

		// inject services in the constructor
		this.modelMapper = modelMapper;
		this.eventDao = eventDao;
		this.cameraDao = cameraDao;
		this.videoDao = videoDao;
		this.videoConvertedDao = videoConvertedDao;

		// config model mapping from DAO to DTO
		this.modelMapper.typeMap(EventModel.class, EventDto.class)
				.addMapping(src -> src.getVideoConverted().getUrl(), EventDto::setVideoUrl)
				.setPropertyCondition(org.modelmapper.Conditions.isNotNull());

		this.modelMapper.typeMap(EventStatModel.class, EventStatDto.class)
				.addMapping(src -> src.getTurn_left(), EventStatDto::setTurnLeft)
				.addMapping(src -> src.getTurn_right(), EventStatDto::setTurnRight);
	}

	/**
	 * get all events and map to DTO object then set video stutus to each objects
	 */
	public List<EventDto> getAllEvent() {
		// get all event
		List<EventModel> events = eventDao.getAllEvents();
		List<EventDto> dtoList = events.stream().map(x -> modelMapper.map(x, EventDto.class))
				.collect(Collectors.toList());

		return this.setVideoToList(dtoList);
	}

	/**
	 * get events by stk_user if stkUser no value get all events then map to DTO
	 * object and set video// Get Stk_User id from token String token =
	 * request.getHeader(Constants.HEADER_STRING); String stk_user =
	 * this.utilityService.getStkUserFromToken(token); stutus to each objects
	 */
	public List<EventDto> getAllEvent(String stkUser) {
		if (stkUser == null || stkUser.isEmpty()) {
			return getAllEvent();
		}

		List<EventModel> events = this.eventDao.getAllEvents(stkUser);
		List<EventDto> dtoList = events.stream().map(x -> this.modelMapper.map(x, EventDto.class))
				.collect(Collectors.toList());

		return this.setVideoToList(dtoList);
	}

	/**
	 * get event by event id. then set video stutus to each objects.
	 */
	public EventDto getByEventId(String eventId) {
		EventModel eventModel = this.eventDao.findByEventId(eventId);

		if (eventModel != null) {
			EventDto eventDto = this.modelMapper.map(eventModel, EventDto.class);
			List<EventDto> dtos = new ArrayList<>();
			dtos.add(eventDto);

			dtos = this.setVideoToList(dtos);
			return dtos.get(0);
		}

		return null;
	}

	/**
	 * get device ids from EventDto listings and use it to find camera and video has
	 * contain ids set video and camera data to new DTO object then assign to new
	 * listings.
	 * 
	 * @param dtos EventDto listings receive from get event methods.
	 * @return mapped EventDto listings should contains vedio object
	 */
	private List<EventDto> setVideoToList(List<EventDto> dtos) {
		final List<String> deviceIds = dtos.stream().map(x -> x.getDeviceId()).collect(Collectors.toList());

		List<CameraModel> cameras = this.cameraDao.findCamerasByDeviceIds(deviceIds);

		List<VideoModel> videos = this.videoDao.findVideosByDeviceIds(deviceIds);

		List<EventDto> newDtos = new ArrayList<>();

		List<VideoConvertedModel> convertedVideos = videoConvertedDao.findAll();

		for (EventDto dto : dtos) {
			VideoDto videoDto = new VideoDto();
			if (videos.stream().anyMatch(x -> x.getEventId().equals(dto.getEventId()))) {

				long videoCount = videos.stream().filter(x -> x.getEventId().equals(dto.getEventId())).count();
				videoDto.setNoOfVideo(videoCount);
			}

			if (cameras.stream().anyMatch(x -> x.getDeviceId().equals(dto.getDeviceId()))) {
				long cameraCount = cameras.stream().filter(x -> x.getDeviceId().equals(dto.getDeviceId())).count();
				videoDto.setNoOfCamera(cameraCount);
			}
			if (convertedVideos.stream().anyMatch(x -> x.getId().equals(dto.getEventId()))) {
				VideoConvertedModel videoConvertedModel = convertedVideos.stream().filter(x -> x.getId().equals(dto.getEventId())).findFirst().get();
				videoDto.setVideoUrl(this.videoUrlPrefix + videoConvertedModel.getUrl());
			}

			videoDto.setVideoId(dto.getVideoId());
			dto.setVideo(videoDto);
			newDtos.add(dto);
		}

		return newDtos;
	}

	public EventStatDto getEventStat(String user) {
		EventStatDto eventStat = new EventStatDto();
		Map<Object,Object> eventStatModel = new HashMap<>();

		if (user != null && !user.isEmpty()) {
			eventStatModel = this.eventDao.getEventStatByUser(user);
		} else {
			eventStatModel = this.eventDao.getEventStat();
		}

		eventStat = this.modelMapper.map(eventStatModel,EventStatDto.class);

		return eventStat;
	}
}
