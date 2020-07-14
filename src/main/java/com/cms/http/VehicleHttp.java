package com.cms.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.cms.dto.DevicesGps;
import com.cms.dto.LocationDto;
import com.cms.dto.VehicleDetailDto;
import com.cms.dto.VehicleDto;
import com.cms.serviceimpl.StonkamServiceImpl;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;


@Service("VehicleHttp")
public class VehicleHttp {
	@Autowired
	private StonkamServiceImpl stonkamService;
	private static HttpTransport TRANSPORT;
	private static HttpRequestFactory REQ_FACTORY;
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private Gson gson;

	@Value("${stonkam.hostname}")
	private String stonkamHostname;

	public VehicleHttp() {
		this.gson = new Gson();
	}

	// get all vehicle information from stonkam API
	public List<VehicleDto> getAllVehicles(String stk_user) throws IOException {
		// get current stonkam session id from application context
		long sessionId = this.stonkamService.refreshAccessToken();

		// build API uri with query string
		UriComponentsBuilder builder = UriComponentsBuilder
			.fromHttpUrl(this.stonkamHostname + "/GetDeviceList/100")
			.queryParam("SessionId", sessionId)
			.queryParam("User", stk_user);
		
		HttpRequest req = reqFactory()
			.buildGetRequest(new GenericUrl(builder.toUriString()));
		HttpResponse res = req.execute();

		String responseBody = res.parseAsString();

		// deserialize json string to DeviceListing object.
		DeviceListing resultObj = this.gson.fromJson(responseBody, DeviceListing.class);
		return this.addLatestVehicleLocation(resultObj.deviceList);
	}

	// update each vehicle objects's location 
	private List<VehicleDto> addLatestVehicleLocation(List<VehicleDetailDto> deviceList)
			throws IOException {
		// get current stonkam session id from application context
		long sessionId = this.stonkamService.refreshAccessToken();

		// build API uri with query string
		UriComponentsBuilder builder = UriComponentsBuilder
			.fromHttpUrl(this.stonkamHostname + "/GetDevicesGps/100")
			.queryParam("SessionId", sessionId)
			.queryParam("IsNeedPush", 0)
			.queryParam("CmsClientId", 0);
		
		// Creating request Body
		List<Map<String, Long>> reqDeviceList = deviceList.stream()
			.collect(Collectors.mapping(dto -> {
				Map<String, Long> ids =  new HashMap<String, Long>();
				ids.put("DeviceId", dto.getDeviceId());
				return ids;
		}, Collectors.toList()));
		
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("UserName", "admin");
		requestBody.put("DeviceList", reqDeviceList);
		HttpContent content = new JsonHttpContent(JSON_FACTORY, requestBody);

		HttpRequest req = reqFactory()
			.buildPostRequest(new GenericUrl(builder.toUriString()), content);
		HttpResponse res = req.execute();

		String responseBody = res.parseAsString();

		DevicesGps resultObj = this.gson.fromJson(responseBody, DevicesGps.class);
		List<VehicleDetailDto> devices = resultObj.getDevicesGps();

		List<VehicleDto> vehicles = new ArrayList<>();
		for (VehicleDetailDto device: devices) {
			VehicleDetailDto vehicleDetailDto = deviceList.stream()
				.filter(x ->  x.getDeviceId() == device.getDeviceId())
				.findFirst()
				.get();

			if (vehicleDetailDto != null) {
				VehicleDto vehicle = new VehicleDto();
			
				vehicle.setId(vehicleDetailDto.getDeviceId());
				vehicle.setActive(vehicleDetailDto.isIsActive());
				vehicle.setOnline(vehicleDetailDto.isIsOnline());
	
				LocationDto location = new LocationDto();
				location.setLat(device.getLatitude());
				location.setLng(device.getLongitude());
	
				vehicle.setLocation(location);
				vehicle.setDetail(vehicleDetailDto);
				vehicles.add(vehicle);
			}
		}

		return vehicles;
	}
	
	private static HttpTransport transport() {
		if (null == TRANSPORT) {
			TRANSPORT = new NetHttpTransport();
		}
		return TRANSPORT;
	}

	private static HttpRequestFactory reqFactory() {
		if (null == REQ_FACTORY) {
			REQ_FACTORY = transport().createRequestFactory();
		}
		return REQ_FACTORY;
	}

	private class DeviceListing {

		@SerializedName("DeviceList")
		private List<VehicleDetailDto> deviceList;
		
		@SerializedName("Number")
		private int number;

		public int getNumber() {
			return this.number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public List<VehicleDetailDto> getDeviceList() {
			return this.deviceList;
		}

		public void setDeviceList(List<VehicleDetailDto> deviceList) {
			this.deviceList = deviceList;
		}
	}
}
