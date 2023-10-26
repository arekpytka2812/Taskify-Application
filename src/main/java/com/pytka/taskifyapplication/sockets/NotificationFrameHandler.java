package com.pytka.taskifyapplication.sockets;

import com.pytka.taskifyapplication.core.controllers.components.UserRightPanel;
import com.pytka.taskifyapplication.core.models.TaskNotificationDTO;
import javafx.application.Platform;
import javafx.concurrent.Task;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

public class NotificationFrameHandler implements StompFrameHandler {

    private UserRightPanel panel;


    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return TaskNotificationDTO.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
        TaskNotificationDTO taskNotification = (TaskNotificationDTO) o;

        Platform.runLater(() -> {
            this.panel.addNotification(taskNotification);
        });



        System.out.println("\n\n[DEBUG] Received notification:\n" + taskNotification.getMessage() + "\n\n");
    }

    public void setPanel(UserRightPanel panel){
        this.panel = panel;
    }
}
