package com.systeminfos.nio.netty.websocket;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private static final ConnectionManager INSTANCE = new ConnectionManager();
    private final ConcurrentHashMap<String, Channel> connections = new ConcurrentHashMap<>();

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        return INSTANCE;
    }

    public void addConnection(Channel channel) {
        connections.put(channel.id().asShortText(), channel);
    }

    public void removeConnection(Channel channel) {
        connections.remove(channel.id().asShortText());
    }

    public void broadcast(String message) {
        connections.values().stream().forEach(channel -> channel.writeAndFlush(new TextWebSocketFrame(message)));
    }
}
