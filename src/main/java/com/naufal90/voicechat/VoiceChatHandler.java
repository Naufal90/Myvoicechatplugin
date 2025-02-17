package com.naufal90.voicechat;

import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.Listener;
import de.maxhenkel.voicechat.api.player.Player;
import de.maxhenkel.voicechat.api.audio.PlayerAudioChannel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Main extends JavaPlugin {

    private VoicechatServerApi voicechatApi;

    @Override
    public void onEnable() {
        // Menyambungkan ke API VoiceChat Server
        voicechatApi = Bukkit.getServicesManager().getRegistration(VoicechatServerApi.class).getProvider();

        if (voicechatApi != null) {
            getLogger().info("SimpleVoiceChat API berhasil terhubung.");
        } else {
            getLogger().severe("Gagal terhubung ke SimpleVoiceChat API.");
        }

        // Mendaftarkan listener
        Bukkit.getPluginManager().registerEvents(new VoiceChatHandler(), this);
    }

    @Override
    public void onDisable() {
        // Menutup koneksi jika ada
        if (voicechatApi != null) {
            voicechatApi.getEventRegistry().unregisterAll();
        }
    }

    @Listener
    public void onPlayerConnected(de.maxhenkel.voicechat.api.events.PlayerConnectedEvent event) {
        VoicechatPlayer player = event.getVoicechatPlayer();
        // Menangani logika player yang terhubung
    }

    @Listener
    public void onPlayerDisconnected(de.maxhenkel.voicechat.api.events.PlayerDisconnectedEvent event) {
        VoicechatPlayer player = event.getVoicechatPlayer();
        // Menangani logika player yang terputus
    }

    @Listener
    public void onMicrophonePacketReceived(de.maxhenkel.voicechat.api.events.MicrophonePacketEvent event) {
        // Menangani data microphone
    }

    public PlayerAudioChannel createPlayerAudioChannel(UUID playerId) {
        // Menggunakan API untuk membuat audio channel pemain
        return voicechatApi.getPlayerAudioChannel(playerId);
    }

    public void createLocationalAudioChannel(UUID playerId, org.bukkit.util.Vector pos) {
        // Membuat audio channel berdasarkan lokasi
        voicechatApi.createLocationalAudioChannel(playerId, pos);
    }

    public void createGroupAudioChannel(UUID groupId, String name) {
        // Membuat audio channel untuk grup
        voicechatApi.createGroupAudioChannel(groupId, name);
    }
}
