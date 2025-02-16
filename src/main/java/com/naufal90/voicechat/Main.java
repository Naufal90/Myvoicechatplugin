package com.naufal90.voicechat;

import org.bukkit.plugin.java.JavaPlugin;
import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;

public class Main extends JavaPlugin implements VoicechatPlugin {

    private VoicechatServerApi voicechatApi;
    private WebSocketServer webSocketServer;
    private VoiceChatHandler voiceChatHandler;


 @Override
public void onEnable() {
    getLogger().info("MyVoiceChatPlugin is enabled!");
    
    saveDefaultConfig(); // Membuat config.yml jika belum ada

    int webSocketPort = getConfig().getInt("websocket-port", 24454);
    getLogger().info("Using WebSocket port: " + webSocketPort);

    BukkitVoicechatService voicechatService = getServer().getServicesManager()
            .load(BukkitVoicechatService.class);

    if (voicechatService != null) {
        voicechatApi = voicechatService.getApi();
        getLogger().info("VoiceChat API connected!");

        voiceChatHandler = new VoiceChatHandler(voicechatApi);
        voicechatApi.getEventBus().register(this, voiceChatHandler);
    } else {
        getLogger().severe("Failed to connect to VoiceChat API!");
        getServer().getPluginManager().disablePlugin(this);
        return;
    }

    webSocketServer = new WebSocketServer(webSocketPort);
    webSocketServer.start();
}
    
    @Override
    public void onDisable() {
        if (webSocketServer != null) {
            webSocketServer.stopServer();
        }
        getLogger().info("MyVoiceChatPlugin is disabled!");
    }

    @Override
    public String getPluginId() {
        return "myvoicechatplugin";
    }
}
