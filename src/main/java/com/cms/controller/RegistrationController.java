package com.cms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.AlreadyBuiltException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.config.TokenProvider;
import com.cms.constants.Constants;
import com.cms.dto.UserDto;
import com.cms.model.UpdatePasswordModel;
import com.cms.serviceimpl.RegistrationServiceImpl;

import io.jsonwebtoken.Claims;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class RegistrationController {

	@Autowired
	RegistrationServiceImpl registrationServiceImpl;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@PreAuthorize(Constants.ADMIN)
	@PostMapping(value="/user/register")
    public ResponseEntity<Map<String, String>> saveUser(@RequestBody UserDto user){
		Map<String, String> response = new HashMap<>();
		try {
			registrationServiceImpl.register(user);
			response.put(Constants.MESSAGE, "Registration successful");
			 return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);
		}catch (MailAuthenticationException ex) {
			response.put(Constants.MESSAGE, "Registration successful");
			response.put("Email", user.getEmail());
			response.put("Warning", "Email cant be sent to the registered user");
			
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.ACCEPTED);
		} catch (Exception ex) {
			response.put(Constants.MESSAGE, "Email already registered");
			response.put("Email", user.getEmail());
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.ALREADY_REPORTED);
		}
	}
	
	@PostMapping(value = "/user/password")
	public ResponseEntity<Map<String, String>> updateUserPassword(
			@RequestBody UpdatePasswordModel updatePasswordModel,HttpServletRequest request, Authentication auth) {
		Map<String, String> response = new HashMap<>();
		String token = request.getHeader(Constants.HEADER_STRING);
		token = token.replace(Constants.TOKEN_PREFIX,"");
		Claims user = tokenProvider.getAllClaimsFromToken(token);
		int  userId = user.get("userId",Integer.class);
		String name = user.get("firstName", String.class);
		try {
		 registrationServiceImpl.updateUserPassword(updatePasswordModel.getOldPassword(),
				updatePasswordModel.getNewPassword(), updatePasswordModel.getConfirmPassword(),auth.getName(),userId);
		 response.put(Constants.MESSAGE, "New Password has updated");
			response.put(Constants.USER_NAME, name);
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);
		}catch(BadCredentialsException bad){
			response.put(Constants.MESSAGE, "Entered Old Password is wrong.");
			response.put(Constants.USER_NAME, name);
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.UNAUTHORIZED);
			
		}catch (AlreadyBuiltException already){
			response.put(Constants.MESSAGE, "New Password cannot be same as Old Password.");
			response.put(Constants.USER_NAME, name);
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.ALREADY_REPORTED);
		} catch (Exception e) {
			response.put(Constants.MESSAGE, "New Password and Confirm Password doesnt match.");
			response.put(Constants.USER_NAME,name);
			return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
