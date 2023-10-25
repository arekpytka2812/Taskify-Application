package com.pytka.taskifyapplication.core.controllers.core;


import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.auth.service.AuthService;
import com.pytka.taskifyapplication.core.controllers.ICenterPane;
import com.pytka.taskifyapplication.core.controllers.components.SettingsPanel;
import com.pytka.taskifyapplication.core.controllers.components.UserRightPanel;
import com.pytka.taskifyapplication.core.controllers.components.WorkspaceCard;
import com.pytka.taskifyapplication.core.controllers.components.WorkspaceLeftPanel;
import com.pytka.taskifyapplication.core.models.StatsDTO;
import com.pytka.taskifyapplication.core.models.UserSettingsDTO;
import com.pytka.taskifyapplication.core.models.WorkspaceDTO;
import com.pytka.taskifyapplication.core.service.StatsService;
import com.pytka.taskifyapplication.core.service.TaskService;
import com.pytka.taskifyapplication.core.service.UserSettingsService;
import com.pytka.taskifyapplication.core.service.WorkspaceService;
import com.pytka.taskifyapplication.utlis.PageNavigator;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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

    private final AuthService authService;

    private final TaskService taskService;

    private final WorkspaceService workspaceService;

    private final UserSettingsService userSettingsService;

    private final StatsService statsService;

    @FXML
    public void initialize() {

        refresh();

        this.userPanel.socketsSetup();

        StatsDTO statsDTO = this.statsService.getUserStats();

        this.userPanel.updateStats(statsDTO);
    }

    @Override
    public void refresh(){

        this.workspacesPanel.clear();
        this.centerPanelsMap = new HashMap<>();

        List<WorkspaceDTO> workspaceDTOs = this.workspaceService.getWorkspacesByUserID();
        UserSettingsDTO userSettingsDTO = this.userSettingsService.getUserSettings();

        for(WorkspaceDTO workspace : workspaceDTOs){

            WorkspaceCard workspaceCard = new WorkspaceCard(workspace.getName());

            MainCenterPanel mainCenterPanel = new MainCenterPanel(
                    taskService,
                    workspace.getID(),
                    workspace.getName(),
                    workspace.getTasks(),
                    userSettingsDTO
            );
            mainCenterPanel.setFilters();

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

        this.workspacesPanel.getDeleteWorkspaceButton().setOnMouseClicked(event -> {

            Long workspaceToDeleteID = currentCenterPanel.getWorkspaceID();

            this.workspaceService.deleteWorkspace(workspaceToDeleteID);

            this.refresh();
        });

        this.userPanel.getSettingsButton().setOnMouseClicked(event -> {

            if(PageNavigator.getInstance().top() instanceof SettingsPanel){
                return;
            }

            PageNavigator.getInstance().push(new SettingsPanel(authService, userSettingsService));
        });

        centerPane.getChildren().addAll();

        PageNavigator.getInstance().setRoot(centerPane);
        PageNavigator.getInstance().push(currentCenterPanel);
    }

}
