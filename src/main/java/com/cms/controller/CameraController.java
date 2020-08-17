package com.cms.controller;

import static com.cms.constants.Constants.HEADER_STRING;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.dto.CameraDto;
import com.cms.dto.VehicleDto;
import com.cms.model.CameraModel;
import com.cms.serviceimpl.CameraServiceImpl;
import com.cms.serviceimpl.UtilityServiceImpl;
import com.cms.serviceimpl.VehicleServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/vehicle/{deviceId}/camera")
public class CameraController {

	@Autowired
	private CameraServiceImpl cameraServiceImpl;

	@Autowired
	private UtilityServiceImpl utilityService;

	@Autowired
	private VehicleServiceImpl vehicleService;

	@PostMapping("")
	public ResponseEntity<?> saveCamera(@Valid @RequestBody CameraDto newCamera, HttpServletRequest request) {

		Map<String, Object> response = new LinkedHashMap<>();

		if (!this.checkUserHasDevice(request, newCamera.getDeviceId())) {
			response.put("message", "Device not found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			cameraServiceImpl.saveCamera(newCamera);
			response.put("message", "Success");
			response.put("cameras", newCamera);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (Exception ex) {
			response.put("message", "Failed");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("")
	public ResponseEntity<?> getCamerasByDeviceId(@PathVariable(value = "deviceId") String deviceId,
			HttpServletRequest request) {
		Map<String, Object> response = new LinkedHashMap<>();

		if (!this.checkUserHasDevice(request, deviceId)) {
			response.put("message", "Device not found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			List<CameraModel> cameraList = cameraServiceImpl.getCamerasByDeviceId(deviceId);
			response.put("camera", cameraList);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception ex) {
			response.put("message", "Failed");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("")
	public ResponseEntity<?> updateCamera(@PathVariable(value = "deviceId") String deviceId,
			@Valid @RequestBody CameraDto camera,
			HttpServletRequest request) {
		Map<String, Object> response = new LinkedHashMap<>();

		if (!this.checkUserHasDevice(request, deviceId)) {
			response.put("message", "Device not found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			cameraServiceImpl.updateCamera(camera);
			response.put("message", "Updated");
			response.put("camera", camera);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception ex) {
			response.put("message", "Failed");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{cameraId}")
	public ResponseEntity<?> deleteCamera(@PathVariable(value = "deviceId") String deviceId,
			@PathVariable(value = "cameraId") int cameraId,
			HttpServletRequest request) {
		Map<String, Object> response = new LinkedHashMap<>();

		if (!this.checkUserHasDevice(request, deviceId)) {
			response.put("message", "Device not found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			cameraServiceImpl.deleteCamera(deviceId, cameraId);
			response.put("message", "Deleted");
			response.put("cameras", cameraId);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception ex) {
			response.put("message", "Failed");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
	}

	private boolean checkUserHasDevice(HttpServletRequest request, String deviceId) {
		long vehicleId = 0;
		try {
			vehicleId = Long.parseLong(deviceId);
		} catch (Exception ex) {
			return false;
		}

		// get stk_user from JWT in HTTP request headers
		String token = request.getHeader(HEADER_STRING);
		String stk_user = this.utilityService.getStkUserFromToken(token);

		final VehicleDto vehicle = this.vehicleService.getVehicleById(vehicleId, stk_user);

		return vehicle != null;
	}

}
