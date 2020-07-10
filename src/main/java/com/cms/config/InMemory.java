package com.cms.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InMemory {

	   @Bean 
	   public Map<String, String> memoryMap(){
	      java.util.Map<String, String> map = new java.util.HashMap<String, String>();
	      map.put("sessionId", "0");
	      return map;      
	   }

	}
