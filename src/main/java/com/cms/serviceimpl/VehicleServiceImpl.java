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
			vehicleList = this.vehicleHttp.getAllVehicles(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vehicleList;
	}

	// get vehicle information by id
	public VehicleDto getVehicleById(long vehicleId, String user) {
		// get all vehicle from stonkam API
		final List<VehicleDto> vehicleList = this.getAllVehiclesByUser(user);
		if (vehicleList != null 
			&& vehicleList.size() > 0
			&& vehicleList.stream().anyMatch(x -> x.getId() == vehicleId)) {
			// return vehicle information is contain device id match with vehicleId
			return vehicleList.stream()
			.filter(x -> x.getId() == vehicleId)
			.findFirst()
			.get();
		}

		return null;
		
	}
}
