package com.pytka.taskifyapplication.core.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.core.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public <RES> RES getRequest(Class<RES> responseType, String endpointURI){

        return this.webClient.get()
                .uri(endpointURI)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + SpringMainApplication.AUTH_TOKEN)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <RES> List<RES> getListRequest(Class<RES> responseType, String endpointURI) {
        JsonNode response
                = webClient.get()
                    .uri(endpointURI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + SpringMainApplication.AUTH_TOKEN)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

        CollectionType listType = objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, responseType);

        return objectMapper.convertValue(response, listType);
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
