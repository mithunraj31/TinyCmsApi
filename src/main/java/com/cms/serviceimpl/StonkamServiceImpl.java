package com.cms.serviceimpl;

import java.time.LocalDateTime;

import com.cms.constants.Constants;
import com.cms.dto.StonkamAccessTokenDto;
import com.cms.dto.StonkamAuthDto;
import com.cms.dto.StonkamAuthUserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("StonkamServiceImpl")
public class StonkamServiceImpl {

    /**
     * use for http request to external API 
     */
    private RestTemplate restService;
    
    /**
     * sigleton context for store latest stokam access token
     */
    @Autowired
    private StonkamAccessTokenDto stonkamAccessToken;

    /**
     * application.properties
     */
    @Value("${stonkam.hostname}")
    private String stonkamHostname;

    @Value("${stonkam.auth.admin.username}")
    private String stonkamAdminUserName;

    @Value("${stonkam.auth.admin.password}")
    private String stonkamAdminPassword;

    @Value("${stonkam.auth.admin.version}")
    private String stonkamAdminVersion;

    @Value("${stonkam.auth.admin.authtype}")
    private int stonkamAdminAuthType;
    /**
     * end application.properties
     */


    /**
     * define RestTemplate while the service initializing.
     */
    public StonkamServiceImpl() {
        this.restService = new RestTemplate();
    }

    /**
     * the method will check stonkam access token from context 
     * if token is 0 or expired will request to get new token 
     * from stonkam server and set expiry time in 10 minute from now
     * @return latest stonkam access token 
     */
    public long refreshAccessToken() {
        if (stonkamAccessToken.getAccessToken() == 0
                || LocalDateTime.now().isAfter(stonkamAccessToken.getExipreDateTime())) {
            
            final long newSessionId = this.requestAccessToken();
            stonkamAccessToken.setAccessToken(newSessionId);
            stonkamAccessToken.setExipreDateTime(LocalDateTime.now().plusMinutes(10));
        }

        return this.stonkamAccessToken.getAccessToken();
    }


    /**
     * request stonkam access token from stonkam server API
     * use attach request body from application.properties
     */
    private long requestAccessToken() {
        final String endpoint = stonkamHostname + Constants.STONKAM_AUTH_ENDPOINT;
        StonkamAuthUserDto stonkamAuthUserDto = new StonkamAuthUserDto(this.stonkamAdminUserName, this.stonkamAdminPassword, this.stonkamAdminVersion, this.stonkamAdminAuthType);
        HttpEntity<StonkamAuthUserDto> requestBody = new HttpEntity<StonkamAuthUserDto>(stonkamAuthUserDto);
        ResponseEntity<StonkamAuthDto> response = this.restService.postForEntity(endpoint, requestBody, StonkamAuthDto.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getSessionId();
        }

        return 0;
    }
}