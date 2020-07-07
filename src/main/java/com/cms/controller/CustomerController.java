package com.cms.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.dto.CustomerDto;
import com.cms.serviceimpl.CustomerServiceImpl;



@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	@Autowired
	private CustomerServiceImpl customerService;
	
	@RequestMapping("")
	public ResponseEntity<?>  getCustomers() {
		Map<String, Object> data = new LinkedHashMap<>();
		List<CustomerDto> customers = customerService.getAllCustomers();
		data.put("message", "Success");
		data.put("customers", customers);
		return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
	}
}
