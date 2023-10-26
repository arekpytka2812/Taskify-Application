package com.pytka.taskifyapplication.sockets;

import com.pytka.taskifyapplication.core.controllers.components.UserRightPanel;
import com.pytka.taskifyapplication.core.models.StatsDTO;
import javafx.application.Platform;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

public class StatsFrameHandler implements StompFrameHandler {

    private UserRightPanel panel;

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return StatsDTO.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {

        Platform.runLater(() -> {
            this.panel.updateStats((StatsDTO) o);
        });



        System.out.println("\n\n[DEBUG] Received stats:\n" + ((StatsDTO) o).toString() + "\n\n");
    }

    public void setPanel(UserRightPanel panel){
        this.panel = panel;
    }
}
