package com.cms.serviceimpl;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cms.dao.UserDao;
import com.cms.model.UserEntityModel;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	
	

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntityModel user = userDao.findByEmail(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
	}

	private Set<SimpleGrantedAuthority> getAuthority(UserEntityModel user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			//authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		});
		return authorities;
		//return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<UserEntityModel> findAll() {
		return userDao.findAll();
	}



	
	public UserEntityModel findOne(String email) {
		return userDao.findByEmail(email);
	}


	public Optional<UserEntityModel> findById(int userId) {
		return userDao.findById(userId);
	}

	public UserEntityModel getupdateUserById(int userId, @Valid UserEntityModel userEntity) {
		UserEntityModel user = userDao.findById(userId).orElse(null);
		if(Objects.nonNull(user)) {
		user.setEmail(userEntity.getEmail());
		user.setLastName(userEntity.getLastName());
		user.setFirstName(userEntity.getFirstName());
		user.setRoleId(userEntity.getRoleId());
		user.setUserId(userId);
		return userDao.save(user);
		}
		return user;
	}

	public ResponseEntity<Map<String, String>> deleteUserById(int userId) {
		userDao.deleteById(userId);
		 Map<String, String> response = new HashMap<>();
		 response.put("message", "user has been deleted");
		 response.put("userId", String.valueOf(userId));
		 return new ResponseEntity<Map<String,String>>(response, HttpStatus.OK);
	
	}


}
