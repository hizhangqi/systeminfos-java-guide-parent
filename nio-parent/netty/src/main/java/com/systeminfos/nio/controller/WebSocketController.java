package com.systeminfos.nio.controller;

import com.systeminfos.nio.netty.websocket.ConnectionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    @GetMapping(value = "/broadcast", produces = "text/plain", params = "message")
    public String broadcast(String message) {
        ConnectionManager.getInstance().broadcast(message);
        return "broadcast";
    }

}
