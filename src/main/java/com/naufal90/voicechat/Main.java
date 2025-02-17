package com.naufal90.voicechat;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private VoicechatServerApi voicechatApi;
    private VoiceChatHandler voiceChatHandler;

    @Override
    public void onEnable() {
        BukkitVoicechatService voicechatService = (BukkitVoicechatService) getServer().getServicesManager().getRegistration(BukkitVoicechatService.class).getProvider();
        if (voicechatService != null) {
            voicechatApi = voicechatService.voicechatServerApi();
            voiceChatHandler = new VoiceChatHandler(voicechatApi);
            voiceChatHandler.registerEvents();
            getLogger().info("VoiceChat plugin enabled.");
        } else {
            getLogger().warning("Failed to load VoiceChat API.");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("VoiceChat plugin disabled.");
    }
}
