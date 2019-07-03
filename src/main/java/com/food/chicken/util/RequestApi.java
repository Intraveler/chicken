package com.food.chicken.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class RequestApi {

    private final RestTemplate restTemplate;

    public RequestApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callApi(String requestUrl, String param, HttpMethod method, HttpEntity<String> header) throws RestClientException {
        return restTemplate.exchange(requestUrl + param, method, header, String.class).getBody();
    }
}
