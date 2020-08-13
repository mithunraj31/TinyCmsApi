package com.cms.serviceimpl;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.dao.CameraDao;
import com.cms.dto.CameraDto;
import com.cms.model.CameraModel;


@Service("CameraServiceImpl")
public class CameraServiceImpl {
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CameraDao cameraDao;

	public void saveCamera(@Valid CameraDto newCamera) {
		CameraModel cameraModel = new CameraModel();
		cameraModel.setCh(newCamera.getCh());
		cameraModel.setDeviceId(newCamera.getDeviceId());
		cameraModel.setRotation(newCamera.getRotation());
		cameraDao.save(cameraModel);
	}

	public void updateCamera( @Valid CameraDto newCamera) {
		CameraModel cameraModel = new CameraModel();
			cameraModel.setCh(newCamera.getCh());
			cameraModel.setDeviceId(newCamera.getDeviceId());
			cameraModel.setId(newCamera.getId());
			cameraModel.setRotation(newCamera.getRotation());
			cameraDao.save(cameraModel);
			
		
	}

	public void deleteCamera(String deviceId, int cameraId) {
		cameraDao.deleteCamera(deviceId,cameraId);
		
	}

	public List<CameraModel> getCamerasByDeviceId(String deviceId) {
		try {
		return cameraDao.getCamerasByDeviceId(deviceId);
		}catch(Exception ex) {
			return null;
		}
	}


}
