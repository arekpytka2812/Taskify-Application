package com.pytka.taskifyapplication.core.controllers.components;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.TaskifyApplication;
import com.pytka.taskifyapplication.core.controllers.ICenterPane;
import com.pytka.taskifyapplication.core.models.TaskDTO;
import com.pytka.taskifyapplication.core.models.UserSettingsDTO;
import com.pytka.taskifyapplication.core.service.TaskService;
import com.pytka.taskifyapplication.core.service.UserSettingsService;
import com.pytka.taskifyapplication.utlis.Constants;
import com.pytka.taskifyapplication.utlis.PageNavigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AddTaskPanel extends VBox implements ICenterPane {

    @FXML
    private Button exitButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private CheckBox appNotificationsBox;

    @FXML
    private CheckBox emailNotificationsBox;

    @FXML
    private ComboBox<String> taskTypeCB;

    @FXML
    private ComboBox<String> taskPriorityCB;

    @FXML
    private DatePicker expDatePicker;

    @FXML
    private ComboBox<String> expTimeCB;

    @FXML
    private Button createButton;



    private final TaskService taskService;

    private final Long workspaceID;

    @SneakyThrows
    public AddTaskPanel(
            TaskService taskService,
            Long workspaceID,
            UserSettingsDTO userSettingsDTO
    ){

        FXMLLoader loader = new FXMLLoader(SpringMainApplication.class.getResource("/ui/components/AddTaskPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        loader.load();

        this.taskService = taskService;
        this.workspaceID = workspaceID;

        this.taskTypeCB.getItems().addAll(userSettingsDTO.getTaskTypes());
        this.taskPriorityCB.getItems().addAll(userSettingsDTO.getTaskPriorities());
        this.expTimeCB.getItems().addAll(Constants.TASK_EXPIRATION_TIMES);

        this.exitButton.setOnMouseClicked(this::exitButtonPressed);
        this.createButton.setOnMouseClicked(this::createButtonPressed);
    }

    private void exitButtonPressed(MouseEvent event){
        PageNavigator.getInstance().pop();
    }

    private void createButtonPressed(MouseEvent event){

        String[] splitedTime = expTimeCB.getValue().split(":");

        LocalDateTime expDate = expDatePicker.getValue().atTime(
                Integer.parseInt(splitedTime[0]),
                Integer.parseInt(splitedTime[1])
        );

        TaskDTO taskDTO = TaskDTO.builder()
                .name(nameField.getText())
                .description(descriptionArea.getText())
                .appNotifications(appNotificationsBox.isSelected())
                .emailNotifications(emailNotificationsBox.isSelected())
                .taskType(taskTypeCB.getValue())
                .priority(taskPriorityCB.getValue())
                .expirationDate(expDate)
                .taskUpdates(new ArrayList<>())
                .workspaceID(workspaceID)
                .build();

        this.taskService.addTask(taskDTO);

        PageNavigator.getInstance().pop();
    }

    @Override
    public void refresh(){

    }
}
