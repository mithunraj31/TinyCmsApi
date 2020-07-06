package com.cms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.dto.UserDto;
import com.cms.model.UpdatePasswordModel;
import com.cms.serviceimpl.RegistrationServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RegistrationController {
	
	@Autowired
	RegistrationServiceImpl registrationServiceImpl;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value="/user/register/")
    public ResponseEntity<Map<String, String>> saveUser(@RequestBody UserDto user){
        return registrationServiceImpl.register(user);
    }

	 @PostMapping(value="/user/password/")
	    public ResponseEntity<Map<String, String>> updateUserPassword(@RequestBody UpdatePasswordModel updatePasswordModel){
	        return registrationServiceImpl.updateUserPassword(updatePasswordModel.getOldPassword(),
	        		updatePasswordModel.getNewPassword(),updatePasswordModel.getConfirmPassword());
	    }

	}

