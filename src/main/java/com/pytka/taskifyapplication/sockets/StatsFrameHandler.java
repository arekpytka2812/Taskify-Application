package com.pytka.taskifyapplication.sockets;

import com.pytka.taskifyapplication.core.models.StatsDTO;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

public class StatsFrameHandler implements StompFrameHandler {

    private StatsDTO statsDTO;

    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
        return StatsDTO.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
        this.statsDTO = (StatsDTO) o;

        System.out.println("\n\n[DEBUG] Received stats:\n" + statsDTO.toString() + "\n\n");
    }

    public StatsDTO getStats(){
        return this.statsDTO;
    }
}
