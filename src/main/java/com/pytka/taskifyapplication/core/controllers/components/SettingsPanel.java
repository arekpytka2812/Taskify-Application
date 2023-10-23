package com.pytka.taskifyapplication.core.controllers.components;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.auth.model.ChangePasswordRequest;
import com.pytka.taskifyapplication.auth.service.AuthService;
import com.pytka.taskifyapplication.core.controllers.ICenterPane;
import com.pytka.taskifyapplication.core.models.TaskPrioritiesDTO;
import com.pytka.taskifyapplication.core.models.TaskTypesDTO;
import com.pytka.taskifyapplication.core.models.UserSettingsDTO;
import com.pytka.taskifyapplication.core.service.UserSettingsService;
import com.pytka.taskifyapplication.utlis.PageNavigator;
import com.pytka.taskifyapplication.utlis.PasswordChecker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class SettingsPanel extends VBox implements ICenterPane {

    @FXML
    private Button exitButton;

    @FXML
    private ListView<String> taskTypesView;

    @FXML
    private Button taskTypesUpdateButton;

    @FXML
    private ListView<String> taskPrioritiesView;

    @FXML
    private Button taskPrioritiesUpdateButton;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField1;

    @FXML
    private PasswordField newPasswordField2;

    @FXML
    private Button passwordUpdateButton;

    private final AuthService authService;

    private final UserSettingsService userSettingsService;

    public SettingsPanel(AuthService authService, UserSettingsService userSettingsService){

        FXMLLoader loader = new FXMLLoader(SpringMainApplication.class.getResource("/ui/components/SettingsPanel.fxml"));

        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.exitButton.setOnMouseClicked(this::exitButtonPressed);
        this.taskTypesUpdateButton.setOnMouseClicked(this::taskTypesUpdateButtonPressed);
        this.taskPrioritiesUpdateButton.setOnMouseClicked(this::taskPrioritiesUpdateButtonPressed);
        this.passwordUpdateButton.setOnMouseClicked(this::passwordUpdateButtonPressed);

        this.authService = authService;
        this.userSettingsService = userSettingsService;

        loadListViews();

    }

    private void exitButtonPressed(MouseEvent event){
        PageNavigator.getInstance().pop();
    }

    private void taskTypesUpdateButtonPressed(MouseEvent event){

        System.out.println("\n\n" + taskTypesView.getItems().toString() + "\n\n");

        TaskTypesDTO taskTypesDTO = TaskTypesDTO.builder()
                .taskTypes(taskTypesView.getItems())
                .build();

        this.userSettingsService.updateTaskTypes(taskTypesDTO);
    }

    private void taskPrioritiesUpdateButtonPressed(MouseEvent event){

        System.out.println("\n\n" + taskPrioritiesView.getItems().toString() + "\n\n");

        TaskPrioritiesDTO taskPrioritiesDTO = TaskPrioritiesDTO.builder()
                .taskPriorities(taskPrioritiesView.getItems())
                .build();

        this.userSettingsService.updateTaskPriorities(taskPrioritiesDTO);
    }

    private void passwordUpdateButtonPressed(MouseEvent event){

        if(oldPasswordField.getText().isEmpty()
                || newPasswordField1.getText().isEmpty()
                || newPasswordField2.getText().isEmpty()
        ) {
            return;
        }

        if(!checkPasswordsConditions()){
            return;
        }

        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .email(SpringMainApplication.USER_EMAIL)
                .oldPassword(oldPasswordField.getText())
                .newPassword(newPasswordField1.getText())
                .build();

        this.authService.changePassword(request);

    }

    private boolean checkPasswordsConditions(){

        String oldPassword = oldPasswordField.getText();
        String newPassword1 = newPasswordField1.getText();
        String newPassword2 = newPasswordField2.getText();

        if(oldPassword.equals(newPassword1)){
            return false;
        }

        if(!newPassword1.equals(newPassword2)){
            return false;
        }

        return PasswordChecker.isValidPassword(newPassword1);
    }

    private void loadListViews(){

        UserSettingsDTO userSettingsDTO = this.userSettingsService.getUserSettings();

        if(userSettingsDTO == null){
            return;
        }

        this.taskTypesView.getItems().addAll(userSettingsDTO.getTaskTypes());
        this.taskPrioritiesView.getItems().addAll(userSettingsDTO.getTaskPriorities());

        this.taskTypesView.setCellFactory(TextFieldListCell.forListView());
        this.taskPrioritiesView.setCellFactory(TextFieldListCell.forListView());
    }

    @Override
    public void refresh(){

    }
}
