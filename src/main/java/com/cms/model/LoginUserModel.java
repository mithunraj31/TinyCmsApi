package com.cms.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LoginUserModel {

    @NotEmpty(message = "emailRequired: Email is required")
    @Email(message = "isNotEmail: Email should be the real one!")
    @Size(min = 4, message = "emailCharacterError: Email should contains at least 4 characters")
    private String email;

    @NotEmpty(message = "passwordRequired: Password is required")
    @Size(min = 8, message = "passwordCharacterError: Password should contains at least 8 characters")
    private String password;


    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
