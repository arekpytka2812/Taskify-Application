package com.pytka.taskifyapplication.core.controllers.components;

import com.pytka.taskifyapplication.TaskifyApplication;
import com.pytka.taskifyapplication.core.models.StatsDTO;
import com.pytka.taskifyapplication.core.models.TaskNotificationDTO;
import com.pytka.taskifyapplication.sockets.NotificationSocketClient;
import com.pytka.taskifyapplication.sockets.StatsSocketClient;
import com.pytka.taskifyapplication.utlis.PageNavigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class UserRightPanel extends VBox {


    @FXML
    private Button settingsButton;

    @FXML
    private VBox notificationsBox;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label workspacesCreatedLabel;

    @FXML
    private Label workspacesDeletedLabel;

    @FXML
    private Label taskCreatedLabel;

    @FXML
    private Label taskDeletedLabel;

    @FXML
    private Label updateInfosCreatedLabel;

    @FXML
    private Label finishedTasksOnTimeLabel;

    @FXML
    private Label finishedTasksDelayedLabel;

    private final NotificationSocketClient notificationClient;

    private final StatsSocketClient statsClient;

    private List<TaskNotificationDTO> notificationList;


    public UserRightPanel(){

        FXMLLoader loader = new FXMLLoader(TaskifyApplication.class.getResource("/ui/components/UserRightPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try{
            loader.load();
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }

        this.notificationClient = new NotificationSocketClient();
        this.statsClient = new StatsSocketClient();

        this.notificationList = new ArrayList<>();
    }

    public void setUsername(String username){
        this.usernameLabel.setText(username);
    }

    public void updateStats(StatsDTO newStats){

        this.workspacesCreatedLabel.setText(newStats.getWorkspacesCreated().toString());
        this.workspacesDeletedLabel.setText(newStats.getWorkspacesDeleted().toString());
        this.taskCreatedLabel.setText(newStats.getTasksCreated().toString());
        this.taskDeletedLabel.setText(newStats.getTasksDeleted().toString());
        this.updateInfosCreatedLabel.setText(newStats.getUpdateInfosCreated().toString());
        this.finishedTasksOnTimeLabel.setText(newStats.getFinishedOnTimeTasks().toString());
        this.finishedTasksDelayedLabel.setText(newStats.getFinishedWithDelayTasks().toString());
    }

    public void addNotification(TaskNotificationDTO notification){
        this.notificationList.add(notification);
        updateNotifications();
    }

    private void updateNotifications(){

        this.notificationsBox.getChildren().clear();

        for(TaskNotificationDTO notification : notificationList){

            Label label = new Label(notification.getMessage());

            notificationsBox.getChildren().add(label);
        }
    }

    public void socketsSetup(){

        this.notificationClient.connect();
        this.notificationClient.subscribe();

        this.statsClient.connect();
        this.statsClient.subscribe();

        this.notificationClient.setPanelForHandler(this);
        this.statsClient.setPanelForHandler(this);
    }
}
