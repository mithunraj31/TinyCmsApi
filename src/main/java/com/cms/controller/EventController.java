package com.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.constants.Constants;
import com.cms.dto.EventDto;
import com.cms.dto.EventStatDto;
import com.cms.serviceimpl.EventServiceImpl;
import com.cms.serviceimpl.UtilityServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/event")
public class EventController {

	@Autowired
	EventServiceImpl eventServiceImpl;

	@Autowired
	private UtilityServiceImpl utilityService;

	@GetMapping("")
	public List<EventDto> getAllEvent(@RequestParam(name = "stk_user", required = false) String stkUser,
			HttpServletRequest request) {
		// Get Stk_User id from token
		String token = request.getHeader(Constants.HEADER_STRING);
		String stk_user = this.utilityService.getStkUserFromToken(token);

		// User is admin
		if (stk_user.equals("admin")) {
			// have specific company event parameter set it to stk_user filter
			if (stkUser != null && !stkUser.isEmpty()) {
				stk_user = stkUser;
			} else {
				// no specifc company event get all events
				return this.eventServiceImpl.getAllEvent();
			}
		}

		// get events by stk_user
		return this.eventServiceImpl.getAllEvent(stk_user);
	}

	@GetMapping("/{eventId}")
	public ResponseEntity<?> getEventById(@PathVariable(value = "eventId") @Valid String eventId,
			HttpServletRequest request) {

		// Get Stk_User id from token
		String token = request.getHeader(Constants.HEADER_STRING);
		String stk_user = this.utilityService.getStkUserFromToken(token);

		// get event by event ID.
		EventDto event = this.eventServiceImpl.getByEventId(eventId);

		// reponse 404
		// if not found event
		// if the request user is not admin and not own the event
		if (event == null || (!"admin".equals(stk_user) && !stk_user.equals(event.getUsername()))) {
			return new ResponseEntity<EventDto>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<EventDto>(event, HttpStatus.OK);
	}

	@GetMapping("/stat")
	public ResponseEntity<?> getEventStat(HttpServletRequest request) {
		// Get Stk_User id from token
		String token = request.getHeader(Constants.HEADER_STRING);
		String stk_user = this.utilityService.getStkUserFromToken(token);

		// if user is admin, set empty value to the variable
		// because will search from all users
		stk_user = stk_user.toLowerCase().equals("admin") ? "" : stk_user;

		EventStatDto eventStat = this.eventServiceImpl.getEventStat(stk_user);

		return new ResponseEntity<EventStatDto>(eventStat, HttpStatus.OK);
	}

	@GetMapping("/stat/{username}")
	@PreAuthorize(Constants.ADMIN)
	public ResponseEntity<?> getEventStatByUser(@PathVariable(value = "username") @Valid String username,
			HttpServletRequest request) {

		EventStatDto eventStat = this.eventServiceImpl.getEventStat(username);

		return new ResponseEntity<EventStatDto>(eventStat, HttpStatus.OK);
	}

}
