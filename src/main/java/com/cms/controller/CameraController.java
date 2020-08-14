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
import org.springframework.security.core.Authentication;
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
import com.cms.model.CameraModel;
import com.cms.serviceimpl.CameraServiceImpl;
import com.cms.serviceimpl.UtilityServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/vehicle/{deviceId}/camera")
public class CameraController {
	
	@Autowired
	CameraServiceImpl cameraServiceImpl;
	
	@Autowired
	private UtilityServiceImpl utilityService;
	
	@PostMapping("")
	public ResponseEntity<?>  saveCamera(@Valid @RequestBody CameraDto newCamera) {
		Map<String, Object> data = new LinkedHashMap<>();
		try {
		 cameraServiceImpl.saveCamera(newCamera);
		data.put("message", "Success");
		data.put("cameras", newCamera);
		return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}catch (Exception ex) {
		data.put("message", "Failed");
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?>  getCamerasByDeviceId(@PathVariable (value="deviceId")String deviceId,
			HttpServletRequest request, Authentication auth) {
		Map<String, Object> data = new LinkedHashMap<>();
		
		// Get Stk_User id from token
				String token = request.getHeader(HEADER_STRING);
				String stk_user = this.utilityService.getStkUserFromToken(token);
				try {
		List<CameraModel> cameraList= cameraServiceImpl.getCamerasByDeviceId(deviceId);
		data.put("camera", cameraList);
		return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}catch(Exception ex) {
		data.put("message", "Failed");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<?>  updateCamera(@PathVariable (value="deviceId")String deviceId,
			@Valid @RequestBody CameraDto newCamera) {
		Map<String, Object> data = new LinkedHashMap<>();
		try {
		 cameraServiceImpl.updateCamera(newCamera);
		data.put("message", "Updated");
		data.put("camera", newCamera);
		return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}catch(Exception ex){
			data.put("message", "Failed");
			return new ResponseEntity<Map<String,Object>>(data, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{cameraId}")
	public ResponseEntity<?>  deleteCamera(@PathVariable (value="deviceId")String deviceId,
			@PathVariable (value="cameraId")int cameraId) {
		Map<String, Object> data = new LinkedHashMap<>();
		try {
		 cameraServiceImpl.deleteCamera(deviceId,cameraId);
		data.put("message", "Deleted");
		data.put("cameras", cameraId);
		return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
		}catch(Exception ex) {
			data.put("message", "Failed");
			return new ResponseEntity<Map<String,Object>>(data, HttpStatus.NOT_FOUND);
		}
	}

}
