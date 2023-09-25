package com.pytka.taskifyapplication;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMainApplication {

    public static final int MAX_SCREEN_WIDTH = 1920;
    public static final int MAX_SCREEN_HEIGHT = 720;

    public static int CURRENT_SCREEN_WIDTH = MAX_SCREEN_WIDTH;
    public static int CURRENT_SCREEN_HEIGHT = MAX_SCREEN_HEIGHT;

    public static String AUTH_TOKEN;

    public static Long USER_ID;

    public static void main(String[] args) {
        Application.launch(TaskifyApplication.class, args);
    }
}
