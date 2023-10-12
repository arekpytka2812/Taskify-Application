package com.pytka.taskifyapplication.sockets;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.core.models.StatsDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.net.URI;
import java.util.concurrent.ExecutionException;


public class StatsSocketClient {

//    @Value("${webclient.basic.url}")
    private String basicURL = "ws://localhost:8069/api/0.0.1-SNAPSHOT/ws";

    private WebSocketStompClient stompClient;

    private final StompSession stompSession;

    private final StatsFrameHandler statsFrameHandler;

    public StatsSocketClient(WebSocketStompClient stompClient){

        this.stompClient = stompClient;

        WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        webSocketHttpHeaders.setBearerAuth(SpringMainApplication.AUTH_TOKEN);

        try {
            this.stompSession = stompClient.connect(basicURL, webSocketHttpHeaders, new StompSessionHandlerAdapter() {}).get();
        } catch (InterruptedException | ExecutionException e) {

            e.printStackTrace();

            throw new RuntimeException(e);

        }

        this.statsFrameHandler = new StatsFrameHandler();

        this.stompSession.subscribe("/user/" + SpringMainApplication.USER_ID + "/stats", statsFrameHandler);

        System.out.println("\n\n[DEBUG - STATS] Successfully connected and subscribed!\n\n");
    }

    public StatsDTO receiveMessage(){
        return this.statsFrameHandler.getStats();
    }
}
