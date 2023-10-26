package com.pytka.taskifyapplication.core.controllers.components;

import com.pytka.taskifyapplication.TaskifyApplication;
import com.pytka.taskifyapplication.core.models.TaskNotificationDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;


@Getter
@Setter
public class NotificationsPanel extends ScrollPane {

    @FXML
    private VBox notificationsBox;

    public NotificationsPanel(){

        FXMLLoader loader = new FXMLLoader(TaskifyApplication.class.getResource("/ui/components/NotificationsPanel.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateNotifications(List<TaskNotificationDTO> notifications){



    }

}
