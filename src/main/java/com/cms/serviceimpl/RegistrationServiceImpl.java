package com.cms.serviceimpl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.AlreadyBuiltException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cms.config.JwtAuthenticationFilter;
import com.cms.dao.UserDao;
import com.cms.dto.UserDto;
import com.cms.model.UserEntityModel;

@Service("RegistrationServiceImpl")
public class RegistrationServiceImpl {

	@Autowired
	private UserDao userDao;

	@Autowired
	JwtAuthenticationFilter jwt;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	public void register(UserDto userDto) throws Exception {
		if(verifyNewUser(userDto.getEmail())) {
			UserEntityModel userEntityModel = new UserEntityModel();
			userEntityModel.setFirstName(userDto.getFirstName());
			userEntityModel.setLastName(userDto.getLastName());
			userEntityModel.setPassword(bcryptEncoder.encode(userDto.getPassword()));
			userEntityModel.setEmail(userDto.getEmail());
			userEntityModel.setRoleId(userDto.getRole());
			userEntityModel.setStkUser(userDto.getStkUser());
			userDao.save(userEntityModel);
			userDao.addRoleToUser( userEntityModel.getUserId(),userDto.getRole());
			try {
				emailServiceImpl.sendEmail(userDto.getEmail(),userDto.getPassword(),userDto.getFirstName());
			}catch(MailAuthenticationException e) {
				throw new MailAuthenticationException("Email cant be sent to the registered user", e.getCause());
			}
		}else {
			throw new Exception("Email Already registered");
		}

	}

	private boolean verifyNewUser(String email) {
		try {
			UserEntityModel user =userDao.findByEmail(email);
			if(Objects.nonNull(user)) {
				return false;	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;

	}

	public void updateUserPassword(String oldPassword, String newPassword, String confirmPassword,
			String email,int userId) throws Exception {
		UserEntityModel newUser = jwt.getUserdetails();
		if(newPassword.equals(confirmPassword)&&!newPassword.equals(oldPassword)) {
			try {
				authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
						email,
						oldPassword
						));
				newUser.setUserId(userId);
				newUser.setPassword(bcryptEncoder.encode(newPassword));
				userDao.save(newUser);
			}catch (BadCredentialsException bad){
				throw new BadCredentialsException("Entered Old Password is wrong.");
			}
		}else if(newPassword.equals(oldPassword)){
			throw new AlreadyBuiltException("New Password cannot be same as Old Password.");
		}
		else {
			throw new Exception("New Password and Confirm Password doesnt match.");

		}

	}



}
