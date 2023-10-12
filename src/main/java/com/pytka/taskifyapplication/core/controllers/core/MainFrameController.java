package com.pytka.taskifyapplication.core.controllers.core;


import com.pytka.taskifyapplication.core.controllers.ICenterPane;
import com.pytka.taskifyapplication.core.controllers.components.SidePanel;
import com.pytka.taskifyapplication.core.controllers.components.WorkspaceCard;
import com.pytka.taskifyapplication.core.models.WorkspaceDTO;
import com.pytka.taskifyapplication.core.service.TaskService;
import com.pytka.taskifyapplication.core.service.WorkspaceService;
import com.pytka.taskifyapplication.sockets.NotificationSocketClient;
import com.pytka.taskifyapplication.sockets.StatsSocketClient;
import com.pytka.taskifyapplication.utlis.PageNavigator;
import com.pytka.taskifyapplication.utlis.WebSocketStompClientCreator;
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
import java.util.concurrent.CompletableFuture;

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
    private SidePanel userPanel;

    private MainCenterPanel currentCenterPanel;

    private Map<WorkspaceCard, MainCenterPanel> centerPanelsMap;

    private boolean workspacesPanelShown = false;

    private boolean userPanelShown = false;


    private TranslateTransition workspacesPanelTransition;

    private TranslateTransition userPanelTransition;

    private final TaskService taskService;

    private final WorkspaceService workspaceService;

    private NotificationSocketClient notClient;

    private StatsSocketClient statsClient;

    @FXML
    public void initialize() {

        this.notClient = new NotificationSocketClient(
                WebSocketStompClientCreator.createWebSocketStompClient()
        );

        this.statsClient = new StatsSocketClient(
                WebSocketStompClientCreator.createWebSocketStompClient()
        );


        refresh();

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
        userPanel.repaint("/icons/settings-icon.jpg");

        workspacesPanel.setPrefWidth(400);
        workspacesPanel.setTranslateX(-350);
        workspacesPanel.setBottomBoxAlignment(SidePanel.PanelSide.LEFT);
        setMargin(workspacesPanel, new Insets(0, workspacesPanel.translateXProperty().doubleValue(), 0, 0));

        userPanel.setPrefWidth(400);

        userPanel.setBottomBoxAlignment(SidePanel.PanelSide.RIGHT);

        setMargin(centerPane, new Insets(0,0,0,0));

        workspacesPanelTransition =
                new TranslateTransition(Duration.millis(200), workspacesPanel);

        userPanelTransition =
                new TranslateTransition(Duration.millis(2000), userPanel);


        workspacesPanel.getShowButton().setOnClicked(this::translateWorkspacesPanel);

        userPanel.getShowButton().setOnClicked(this::translateUserPanel);

        workspacesPanel.translateXProperty().addListener(e -> {
            setMargin(workspacesPanel, new Insets(0, workspacesPanel.translateXProperty().doubleValue(), 0, 0));
        });

//        userPanel.translateXProperty().addListener(e -> {
//            setMargin(centerPane, new Insets(
//                    0,
//                    userPanel.translateXProperty().doubleValue(),
//                    0,
//                    0
//            ));
////            setMargin(centerPane, new Insets(0,0,0,0));
//
//        });
    }


    private void translateUserPanel(MouseEvent event) {
        if(userPanelShown){
            userPanelTransition.setFromX(1520);
            userPanelTransition.setToX(1870);
        }
        else{
            userPanelTransition.setFromX(1870);
            userPanelTransition.setToX(1520);
        }

        userPanelShown = !userPanelShown;

        System.out.println(userPanel.getLayoutX());

        userPanelTransition.play();

    }

    private void translateWorkspacesPanel(MouseEvent event){
        if(workspacesPanelShown){
            workspacesPanelTransition.setFromX(0);
            workspacesPanelTransition.setToX(-350);
        }
        else{
            workspacesPanelTransition.setFromX(-350);
            workspacesPanelTransition.setToX(0);
        }

        workspacesPanelShown = !workspacesPanelShown;

        workspacesPanelTransition.play();
    }

}