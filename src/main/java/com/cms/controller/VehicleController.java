package com.cms.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static com.cms.constants.Constants.HEADER_STRING;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.dto.DailyOnlineDto;
import com.cms.dto.HourlyOnlineDto;
import com.cms.dto.VehicleDto;
import com.cms.serviceimpl.UtilityServiceImpl;
import com.cms.serviceimpl.VehicleServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

	@Autowired
	private VehicleServiceImpl vehicleService;

	@Autowired
	private UtilityServiceImpl utilityService;

	@RequestMapping("")
	public ResponseEntity<?> getAllVehiclesByUser(@RequestParam(required = false) String customer,
			HttpServletRequest request, Authentication auth) {
		String email = auth.getName();
		// Get Stk_User id from token
		String token = request.getHeader(HEADER_STRING);
		String stk_user = this.utilityService.getStkUserFromToken(token);

		if (request.isUserInRole("ROLE_ADMIN")
			&& customer != null 
			&& !customer.isEmpty()) {
				stk_user = customer;
		}

		List<VehicleDto> vehicles = this.vehicleService.getAllVehiclesByUser(stk_user);
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("email", email);
		response.put("stk_user", stk_user);
		response.put("total", vehicles.size());
		response.put("vehicles", vehicles);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	/**
	 * request GET /api/vehicle/online
	 */
	@GetMapping("/online")
	public ResponseEntity<?> getOnlineVehicle(HttpServletRequest request) {

		// get stk_user from JWT in HTTP request headers
		String token = request.getHeader(HEADER_STRING);
		String stk_user = this.utilityService.getStkUserFromToken(token);

		// if user is admin, set empty value to the variable
		// because will search from all users
		stk_user = stk_user.toLowerCase().equals("admin") ? "" : stk_user;
		long online = this.vehicleService.getOnlineVehicle(stk_user);
		long total = this.vehicleService.getTotalVehicleCount(stk_user);
		Map<String, Object> response = new LinkedHashMap<>();

		response.put("online", online);
		response.put("total", total);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	}

	/**
	 * request GET /api/vehicle/online?days={0 ~ N}
	 */
	@GetMapping("/online/summary")
	public ResponseEntity<?> getOnlineSummary(@RequestParam(required = false) int days,
		HttpServletRequest request) {
		
		// get stk_user from JWT in HTTP request headers
		String token = request.getHeader(HEADER_STRING);
		String stk_user = this.utilityService.getStkUserFromToken(token);

			// if user is admin, set empty value to the variable
		// because will search from all users
		stk_user = stk_user.toLowerCase().equals("admin") ? "" : stk_user;

		Map<String, Object> response = new LinkedHashMap<>();
		// if days is only one day will get hourly data
		if (days == 1) {
			List<HourlyOnlineDto> reports =  this.vehicleService.getHourlyOnlineDevice(stk_user);
			response.put("reports", reports);
		} else {
			List<DailyOnlineDto> reports =  this.vehicleService.getDailyOnlineDevice(days, stk_user);
			response.put("reports", reports);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); 
	}

	
	// retrive vehicle information by vehicle id.
	@GetMapping("/{vehicleId}")
	public ResponseEntity<?> getVehicleById(@PathVariable("vehicleId") long vehicleId,
		HttpServletRequest request, Authentication auth) {
		// get stk_user from JWT in HTTP request headers
		String token = request.getHeader(HEADER_STRING);
		String stk_user = this.utilityService.getStkUserFromToken(token);
		Map<String, Object> response = new LinkedHashMap<>();
		
		final VehicleDto vehicleDto = 
				this.vehicleService.getVehicleById(vehicleId, stk_user);
		if (vehicleDto != null) {
			response.put("message", "Success");
			response.put("vehicle", vehicleDto);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		response.put("message", "Failed");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
