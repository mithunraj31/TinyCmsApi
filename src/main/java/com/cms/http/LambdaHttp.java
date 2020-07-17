package com.cms.http;


import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.jackson2.JacksonFactory;

@Service("LambdaHttp")
public class LambdaHttp {
    private static HttpTransport TRANSPORT;
    private static HttpRequestFactory REQ_FACTORY;
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    @Value("${lambda.hostname}")
    private String lambdaHostname;

    public String startVideoConversion(Map<String, Object> requestBody) throws IOException {
        // build API uri
		UriComponentsBuilder builder = UriComponentsBuilder
        .fromHttpUrl(this.lambdaHostname);

        HttpContent content = new JsonHttpContent(JSON_FACTORY, requestBody);

        HttpRequest req = reqFactory()
			.buildPostRequest(new GenericUrl(builder.toUriString()), content);
        HttpResponse res = req.execute();
        String responseBody = res.parseAsString();
        return responseBody;
    }


    private static HttpTransport transport() {
		if (null == TRANSPORT) {
			TRANSPORT = new NetHttpTransport();
		}
		return TRANSPORT;
	}

	private static HttpRequestFactory reqFactory() {
		if (null == REQ_FACTORY) {
			REQ_FACTORY = transport().createRequestFactory();
		}
		return REQ_FACTORY;
	}
    
    
}