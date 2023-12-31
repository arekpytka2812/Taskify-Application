package com.pytka.taskifyapplication.auth.controller;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.auth.model.AuthRequest;
import com.pytka.taskifyapplication.auth.model.AuthResponse;
import com.pytka.taskifyapplication.auth.service.AuthService;
import com.pytka.taskifyapplication.utlis.StageChanger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginPageController {

    @FXML
    private Button registerButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    private final AuthService authService;

    private final ApplicationContext context;

    @FXML
    private void initialize(){
        Platform.runLater(() -> emailField.requestFocus());
    }


    public void registerButtonPressed(ActionEvent event){
        Stage stage = StageChanger.changeStage(
                event,
                "/ui/auth/RegisterPage.fxml",
                context
        );

        stage.show();
    }

    public void loginButtonPressed(ActionEvent event){

        errorLabel.setText("");

        if(areFieldsEmpty()){
            errorLabel.setText("Fields are empty!");
            return;
        }

        AuthRequest request = AuthRequest.builder()
                .email(emailField.getText())
                .password(passwordField.getText())
                .build();

        AuthResponse response = this.authService.login(request);

        if(response == null){
            errorLabel.setText("Something went wrong!");
            return;
        }

        SpringMainApplication.AUTH_TOKEN = response.getToken();
        SpringMainApplication.USER_ID = response.getID();
        SpringMainApplication.USERNAME = response.getUsername();
        SpringMainApplication.USER_EMAIL = emailField.getText();

        Stage stage = StageChanger.changeStage(
                event,
                "/ui/core/MainFrame.fxml",
                context
        );

        stage.show();
    }

    private boolean areFieldsEmpty(){
        return emailField.getText().isEmpty() && passwordField.getText().isEmpty();
    }
}
