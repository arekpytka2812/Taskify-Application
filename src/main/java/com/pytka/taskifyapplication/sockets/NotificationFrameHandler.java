package com.pytka.taskifyapplication.sockets;

import com.pytka.taskifyapplication.core.models.TaskNotificationDTO;
import javafx.concurrent.Task;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

public class NotificationFrameHandler implements StompFrameHandler {

    private TaskNotificationDTO taskNotification = null;

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return TaskNotificationDTO.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
        this.taskNotification = (TaskNotificationDTO) o;

        System.out.println("\n\n[DEBUG] Received notification:\n" + taskNotification.getMessage() + "\n\n");
    }

    public TaskNotificationDTO getTaskNotification(){
        return this.taskNotification;
    }
}
