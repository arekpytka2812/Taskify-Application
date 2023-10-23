package com.pytka.taskifyapplication.core.controllers.core;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.core.controllers.ICenterPane;
import com.pytka.taskifyapplication.core.controllers.components.TaskCard;
import com.pytka.taskifyapplication.core.models.TaskDTO;
import com.pytka.taskifyapplication.core.models.UserSettingsDTO;
import com.pytka.taskifyapplication.core.service.TaskService;
import com.pytka.taskifyapplication.sockets.NotificationSocketClient;
import com.pytka.taskifyapplication.sockets.StatsSocketClient;
import com.pytka.taskifyapplication.utlis.PageNavigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MainCenterPanel extends VBox implements ICenterPane {

    @FXML
    private Label workspaceLabel;

    @FXML
    private Button addTaskButton;

    @FXML
    private HBox topBox;

    @FXML
    private ComboBox<String> taskTypeFilter;

    @FXML
    private ComboBox<String> taskPriorityFilter;

    @FXML
    private FlowPane tasksContainer;

    private TaskService taskService;

    private Long workspaceID;

    private List<TaskDTO> tasks;

    private UserSettingsDTO userSettingsDTO;

    public MainCenterPanel(){

        FXMLLoader loader = new FXMLLoader(SpringMainApplication.class.getResource("/ui/core/MainCenterPanel.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try{
            loader.load();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }

        this.addTaskButton.setOnMouseClicked(this::addTaskButtonPressed);
    }

    public MainCenterPanel(
            TaskService taskService,
            Long workspaceID,
            String workspaceName,
            List<TaskDTO> tasks,
            UserSettingsDTO userSettingsDTO
    ){
        this();

        this.taskService = taskService;
        this.workspaceID = workspaceID;
        this.userSettingsDTO = userSettingsDTO;

        setWorkspaceLabel(workspaceName);

        setTasks(tasks);
    }

    public void setFilters(){

        List<String> types = new ArrayList<>(userSettingsDTO.getTaskTypes());
        types.add(0, "None");
        taskTypeFilter.getItems().addAll(types);
        taskTypeFilter.setValue("None");

        List<String> priorities = new ArrayList<>(userSettingsDTO.getTaskPriorities());
        priorities.add(0, "None");
        taskPriorityFilter.getItems().addAll(priorities);
        taskPriorityFilter.setValue("None");

        taskTypeFilter.setOnAction(this::filterTasks);
        taskPriorityFilter.setOnAction(this::filterTasks);
    }

    private void filterTasks(javafx.event.ActionEvent event){

        tasksContainer.getChildren().clear();

        for(TaskDTO task : tasks){

            if((taskTypeFilter.getValue().equals("None") || task.getTaskType().equals(taskTypeFilter.getValue())) &&
                    (taskPriorityFilter.getValue().equals("None") || task.getPriority().equals(taskPriorityFilter.getValue()))
            ){
                tasksContainer.getChildren().add(
                        new TaskCard(task, userSettingsDTO)
                );
            }

        }

    }

    public void setWorkspaceLabel(String workspaceName){
        this.workspaceLabel.setText(workspaceName);
    }

    public void setTasks(List<TaskDTO> tasks){

        if(this.tasksContainer.getChildren() != null){
            this.tasksContainer.getChildren().clear();
        }

        this.tasks = tasks;

        for(TaskDTO task : tasks){
            tasksContainer.getChildren().add(
                    new TaskCard(task, userSettingsDTO)
            );
        }
    }

    public void refreshTasks(){
        this.tasks = this.taskService.getTasksByWorkspaceID(workspaceID);

        if(this.tasksContainer.getChildren() != null){
            this.tasksContainer.getChildren().clear();
        }


        for(TaskDTO task : tasks){
            tasksContainer.getChildren().add(
                    new TaskCard(task, userSettingsDTO)
            );
        }
    }

    private void addTaskButtonPressed(MouseEvent event){
        //TODO: handle this
    }

    @Override
    public void refresh(){
        refreshTasks();
    }

}
