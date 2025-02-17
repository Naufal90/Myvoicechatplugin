package com.naufal90.voicechat;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Optional;

public class Main extends JavaPlugin implements VoicechatPlugin {

    private VoicechatServerApi voicechatApi;
    private VoiceChatHandler voiceChatHandler;

    @Override
    public String getPluginId() {
        return "myvoicechatplugin";  // Sesuaikan dengan ID plugin di `plugin.yml`
    }

    @Override
    public void onEnable() {
        Optional<BukkitVoicechatService> service = getServer().getServicesManager()
                .load(BukkitVoicechatService.class);

        if (service.isPresent()) {
            voicechatApi = service.get().getVoicechatServerApi(); // Menggunakan metode yang benar
            voiceChatHandler = new VoiceChatHandler(voicechatApi);
            voicechatApi.getEventRegistry().register(this, voiceChatHandler);
            getLogger().info("Voice chat plugin loaded successfully.");
        } else {
            getLogger().severe("Failed to load BukkitVoicechatService!");
        }
    }
}
