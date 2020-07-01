package com.cms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.dto.UserDto;
import com.cms.model.UpdatePassword;
import com.cms.serviceimpl.RegistrationServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RegistrationController {
	
	@Autowired
	RegistrationServiceImpl registrationServiceImpl;
	
	@PostMapping(value="/user/register/")
    public ResponseEntity<Map<String, String>> saveUser(@RequestBody UserDto user){
        return registrationServiceImpl.register(user);
    }

	 @PostMapping(value="/user/password/")
	    public ResponseEntity<Map<String, String>> updateUserPassword(@RequestBody UpdatePassword newpassword){
	        return registrationServiceImpl.updateUserPassword(newpassword.getOldPassword(),
	        		newpassword.getNewPassword(),newpassword.getConfirmPassword());
	    }

	}

