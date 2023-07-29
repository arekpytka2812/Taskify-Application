package com.pytka.taskifyapplication.auth.service.impl;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.auth.model.AuthRequest;
import com.pytka.taskifyapplication.auth.model.AuthResponse;
import com.pytka.taskifyapplication.auth.model.ChangePasswordRequest;
import com.pytka.taskifyapplication.auth.model.RegisterRequest;
import com.pytka.taskifyapplication.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final static String REGISTER_ENDPOINT = "/auth/register";

    private final static String LOGIN_ENDPOINT = "/auth/login";

    private final static String CHANGE_PASSWORD_ENDPOINT = "/auth/changePassword";

    private final WebClient webClient;

    public AuthResponse register(RegisterRequest request){

        return webClient.post()
                .uri(REGISTER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();
    }

    public AuthResponse login(AuthRequest request){
        return webClient.post()
                .uri(LOGIN_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();
    }

    public AuthResponse changePassword(ChangePasswordRequest request){

        return webClient.post()
                .uri(CHANGE_PASSWORD_ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + SpringMainApplication.AUTH_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();
    }
}
