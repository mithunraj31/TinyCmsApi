package com.cms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="app")
public class AppProperties {

    private String stkUrl = "http://54.65.187.82:6060";

	public String getStkUrl() {
		return stkUrl;
	}

	public void setStkUrl(final String stkUrl) {
		this.stkUrl = stkUrl;
	} 

}