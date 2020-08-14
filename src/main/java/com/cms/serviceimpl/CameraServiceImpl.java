package com.cms.serviceimpl;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.dao.CameraDao;
import com.cms.dto.CameraDto;
import com.cms.model.CameraModel;


@Service("CameraServiceImpl")
public class CameraServiceImpl {
	

	@Autowired
	private CameraDao cameraDao;

	public void saveCamera(@Valid CameraDto newCamera) throws Exception {
		CameraModel cameraModel = new CameraModel();
		cameraModel.setCh(newCamera.getCh());
		cameraModel.setDeviceId(newCamera.getDeviceId());
		cameraModel.setRotation(newCamera.getRotation());
		try {
		cameraDao.save(cameraModel);
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
			
		}
	}

	public void updateCamera(@Valid CameraDto newCamera) throws Exception {
		CameraModel cameraModel = cameraDao.findById(newCamera.getId()).orElse(null);
		if(Objects.nonNull(cameraModel)) {
			cameraModel.setCh(newCamera.getCh());
			cameraModel.setDeviceId(newCamera.getDeviceId());
			cameraModel.setId(newCamera.getId());
			cameraModel.setRotation(newCamera.getRotation());
			try {
			cameraDao.save(cameraModel);
			}catch (Exception ex) {
				throw new Exception(ex.getMessage());
			}
		}
			
		
	}

	public void deleteCamera(String deviceId, int cameraId) throws Exception {
		try {
		cameraDao.deleteCamera(deviceId,cameraId);
		}catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
	}

	public List<CameraModel> getCamerasByDeviceId(String deviceId) throws Exception {
		try {
		return cameraDao.getCamerasByDeviceId(deviceId);
		}catch(Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}


}
