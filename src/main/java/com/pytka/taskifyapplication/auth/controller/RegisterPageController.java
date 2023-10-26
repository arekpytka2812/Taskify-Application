package com.pytka.taskifyapplication.auth.controller;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.auth.model.AuthResponse;
import com.pytka.taskifyapplication.auth.model.RegisterRequest;
import com.pytka.taskifyapplication.auth.model.VerificationCodeRequest;
import com.pytka.taskifyapplication.auth.service.AuthService;
import com.pytka.taskifyapplication.utlis.PasswordChecker;
import com.pytka.taskifyapplication.utlis.StageChanger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterPageController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button registerButton;

    private final AuthService authService;

    private final ApplicationContext context;

    @FXML
    private void initialize(){
        Platform.runLater(() -> firstnameField.requestFocus());
    }


    public void loginButtonPressed(ActionEvent event){

        Stage stage = StageChanger.changeStage(
                event,
                "/ui/auth/LoginPage.fxml",
                context
        );

        stage.show();
    }

    public void registerButtonPressed(ActionEvent event){

        errorLabel.setText("");

        if(areFieldsEmpty()){
            errorLabel.setText("Fields are empty!");
            return;
        }

        if(!PasswordChecker.isValidPassword(passwordField.getText())){
            errorLabel.setText("Password to weak!");
            return;
        }

        RegisterRequest request = RegisterRequest.builder()
                .firstname(firstnameField.getText())
                .lastname(lastnameField.getText())
                .email(emailField.getText())
                .username(usernameField.getText())
                .password(passwordField.getText())
                .verificationCode(null)
                .sentDate(null)
                .build();

        VerificationCodeRequest codeRequest = VerificationCodeRequest.builder()
                .email(emailField.getText())
                .username(usernameField.getText())
                .build();

        try{
            this.authService.generateVerificationCode(codeRequest);
        }
        catch (Exception e){
            this.errorLabel.setText("Something went wrong!");
            return;
        }

        Stage stage = StageChanger.changeStage(
                event,
                "/ui/auth/VerificationCodePage.fxml",
                context
        );

        ((VerificationCodePageController)stage.getScene().getUserData())
                .setCachedRegisterData(request);

        stage.show();

    }

    private boolean areFieldsEmpty(){
        return firstnameField.getText().isEmpty() &&
                lastnameField.getText().isEmpty() &&
                emailField.getText().isEmpty() &&
                passwordField.getText().isEmpty();
    }
}
