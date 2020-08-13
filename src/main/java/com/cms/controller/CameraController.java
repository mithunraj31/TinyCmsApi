package com.cms.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.cms.model.CameraModel;
import com.cms.serviceimpl.CameraServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CameraController {
	
	@Autowired
	CameraServiceImpl cameraServiceImpl;
	
	@PostMapping("/camera")
	public ResponseEntity<?>  saveCamera(@Valid @RequestBody CameraDto newCamera) {
		Map<String, Object> data = new LinkedHashMap<>();
		 cameraServiceImpl.saveCamera(newCamera);
		data.put("message", "Success");
		data.put("cameras", newCamera);
		return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
	}
	
	@GetMapping("/camera/{deviceId}")
	public List<CameraModel>  getCamerasByDeviceId(@PathVariable (value="deviceId")String deviceId) {
		return cameraServiceImpl.getCamerasByDeviceId(deviceId);
	}
	
	@PutMapping("/camera")
	public ResponseEntity<?>  updateCamera(@Valid @RequestBody CameraDto newCamera) {
		Map<String, Object> data = new LinkedHashMap<>();
		 cameraServiceImpl.updateCamera(newCamera);
		data.put("message", "Updated");
		data.put("cameras", newCamera);
		return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
	}
	
	@DeleteMapping("/camera/{deviceId}/{cameraId}")
	public ResponseEntity<?>  deleteCamera(@PathVariable (value="deviceId")String deviceId,
			@PathVariable (value="cameraId")int cameraId) {
		Map<String, Object> data = new LinkedHashMap<>();
		 cameraServiceImpl.deleteCamera(deviceId,cameraId);
		data.put("message", "Deleted");
		data.put("cameras", cameraId);
		return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
	}

}
