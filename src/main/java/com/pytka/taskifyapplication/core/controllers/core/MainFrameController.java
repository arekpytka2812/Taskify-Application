package com.pytka.taskifyapplication.core.controllers.core;


import com.pytka.taskifyapplication.core.controllers.ICenterPane;
import com.pytka.taskifyapplication.core.controllers.components.SidePanel;
import com.pytka.taskifyapplication.core.controllers.components.UserRightPanel;
import com.pytka.taskifyapplication.core.controllers.components.WorkspaceCard;
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
    private SidePanel workspacesPanel;

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

        this.workspacesPanel.getMainBox().getChildren().clear();
        this.centerPanelsMap = new HashMap<>();

        List<WorkspaceDTO> workspaceDTOs = this.workspaceService.getWorkspacesByUserID();

        for(WorkspaceDTO workspace : workspaceDTOs){

            WorkspaceCard workspaceCard = new WorkspaceCard();
            workspaceCard.getWorkspaceName().setText(workspace.getName());

            MainCenterPanel mainCenterPanel = new MainCenterPanel();
            mainCenterPanel.setWorkspaceID(workspace.getID());
            mainCenterPanel.setTaskService(taskService);
            mainCenterPanel.setTasks(workspace.getTasks());

            this.centerPanelsMap.put(workspaceCard, mainCenterPanel);

            workspaceCard.setOnMouseClicked(e -> {

                this.currentCenterPanel = centerPanelsMap.get(workspaceCard);
                this.currentCenterPanel.refreshTasks();

                PageNavigator.getInstance().pop();
                PageNavigator.getInstance().push(this.currentCenterPanel);

            });

            this.workspacesPanel.getMainBox().getChildren().add(workspaceCard);
            this.currentCenterPanel = centerPanelsMap.get(workspaceCard);
        }

        WorkspaceCard addWorkspace = new WorkspaceCard();
        addWorkspace.getWorkspaceName().setText(" + add new workspace!");
        addWorkspace.setOnMouseClicked(event -> {
            PageNavigator.getInstance().push(new WorkspacePanel(workspaceService, this));
        });


        this.workspacesPanel.getMainBox().getChildren().add(addWorkspace);

        centerPane.getChildren().addAll();

        PageNavigator.getInstance().setRoot(centerPane);
        PageNavigator.getInstance().push(currentCenterPanel);
    }


    private void setupSidePanels(){

        workspacesPanel.repaint("/icons/user.png");

        workspacesPanel.setPrefWidth(400);
        workspacesPanel.setTranslateX(0);
        workspacesPanel.setBottomBoxAlignment(SidePanel.PanelSide.LEFT);

        userPanel.setPrefWidth(400);


        setMargin(centerPane, new Insets(0,0,0,0));


    }

}
