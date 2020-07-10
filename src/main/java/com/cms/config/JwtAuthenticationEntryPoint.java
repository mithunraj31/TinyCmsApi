package com.cms.config;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        String path = request.getRequestURI();

        if (path.equals("/api/login")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UserName or Password is invalid");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are Unauthorized");
        }
    }
}