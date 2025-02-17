package com.naufal90.voicechat;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;
import java.util.logging.Logger;

public class Main extends JavaPlugin implements VoicechatPlugin {
    private VoicechatServerApi voicechatApi;
    private VoiceChatHandler voiceChatHandler;
    private WebSocketServer webSocketServer;
    private static Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.info("MyVoiceChatPlugin is enabled!");

        Optional<BukkitVoicechatService> service = getServer().getServicesManager()
                .load(BukkitVoicechatService.class);

        if (service.isEmpty()) {
            logger.warning("VoiceChat API tidak ditemukan!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        voicechatApi = service.get().getServerApi();
        voiceChatHandler = new VoiceChatHandler(voicechatApi);

        // Registrasi event
        voicechatApi.getEventRegistry().register(this, voiceChatHandler);

        // Jalankan WebSocket Server
        webSocketServer = new WebSocketServer(24454);
        webSocketServer.start();
        logger.info("WebSocket server berjalan di port 24454!");
    }

    @Override
    public void onDisable() {
        if (webSocketServer != null) {
            webSocketServer.stopServer();
        }
        logger.info("MyVoiceChatPlugin is disabled!");
    }
}
