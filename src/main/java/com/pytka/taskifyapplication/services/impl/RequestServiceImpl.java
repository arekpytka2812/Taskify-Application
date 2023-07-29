package com.pytka.taskifyapplication.services.impl;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final WebClient webClient;

    public <RES> RES getRequest(Class<RES> responseType, String endpointURI){

        return this.webClient.get()
                .uri(endpointURI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + SpringMainApplication.AUTH_TOKEN)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <RES, REQ> RES postRequest(Class<RES> responseType, REQ request, String endpointURI){

        return this.webClient.post()
                .uri(endpointURI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + SpringMainApplication.AUTH_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <RES> RES deleteRequest(Class<RES> responseType, String endpointURI){

        return this.webClient.delete()
                .uri(endpointURI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + SpringMainApplication.AUTH_TOKEN)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

}
