package com.pytka.taskifyapplication.sockets;


import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.core.controllers.components.UserRightPanel;
import com.pytka.taskifyapplication.core.models.TaskNotificationDTO;
import com.pytka.taskifyapplication.utlis.WebSocketStompClientCreator;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;


@Component
@NoArgsConstructor
public class NotificationSocketClient  {

//    @Value("${webclient.basic.url}")
    private String basicURL = "ws://localhost:8069/api/0.0.1-SNAPSHOT/ws";

    private  WebSocketStompClient stompClient;

    private StompSession stompSession;

    private NotificationFrameHandler notificationHandler;

    private boolean connected = false;

    private boolean subscribed = false;

    public void connect(){

        if(connected){
            System.out.println("You are already connected!");
            return;
        }

        this.stompClient = WebSocketStompClientCreator.createWebSocketStompClient();

        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        webSocketHttpHeaders.setBearerAuth(SpringMainApplication.AUTH_TOKEN);

        try {
            this.stompSession = stompClient.connect(basicURL, webSocketHttpHeaders, new StompSessionHandlerAdapter() {}).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        this.notificationHandler = new NotificationFrameHandler();

        connected = true;

    }

    public void subscribe(){

        if(subscribed){
            System.out.println("You are already subscribed!");
            return;
        }

        this.stompSession.subscribe("/user/" + SpringMainApplication.USER_ID + "/notification", notificationHandler);

        System.out.println("\n\n[DEBUG - NOTIFICATION] Successfully connected and subscribed!\n\n");

        subscribed = true;
    }

    public void setPanelForHandler(UserRightPanel panel){
        this.notificationHandler.setPanel(panel);
    }

}
