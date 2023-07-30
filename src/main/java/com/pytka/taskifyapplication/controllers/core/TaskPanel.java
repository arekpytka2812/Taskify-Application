package com.pytka.taskifyapplication.controllers.core;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.controllers.components.IconButton;
import com.pytka.taskifyapplication.models.TaskDTO;
import com.pytka.taskifyapplication.services.TaskService;
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

import java.io.IOException;


@Getter
public class TaskPanel extends VBox{

    @FXML
    private IconButton exitButton;

    @FXML
    private HBox mainBox;

    @FXML
    private VBox leftBox;

    @FXML
    private TextField taskNameField;

    @FXML
    private Separator nameDescSeparator;

    @FXML
    private TextField descriptionField;

    @FXML
    private Separator descNotSeparator;

    @FXML
    private CheckBox notificationsOn;

    @FXML
    private Separator notUpdatesSeparator;

    @FXML
    private ScrollPane updatesPane;

    @FXML
    private VBox updatesContainer;

    @FXML
    private Separator updatesAddSeparator;

    @FXML
    private Button addUpdateInfoButton;

    @FXML
    private Separator vertical;

    @FXML
    private VBox rightBox;

    @FXML
    private TextField taskTypeField;

    @FXML
    private Separator typePrioritySeparator;

    @FXML
    private TextField priorityField;

    @FXML
    private Separator priorityCreateSeparator;

    @FXML
    private DatePicker createDatePicker; //TODO: dont make it editable

    @FXML
    private Separator createExpSeparator;

    @FXML
    private DatePicker expDatePicker;

    @FXML
    private Separator expButtonSeparator;

    @FXML
    private HBox buttonsBox;

    @FXML
    private Button updateTaskButton;

    @FXML
    private Separator updateDeleteSeparator;

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

        this.exitButton.setImageAndBackground("/icons/back.png", null);

        this.addUpdateInfoButton.setOnAction(this::onAddUpdateInfoButtonPressed);
        this.updateTaskButton.setOnAction(this::onUpdateTaskButtonPressed);
        this.deleteTaskButton.setOnMouseClicked(this::onDeleteTaskButtonPressed);
    }

    public void setTaskData(TaskDTO task){

        if(task == null){
            return;
        }

        this.task = task;

        this.taskNameField.setText(this.task.getName());
        this.descriptionField.setText(this.task.getDescription());
        // TODO: notification is on?
        // TODO: updateInfo card and then read date to card and add cards to vbox
        this.taskTypeField.setText(this.task.getTaskType());
        this.priorityField.setText(this.task.getPriority());

        // TODO: it somehow returns null from backend
        //this.createDatePicker.getEditor().setText(this.task.getCreateDate().toString());

        if(this.task.getExpirationDate() == null){
            return;
        }
        this.expDatePicker.getEditor().setText(this.task.getExpirationDate().toString());
    }

    public void setTaskService(TaskService taskService){
        this.taskService = taskService;
    }

    private void onAddUpdateInfoButtonPressed(ActionEvent event){

        // this.taskService.addTaskUpdate(task.getID(),)
    }

    private void onUpdateTaskButtonPressed(ActionEvent event){

    }

    private void onDeleteTaskButtonPressed(MouseEvent event){
        boolean response = this.taskService.deleteTask(task.getID());

        if(response){
            System.out.println("YIIIIPPPPEEE");
        }

        exitButton.getOnMouseClicked().handle(event);
    }

}
