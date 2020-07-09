package com.cms.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static com.cms.constants.Constants.HEADER_STRING;
import static com.cms.constants.Constants.TOKEN_PREFIX;

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

import com.cms.config.TokenProvider;
import com.cms.dto.VehicleDto;
import com.cms.serviceimpl.VehicleServiceImpl;

import io.jsonwebtoken.Claims;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

	@Autowired
	private VehicleServiceImpl vehicleService;

	@Autowired
	private TokenProvider tokenProvider;

	@RequestMapping("")
	public ResponseEntity<?> getAllVehiclesByUser(@RequestParam(required = false) String customer,
			HttpServletRequest request, Authentication auth) {
		String email = auth.getName();
		// Get Stk_User id from token
		String token = request.getHeader(HEADER_STRING);
		token = token.replace(TOKEN_PREFIX, "");
		Claims user = tokenProvider.getAllClaimsFromToken(token);
		String stk_user = user.get("stk_user", String.class);

		// Handle query parameter customer
		if (customer != null && request.isUserInRole("ROLE_ADMIN") == false) {
			return new ResponseEntity<Error>(HttpStatus.FORBIDDEN);
		} else if (customer != null && request.isUserInRole("ROLE_ADMIN") == true) {
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

	// retrive vehicle information by vehicle id.
	@GetMapping("/{vehicleId}")
	public ResponseEntity<?> getVehicleById(@PathVariable("vehicleId") long vehicleId,
		HttpServletRequest request, Authentication auth) {
		// Get Stk_User id from token
		String token = request.getHeader(HEADER_STRING);
		token = token.replace(TOKEN_PREFIX, "");
		Claims user = tokenProvider.getAllClaimsFromToken(token);
		String stk_user = user.get("stk_user", String.class);
		Map<String, Object> response = new LinkedHashMap<>();

		if (vehicleId > 0) {
			final VehicleDto vehicleDto = 
				this.vehicleService.getVehicleById(vehicleId, stk_user);
			response.put("message", "Success");
			response.put("vehicle", vehicleDto);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		response.put("message", "Failed");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	}
}
