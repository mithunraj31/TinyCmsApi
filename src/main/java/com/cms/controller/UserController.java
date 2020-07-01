package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")

public class  UserController{
	 @Bean
	 public RestTemplate restTemplate() {
	     return new RestTemplate();
	 }
	
	@Autowired
	private RestTemplate restTemplate;
	

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String createEmployee() {
		
		return restTemplate.getForObject("https://mbel.tokyo/fs/kitting/latest",String.class );
	    
	}
	
	

//	private static void createEmployee()
//	{
//	    final String uri = "http://54.65.187.82:6060/RecordDataAuthentication/100";
//	 
//	    AuthModel newAuth = new AuthModel();
//	    newAuth.setUserName("admin");
//	 newAuth.setAuthType(1);
//	 newAuth.setPassword("U2iE3Haf");
//	 newAuth.setVersion("1.6.2.3");
//	    
//	    System.out.println(restTemplate.postForObject( uri, newAuth, AuthModel.class));
//	    AuthModel result = restTemplate.postForObject( uri, newAuth, AuthModel.class);
//	 
//	    System.out.println(result);
//	}
}

//class MessageConverter {
//	List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
//	//Add the Jackson Message converter
//	MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//
//	// Note: here we are making this converter to process any kind of response, 
//	// not only application/*json, which is the default behaviour
//	converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));        
//	messageConverters.add(converter);  
//	restTemplate.setMessageConverters(messageConverters); 
//	}

class AuthModel{
	
	private String userName;
	
	private String password;
	
	private String version;
	
	private int authType;


	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getVersion() {
		return version;
	}

	public int getAuthType() {
		return authType;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setAuthType(int authType) {
		this.authType = authType;
	}
	
}

 class ResultModel{
	
private String userName;
	
	private String result;
	
	private String reason;
	
	private int userType;
	
	private int sessionId;

	public String getUserName() {
		return userName;
	}

	public String getResult() {
		return result;
	}

	public String getReason() {
		return reason;
	}

	public int getUserType() {
		return userType;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	
}
 
