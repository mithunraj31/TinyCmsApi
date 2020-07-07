package com.cms.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import com.cms.HttpModel.Customer;
import com.cms.constants.Constants;
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
	
	
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static HttpTransport TRANSPORT;
	private static HttpRequestFactory REQ_FACTORY;
	
	
	@SuppressWarnings("unchecked")
	public List<Customer> getAllCustomers() throws IOException, JSONException {
		Gson gson = new Gson();
		GenericUrl url = new GenericUrl(Constants.KITTING_URL+"/latest");
		
	    HttpRequest req = reqFactory().buildGetRequest(url);
	    HttpResponse res = req.execute();
	    String responseBody = res.parseAsString();
	    Type type = new TypeToken<Map<String, Object>>(){}.getType();
	    //parsing to Map of <string,Object>
	    Map<String, Object> resultObj= gson.fromJson(responseBody, type);
	    List<Map<String,Object>> customerArray = (List<Map<String,Object>>) resultObj.get("customers");
	    
	    List<Customer> customers= new ArrayList<Customer>();
	    for(int i =0; i<customerArray.size();i++) {
	    	Map<String,Object> j = customerArray.get(i);
	    	Customer c = new Customer();
	    	c.id = (int) Double.parseDouble(j.get("id").toString());
	    	c.description = j.get("description").toString();
	    	c.sid = j.get("sid").toString();
	    	if(j.containsKey("stk_user")&&j.get("stk_user") != null) {
	    		c.stk_user = j.get("stk_user").toString();
	    	}else {
	    		
	    		c.stk_user = "";
	    	}
	    	customers.add(c);
	    }
	    
		return customers;
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