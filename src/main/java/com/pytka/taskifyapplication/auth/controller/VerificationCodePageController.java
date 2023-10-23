package com.pytka.taskifyapplication.auth.controller;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.auth.model.AuthResponse;
import com.pytka.taskifyapplication.auth.model.RegisterRequest;
import com.pytka.taskifyapplication.auth.model.VerificationCodeRequest;
import com.pytka.taskifyapplication.auth.service.AuthService;
import com.pytka.taskifyapplication.utlis.StageChanger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class VerificationCodePageController {

    @FXML
    private Button exitButton;

    @FXML
    private Button resendButton;

    @FXML
    private Label notificationLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField codeField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button sendButton;


    private final AuthService authService;

    private final ApplicationContext context;

    private RegisterRequest cachedRegisterData;

    public void setCachedRegisterData(RegisterRequest request){

        this.cachedRegisterData = request;
        this.emailLabel.setText(request.getEmail());
    }

    @FXML
    private void exitButtonPressed(ActionEvent event){

        Stage stage = StageChanger.changeStage(
                event,
                "/ui/auth/RegisterPage.fxml",
                context
        );

        stage.show();

    }

    @FXML
    private void resendButtonPressed(ActionEvent event){

        VerificationCodeRequest request = VerificationCodeRequest.builder()
                .email(cachedRegisterData.getEmail())
                .username(cachedRegisterData.getUsername())
                .build();

        this.authService.regenerateVerificationCode(request);

//        Platform.runLater(() -> {
//            this.notificationLabel.setText("Resent verification code!");
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            this.notificationLabel.setText("");
//        });

    }

    @FXML
    private void sendButtonPressed(ActionEvent event){

        this.errorLabel.setText("");

        if(codeField.getText().isEmpty()){
            this.errorLabel.setText("Type verification code from sent email!");
        }

        cachedRegisterData.setVerificationCode(codeField.getText());
        cachedRegisterData.setSentDate(LocalDateTime.now());

        AuthResponse response = authService.register(cachedRegisterData);

        if(response == null){
            errorLabel.setText("Something went wrong!");
            return;
        }

        SpringMainApplication.AUTH_TOKEN = response.getToken();
        SpringMainApplication.USER_ID = response.getID();
        SpringMainApplication.USER_EMAIL = cachedRegisterData.getEmail();
        SpringMainApplication.USERNAME = cachedRegisterData.getUsername();

        Stage stage = StageChanger.changeStage(
                event,
                "/ui/core/MainFrame.fxml",
                context
        );

        stage.show();

    }

}
