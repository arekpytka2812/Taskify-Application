package com.pytka.taskifyapplication.core.controllers.core;


import com.pytka.taskifyapplication.core.controllers.ICenterPane;
import com.pytka.taskifyapplication.core.controllers.components.SidePanel;
import com.pytka.taskifyapplication.core.controllers.components.UserRightPanel;
import com.pytka.taskifyapplication.core.controllers.components.WorkspaceCard;
import com.pytka.taskifyapplication.core.controllers.components.WorkspaceLeftPanel;
import com.pytka.taskifyapplication.core.models.WorkspaceDTO;
import com.pytka.taskifyapplication.core.service.TaskService;
import com.pytka.taskifyapplication.core.service.WorkspaceService;
import com.pytka.taskifyapplication.utlis.PageNavigator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.scene.layout.BorderPane.setMargin;


@Component
@RequiredArgsConstructor
public class MainFrameController implements ICenterPane {

    @FXML
    private BorderPane mainPane;

    @FXML
    private WorkspaceLeftPanel workspacesPanel;

    @FXML
    private StackPane centerPane;

    @FXML
    private UserRightPanel userPanel;

    private MainCenterPanel currentCenterPanel;

    private Map<WorkspaceCard, MainCenterPanel> centerPanelsMap;

    private final TaskService taskService;

    private final WorkspaceService workspaceService;

    @FXML
    public void initialize() {

        refresh();

        this.userPanel.socketsSetup();
    }

    @Override
    public void refresh(){

        this.setupSidePanels();

        this.workspacesPanel.clear();
        this.centerPanelsMap = new HashMap<>();

        List<WorkspaceDTO> workspaceDTOs = this.workspaceService.getWorkspacesByUserID();

        for(WorkspaceDTO workspace : workspaceDTOs){

            WorkspaceCard workspaceCard = new WorkspaceCard(workspace.getName());

            MainCenterPanel mainCenterPanel = new MainCenterPanel(
                    taskService,
                    workspace.getID(),
                    workspace.getTasks()
            );

            this.centerPanelsMap.put(workspaceCard, mainCenterPanel);

            workspaceCard.setOnMouseClicked(e -> {

                this.currentCenterPanel = centerPanelsMap.get(workspaceCard);
                this.currentCenterPanel.refreshTasks();

                PageNavigator.getInstance().pop();
                PageNavigator.getInstance().push(this.currentCenterPanel);

            });


            this.workspacesPanel.addWorkspace(workspaceCard);
            this.currentCenterPanel = centerPanelsMap.get(workspaceCard);
        }


        this.workspacesPanel.getAddWorkspaceButton().setOnMouseClicked(event -> {
            PageNavigator.getInstance().push(new WorkspacePanel(workspaceService, this));
        });

        centerPane.getChildren().addAll();

        PageNavigator.getInstance().setRoot(centerPane);
        PageNavigator.getInstance().push(currentCenterPanel);
    }


    private void setupSidePanels(){

        setMargin(centerPane, new Insets(0,0,0,0));

    }

}
