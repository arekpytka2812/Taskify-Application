package com.pytka.taskifyapplication.controllers.auth;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.TaskifyApplication;
import com.pytka.taskifyapplication.auth.model.AuthResponse;
import com.pytka.taskifyapplication.auth.model.RegisterRequest;
import com.pytka.taskifyapplication.auth.service.AuthService;
import com.pytka.taskifyapplication.utlis.ParentLoader;
import com.pytka.taskifyapplication.utlis.PasswordChecker;
import com.pytka.taskifyapplication.utlis.StageChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.SpringApplicationEvent;
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
                .build();

        AuthResponse response = authService.register(request);


        if(response == null){
            errorLabel.setText("Something went wrong!");
            return;
        }

        SpringMainApplication.AUTH_TOKEN = response.getToken();
        SpringMainApplication.USER_ID = response.getID();

        Stage stage = StageChanger.changeStage(
                event,
                "/ui/ui.fxml",
                context
        );

        stage.show();
    }

    private boolean areFieldsEmpty(){
        return firstnameField.getText().isEmpty() &&
                lastnameField.getText().isEmpty() &&
                emailField.getText().isEmpty() &&
                passwordField.getText().isEmpty();
    }
}
