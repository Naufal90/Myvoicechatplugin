package com.naufal90.voicechat;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.WebSocket;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class WebSocketServer extends WebSocketServer {
    private final Set<WebSocket> connections = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private static final Logger logger = Logger.getLogger("WebSocketServer");

    public WebSocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        connections.add(conn);
        logger.info("Client terhubung: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        connections.remove(conn);
        logger.info("Client terputus: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        logger.info("Pesan dari client: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        logger.warning("Terjadi error: " + ex.getMessage());
    }

    @Override
    public void onStart() {
        logger.info("WebSocket Server dimulai.");
    }

    public void stopServer() {
        try {
            this.stop();
        } catch (InterruptedException e) {
            logger.warning("Gagal menghentikan WebSocket Server.");
        }
    }
}
