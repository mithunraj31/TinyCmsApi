package com.cms.serviceimpl;

import com.cms.config.TokenProvider;
import com.cms.constants.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;

@Service(value = "utilityService")
public class UtilityServiceImpl {
    @Autowired
    private TokenProvider tokenProvider;
    
    /**
	 * get stk_user value from JWY payload
	 * @param token JWT in HTTP request headers
	 * @return String value of stk_user.
	 */
	public String getStkUserFromToken(String token) {
		// remove text "Bearer " from header value
		token = token.replace(Constants.TOKEN_PREFIX, "");
		// get payload as Claims object
		Claims user = tokenProvider.getAllClaimsFromToken(token);
		// get value by object key
		return user.get("stk_user", String.class);
	}
}