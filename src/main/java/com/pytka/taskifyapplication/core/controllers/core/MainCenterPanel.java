package com.pytka.taskifyapplication.core.controllers.core;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.core.controllers.ICenterPane;
import com.pytka.taskifyapplication.core.controllers.components.TaskCard;
import com.pytka.taskifyapplication.core.models.TaskDTO;
import com.pytka.taskifyapplication.core.service.TaskService;
import com.pytka.taskifyapplication.sockets.NotificationSocketClient;
import com.pytka.taskifyapplication.sockets.StatsSocketClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Setter
@Getter
public class MainCenterPanel extends VBox implements ICenterPane {

    @FXML
    private Button addTaskButton;

    @FXML
    private HBox topBox;

    @FXML
    private ComboBox<String> sortingCB;

    @FXML
    private FlowPane tasksContainer;

    private TaskService taskService;

    private Long workspaceID;

    private List<TaskDTO> tasks;



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

    public void setTasks(List<TaskDTO> tasks){

        if(this.tasksContainer.getChildren() != null){
            this.tasksContainer.getChildren().clear();
        }

        this.tasks = tasks;

        for(TaskDTO task : tasks){
            tasksContainer.getChildren().add(
                    new TaskCard(task)
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
                    new TaskCard(task)
            );
        }
    }

    private void addTaskButtonPressed(MouseEvent event){

    }

    @Override
    public void refresh(){
        refreshTasks();
    }

}
