package com.pytka.taskifyapplication;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMainApplication {

    public static String AUTH_TOKEN;

    public static Long USER_ID;

    public static void main(String[] args) {
        Application.launch(TaskifyApplication.class, args);
    }
}
