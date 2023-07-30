package com.pytka.taskifyapplication.controllers;


import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.controllers.components.HideablePanel;
import com.pytka.taskifyapplication.controllers.components.TaskCard;
import com.pytka.taskifyapplication.models.TaskDTO;
import com.pytka.taskifyapplication.models.WorkspaceDTO;
import com.pytka.taskifyapplication.services.TaskService;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class UIController {

    @FXML
    private HBox tasksContainer;

    @FXML
    private HideablePanel leftPanel;

    @FXML
    private HideablePanel rightPanel;


    private final TaskService taskService;

    private List<WorkspaceDTO> workspaceDTOs;

    private int workspacesCounter = 0;


    @FXML
    public void initialize() {

        this.taskService.getTasks(this::addTaskToHBox);

        leftPanel.setDirection(HideablePanel.DirectionToVisibility.RIGHT);
        leftPanel.setMaxLeftX(-350);
        leftPanel.setMaxRightX(0);
        rightPanel.setDirection(HideablePanel.DirectionToVisibility.LEFT);
        rightPanel.setMaxRightX(SpringMainApplication.CURRENT_SCREEN_WIDTH - 50);
        rightPanel.setMaxLeftX(SpringMainApplication.CURRENT_SCREEN_WIDTH - 400);

        leftPanel.repaint("/icons/user.png");
        rightPanel.repaint("/icons/settings-icon.jpg");
    }

    private void addTaskToHBox(List<WorkspaceDTO> workspaceDTOs){
        List<TaskDTO> tasks = workspaceDTOs.get(workspacesCounter).getTasks();
        Platform.runLater(() -> {
                    for(var task :tasks){
                        tasksContainer.getChildren().add(
                                new TaskCard(
                                        task.getName(),
                                        task.getPriority(),
                                        task.getTaskType()
                                )
                        );
                    }
                }
        );

        workspacesCounter++;

    }

}
