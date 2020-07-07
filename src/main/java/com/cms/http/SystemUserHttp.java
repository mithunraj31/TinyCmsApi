package com.cms.http;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.constants.Constants;
import com.cms.dto.SystemUserDto;
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

@Service("SystemUserHttp")
public class SystemUserHttp {
	@Autowired()
	private Map<String, String> memoryMap;
	
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static HttpTransport TRANSPORT;
	private static HttpRequestFactory REQ_FACTORY;

	
	public void login() throws IOException {
		 
		Gson gson = new Gson();
		GenericUrl url = new GenericUrl(Constants.STK_URL+"/RecordDataAuthentication/100");
		Map<String, Object> data = new LinkedHashMap<>();
		
		    data.put("Username", Constants.STK_USERNAME);
		    data.put("Password", Constants.STK_PASSWORD);
		    data.put("Version", Constants.STK_VERSION);
		    data.put("AuthType", Constants.STK_AUTHTYPE);
		    
		    
		    HttpContent content = new JsonHttpContent(JSON_FACTORY, data);
		    HttpRequest req = reqFactory().buildPostRequest(url, content);
		    HttpResponse res = req.execute();
		    String responseBody = res.parseAsString();
		    
		    // parse data into SystemUser
		    try {
		    	SystemUserDto systemUser = gson.fromJson(responseBody, SystemUserDto.class);
		    	memoryMap.put("sessionId", Integer.toString(systemUser.getSessionId()));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}

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
