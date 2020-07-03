package com.cms.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.http.SystemUserHttp;

@RestController
public class HelloController {
	@Autowired()
	SystemUserHttp ch;
	@Autowired()
	private Map<String, String> memoryMap;
	@RequestMapping("/")
	public Map<String, Object> index() throws IOException {
		System.out.println(memoryMap);
		ch.login();
		
		System.out.println(memoryMap);
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("message", "Greetings from Tiny Cms API");
		return data;
	}

}