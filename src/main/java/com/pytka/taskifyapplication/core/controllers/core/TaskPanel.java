package com.pytka.taskifyapplication.core.controllers.core;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.core.controllers.ICenterPane;
import com.pytka.taskifyapplication.core.controllers.components.AddUpdateInfoPanel;
import com.pytka.taskifyapplication.core.controllers.components.IconButton;
import com.pytka.taskifyapplication.core.controllers.components.UpdateInfoCard;
import com.pytka.taskifyapplication.core.models.TaskDTO;
import com.pytka.taskifyapplication.core.models.UpdateInfoDTO;
import com.pytka.taskifyapplication.core.models.UserSettingsDTO;
import com.pytka.taskifyapplication.core.service.TaskService;
import com.pytka.taskifyapplication.utlis.Constants;
import com.pytka.taskifyapplication.utlis.PageNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Component
public class TaskPanel extends VBox implements ICenterPane {

    @FXML
    private HBox mainBox;

    @FXML
    private VBox leftBox;

    @FXML
    private Button exitButton;

    @FXML
    private TextField taskNameField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private CheckBox appNotifications;

    @FXML
    private CheckBox emailNotifications;

    @FXML
    private ScrollPane updatesPane;

    @FXML
    private VBox updatesContainer;

    @FXML
    private Button addUpdateInfoButton;

    @FXML
    private VBox rightBox;

    @FXML
    private ComboBox<String> taskTypeCB;

    @FXML
    private ComboBox<String> taskPriorityCB;

    @FXML
    private DatePicker expDatePicker;

    @FXML
    private ComboBox<String> timePicker;

    @FXML
    private HBox buttonsBox;

    @FXML
    private Button updateTaskButton;

    @FXML
    private Button deleteTaskButton;

    private TaskService taskService;

    private TaskDTO task;


    public TaskPanel(){
        FXMLLoader loader = new FXMLLoader(SpringMainApplication.class.getResource("/ui/core/TaskPanel.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try{
            loader.load();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }


        this.addUpdateInfoButton.setOnAction(this::onAddUpdateInfoButtonPressed);
        this.updateTaskButton.setOnAction(this::onUpdateTaskButtonPressed);
        this.deleteTaskButton.setOnMouseClicked(this::onDeleteTaskButtonPressed);
        this.exitButton.setOnMouseClicked(this::onExitButtonPressed);
    }

    public TaskPanel(TaskDTO task){

        this();
        this.setTaskData(task);
    }

    public TaskPanel(TaskService taskService, TaskDTO task, UserSettingsDTO userSettingsDTO){

        this();
        this.taskService = taskService;
        fillComboboxes(userSettingsDTO);
        this.setTaskData(task);
    }

    private void fillComboboxes(UserSettingsDTO userSettingsDTO){
        taskTypeCB.getItems().addAll(userSettingsDTO.getTaskTypes());
        taskPriorityCB.getItems().addAll(userSettingsDTO.getTaskPriorities());
        timePicker.getItems().addAll(Constants.TASK_EXPIRATION_TIMES);
    }

    public void setTaskData(TaskDTO task){

        if(task == null){
            return;
        }

        this.task = task;

        this.taskNameField.setText(this.task.getName());
        this.descriptionField.setText(this.task.getDescription());
        this.appNotifications.setSelected(this.task.getAppNotifications());
        this.emailNotifications.setSelected(this.task.getEmailNotifications());

        if(this.updatesContainer.getChildren() != null){
            this.updatesContainer.getChildren().clear();
        }

        for(UpdateInfoDTO updateInfoDTO : this.task.getTaskUpdates()){
            this.updatesContainer.getChildren().add(
                    new UpdateInfoCard(updateInfoDTO.getUpdateInfoDate(), updateInfoDTO.getDescription())
            );
        }

        this.taskTypeCB.setValue(this.task.getTaskType());
        this.taskPriorityCB.setValue(this.task.getPriority());

        // TODO: it somehow returns null from backend

        if(this.task.getEmailNotifications() != null){
            this.expDatePicker.setValue(
                    LocalDate.parse(
                            this.task.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            )
                    )
            );
            this.timePicker.setValue(this.task.getExpirationDate().format(DateTimeFormatter.ofPattern("HH:mm")));
        }

    }

    private void onAddUpdateInfoButtonPressed(ActionEvent event){
        PageNavigator.getInstance().push(
                new AddUpdateInfoPanel(
                        task.getName(),
                        task.getID(),
                        taskService
                )
        );
    }

    private void onUpdateTaskButtonPressed(ActionEvent event){

        System.out.println(timePicker.getValue());

        String[] splitedTime = timePicker.getValue().split(":");

        LocalDateTime expDate = expDatePicker.getValue().atTime(
                Integer.parseInt(splitedTime[0]),
                Integer.parseInt(splitedTime[1])
        );

        TaskDTO newTask = TaskDTO.builder()
                .ID(task.getID())
                .name(taskNameField.getText())
                .description(descriptionField.getText())
                .appNotifications(appNotifications.isSelected())
                .emailNotifications(emailNotifications.isSelected())
                .taskUpdates(task.getTaskUpdates())
                .taskType(taskTypeCB.getValue())
                .priority(taskPriorityCB.getValue())
                .expirationDate(expDate)
                .workspaceID(task.getWorkspaceID())
                .build();

        this.taskService.updateTask(newTask);

    }

    private void onDeleteTaskButtonPressed(MouseEvent event){

        this.taskService.deleteTask(task.getID());

        exitButton.fireEvent(event);
    }

    private void onExitButtonPressed(MouseEvent event){
        PageNavigator.getInstance().pop();
    }

    @Override
    public void refresh(){
        this.setTaskData(
                this.taskService.getTaskByID(task.getID())
        );
    }


}
