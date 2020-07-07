package com.cms.serviceimpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.dto.VehicleDto;
import com.cms.http.VehicleHttp;

@Service("VehicleServiceImpl")
public class VehicleServiceImpl {
@Autowired
private VehicleHttp vehicleHttp;

public List<VehicleDto> getAllVehiclesByUser(String user) {
	List<VehicleDto> vehicleList = new ArrayList<>();
	try {
		vehicleList = vehicleHttp.getAllVehicles(user);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return vehicleList;
}
}
