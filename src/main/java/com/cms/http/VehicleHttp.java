package com.cms.http;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.constants.Constants;
import com.cms.dto.LocationDto;
import com.cms.dto.VehicleDto;
import com.cms.serviceimpl.StonkamServiceImpl;
import com.cms.serviceimpl.VehicleServiceImpl;
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
import com.google.gson.reflect.TypeToken;

@Service("VehicleHttp")
public class VehicleHttp {
	@Autowired
	private StonkamServiceImpl stonkamService;
	private static HttpTransport TRANSPORT;
	private static HttpRequestFactory REQ_FACTORY;
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	public List<VehicleDto> getAllVehicles(String stk_user) throws IOException {
	Gson gson = new Gson();
	long sessionId = this.stonkamService.refreshAccessToken();
	GenericUrl url = new GenericUrl(Constants.STK_URL+"/GetDeviceList/100?SessionId="+sessionId+"&User="+stk_user);
	
    HttpRequest req = reqFactory().buildGetRequest(url);
    HttpResponse res = req.execute();
    
    String responseBody = res.parseAsString();
    Type type = new TypeToken<Map<String, Object>>(){}.getType();
    
    // Getting device list as a string array
    Map<String, Object> resultObj= gson.fromJson(responseBody, type);
    List<Map<String,Object>> deviceArray = (List<Map<String,Object>>) resultObj.get("DeviceList");
    List<Long> deviceList= new ArrayList<>(); 
    for(int i =0; i<deviceArray.size();i++) {
    	Map<String,Object> j = deviceArray.get(i);
    	deviceList.add((Long) Long.parseLong(j.get("PlateNumber").toString()));   
    	}
    
    
    return this.getDevicesByIds(deviceList,deviceArray);
	}
	
	
	private List<VehicleDto>getDevicesByIds(List<Long> deviceList, List<Map<String, Object>> deviceObjects) throws IOException {
		Gson gson = new Gson();
		long sessionId = this.stonkamService.refreshAccessToken();
		GenericUrl url = new GenericUrl(Constants.STK_URL+"/GetDevicesGps/100?CmsClientId=0&IsNeedPush=0&SessionId="+sessionId);
		
		// Creating request Body
		List<Map<String, Long>> reqDeviceList = new ArrayList<Map<String,Long>>();
		for(int i=0; i<deviceList.size();i++) {
			Map<String,Long> a = new HashMap<String, Long>();
			a.put("DeviceId", deviceList.get(i));
			reqDeviceList.add(a);
		}
		Map<String, Object> requestBody = new HashMap<String, Object>();
		requestBody.put("UserName","admin");
		requestBody.put("DeviceList",reqDeviceList);
		HttpContent content = new JsonHttpContent(JSON_FACTORY, requestBody);
	    HttpRequest req = reqFactory().buildPostRequest(url,content);
	    HttpResponse res = req.execute();
	    
	    String responseBody = res.parseAsString();
	    
	    Type type = new TypeToken<Map<String, Object>>(){}.getType();
	    //parsing to Map of <string,Object>
	    Map<String, Object> resultObj= gson.fromJson(responseBody, type);
	    List<Map<String,Object>> deviceArray = (List<Map<String,Object>>) resultObj.get("DevicesGps");
	    
	    List<VehicleDto> vehicles = new ArrayList<>();
	    for(int i=0;i<deviceArray.size(); i++) {
	    	VehicleDto vehicle = new VehicleDto();
	    	vehicle.setId(deviceList.get(i));
	    	vehicle.setActive((Boolean) deviceObjects.get(i).get("IsActive"));
	    	vehicle.setOnline((Boolean) deviceObjects.get(i).get("IsOnline"));
	    	LocationDto location = new LocationDto();
	    	location.setLat(deviceArray.get(i).get("Latitude").toString());
	    	location.setLng(deviceArray.get(i).get("Longitude").toString());
	    	vehicle.setLocation(location);
	    	vehicles.add(vehicle);
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
}
