package com.cms.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cms.dto.CustomerDto;
import com.cms.dto.CustomerStatusDto;
import com.cms.dto.VehicleDto;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

@Service("CustomerHttp")
public class CustomerHttp {
	@Autowired()
	private Map<String, String> memoryMap;
	@Autowired()
	VehicleHttp vehicleHttp;

	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static HttpTransport TRANSPORT;
	private static HttpRequestFactory REQ_FACTORY;

	@Value("${kitting.url}")
	private String kittingHostname;

	@SuppressWarnings("unchecked")
	public List<CustomerDto> getAllCustomers() throws IOException {
		Gson gson = new Gson();
		GenericUrl url = new GenericUrl(this.kittingHostname + "/latest");

		HttpRequest req = reqFactory().buildGetRequest(url);
		HttpResponse res = req.execute();
		String responseBody = res.parseAsString();
		Type type = new TypeToken<Map<String, Object>>() {}.getType();
		// parsing to Map of <string,Object>
		Map<String, Object> resultObj = gson.fromJson(responseBody, type);
		List<Map<String, Object>> customerArray = (List<Map<String, Object>>) resultObj.get("customers");

		List<CustomerDto> customers = new ArrayList<CustomerDto>();
		for (int i = 0; i < customerArray.size(); i++) {
			Map<String, Object> j = customerArray.get(i);
			CustomerDto c = new CustomerDto();
			c.setId((int) Double.parseDouble(j.get("id").toString()));
			c.setDescription(j.get("description").toString());
			c.setSid(j.get("sid").toString());
			if (j.containsKey("stk_user") && j.get("stk_user") != null) {
				c.setStk_user(j.get("stk_user").toString());
			} else {

				c.setStk_user("");
			}
			customers.add(c);
		}

		return customers;
	}

	public List<CustomerStatusDto> getCustomersStatusByid(List<CustomerDto> stk_users) {
		List<CustomerStatusDto> customersStatus = new ArrayList<CustomerStatusDto>();

		for (int i = 0; i < stk_users.size(); i++) {
			List<VehicleDto> vehicles = new ArrayList<VehicleDto>();
			CustomerStatusDto status = new CustomerStatusDto();
			int total = 0;
			int online = 0;

			status.setId(stk_users.get(i).getId());
			status.setName(stk_users.get(i).getDescription());
			status.setStk_user(stk_users.get(i).getStk_user());
			try {
				vehicles = this.vehicleHttp.getAllVehicles(stk_users.get(i).getStk_user());
				total = vehicles.size();

				for (int j = 0; i < vehicles.size(); i++) {
					if (vehicles.get(i).isOnline()) {
						online++;
					}
				}

			} catch (IOException e) {

			}

			status.setTotalVehicle(total);
			status.setRunningVehicle(online);

			customersStatus.add(status);
		}

		return customersStatus;
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