package com.pytka.taskifyapplication.controllers.core;


import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.controllers.components.SidePanel;
import com.pytka.taskifyapplication.controllers.components.TaskCard;
import com.pytka.taskifyapplication.models.TaskDTO;
import com.pytka.taskifyapplication.models.WorkspaceDTO;
import com.pytka.taskifyapplication.services.TaskService;
import com.pytka.taskifyapplication.utlis.ParentLoader;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static javafx.scene.layout.BorderPane.setMargin;


@Component
@RequiredArgsConstructor
public class MainFrameController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private SidePanel workspacesPanel;

    @FXML
    private StackPane centerPane;

    @FXML
    private SidePanel userPanel;

    private MainCenterPanel mainCenterPanel;

    private TaskPanel taskPanel;

    private boolean workspacesPanelShown = false;

    private boolean userPanelShown = false;


    private TranslateTransition workspacesPanelTransition;

    private TranslateTransition userPanelTransition;

    private final ApplicationContext ac;

    private final TaskService taskService;

    @FXML
    public void initialize() {

        // TODO: lista nazw workspacow i idkow

        workspacesPanel.repaint("/icons/user.png");
        userPanel.repaint("/icons/settings-icon.jpg");

        workspacesPanel.setPrefWidth(400);
        workspacesPanel.setTranslateX(-350);
        workspacesPanel.setBottomBoxAlignment(SidePanel.PanelSide.LEFT);
        setMargin(workspacesPanel, new Insets(0, workspacesPanel.translateXProperty().doubleValue(), 0, 0));

        userPanel.setPrefWidth(400);
        userPanel.setTranslateX(350);
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

        userPanel.translateXProperty().addListener(e -> {
            setMargin(centerPane, new Insets(
                    0,
                    userPanel.translateXProperty().doubleValue(),
                    0,
                    0
                    ));
            setMargin(centerPane, new Insets(0,0,0,0));

        });

        this.mainCenterPanel = new MainCenterPanel();
        this.taskPanel = new TaskPanel();

        this.mainCenterPanel.setWorkspaceID(1L);
        this.mainCenterPanel.setTaskService(taskService);
        this.mainCenterPanel.refreshTasks();
        this.mainCenterPanel.getTasksContainer().getChildren().stream()
                .forEach(task -> {
                    task.setOnMouseClicked(this::taskCardPressed);
                });

        this.taskPanel.setTaskService(taskService);
        this.taskPanel.getExitButton().setOnClicked(this::taskPanelOnExitButtonPressed);

        centerPane.getChildren().addAll(mainCenterPanel);
    }

    private void translateUserPanel(MouseEvent event) {
        if(userPanelShown){
            userPanelTransition.setFromX(SpringMainApplication.MAX_SCREEN_WIDTH - 400);
            userPanelTransition.setToX(SpringMainApplication.MAX_SCREEN_WIDTH - 50);
        }
        else{
            userPanelTransition.setFromX(SpringMainApplication.MAX_SCREEN_WIDTH - 50);
            userPanelTransition.setToX(SpringMainApplication.MAX_SCREEN_WIDTH - 400);
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

    private void taskPanelOnExitButtonPressed(MouseEvent event){
        
        this.centerPane.getChildren().clear();
        this.centerPane.getChildren().add(this.mainCenterPanel);

        this.mainCenterPanel.refreshTasks();
        this.mainCenterPanel.getTasksContainer().getChildren().stream()
                .forEach(task -> {
                    task.setOnMouseClicked(this::taskCardPressed);
                });

        this.mainCenterPanel.toFront();
    }

    private void taskCardPressed(MouseEvent event){

        this.taskPanel.setTaskData(((TaskCard)event.getSource()).getTask());

        this.centerPane.getChildren().clear();
        this.centerPane.getChildren().add(this.taskPanel);

        this.taskPanel.toFront();
    }

}
