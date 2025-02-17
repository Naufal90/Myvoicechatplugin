package com.naufal90.voicechat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.WebSocket;

public class WebSocketServer extends org.java_websocket.server.WebSocketServer {
    private Set<WebSocket> connections = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public WebSocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        connections.add(conn);
        Bukkit.getLogger().info("New WebSocket connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        connections.remove(conn);
        Bukkit.getLogger().info("WebSocket closed: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Bukkit.getLogger().info("Received message: " + message);
        for (WebSocket ws : connections) {
            ws.send("Echo: " + message);
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        Bukkit.getLogger().info("WebSocket server started on port " + getPort());
    }

    public void stopServer() {
        try {
            stop();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
