package com.pytka.taskifyapplication.auth.service.impl;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.auth.model.*;
import com.pytka.taskifyapplication.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final static String GENERATE_VER_CODE_ENDPOINT = "/auth/generateCode";
    private final static String REGISTER_ENDPOINT = "/auth/register";

    private final static String REGENERATE_VER_CODE_ENDPOINT = "/auth/regenerateCode";

    private final static String LOGIN_ENDPOINT = "/auth/login";

    private final static String CHANGE_PASSWORD_ENDPOINT = "/auth/changePassword";

    private final static String REMIND_PASSWORD_ENDPOINT = "/auth/remindPassword";

    private final static String SET_NEW_PASSWORD_ENDPOINT = "/auth/setNewPassword";

    private final WebClient webClient;

    @Override
    public void generateVerificationCode(VerificationCodeRequest request) {

        this.webClient.post()
                .uri(GENERATE_VER_CODE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public AuthResponse register(RegisterRequest request){

        return webClient.post()
                .uri(REGISTER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();
    }

    @Override
    public void regenerateVerificationCode(VerificationCodeRequest request) {

        this.webClient.post()
                .uri(REGENERATE_VER_CODE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public AuthResponse login(AuthRequest request){

        return this.webClient.post()
                .uri(LOGIN_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();
    }

    @Override
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

    @Override
    public void remindPassword(RemindPasswordRequest request) {

        this.webClient.post()
                .uri(REMIND_PASSWORD_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }

    @Override
    public AuthResponse setNewPasswordAfterPasswordRemind(NewPasswordRequest request) {

        return this.webClient.post()
                .uri(SET_NEW_PASSWORD_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();
    }
}
