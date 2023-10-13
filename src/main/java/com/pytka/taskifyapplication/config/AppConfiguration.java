package com.pytka.taskifyapplication.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pytka.taskifyapplication.core.service.RequestService;
import com.pytka.taskifyapplication.core.service.impl.RequestServiceImpl;
import com.pytka.taskifyapplication.sockets.NotificationSocketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@PropertySource("classpath:/prop/rest.properties")
public class AppConfiguration {

    @Value("${webclient.basic.url}")
    private String basicURL;

    @Bean
    @ConditionalOnMissingBean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl(basicURL)
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        return om;
    }

    @Bean
    public RequestService requestService(){
        return new RequestServiceImpl(webClient(), objectMapper());
    }




}
