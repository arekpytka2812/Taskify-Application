package com.pytka.taskifyapplication.config;

import com.pytka.taskifyapplication.auth.service.AuthService;
import com.pytka.taskifyapplication.auth.service.impl.AuthServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;

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

}
