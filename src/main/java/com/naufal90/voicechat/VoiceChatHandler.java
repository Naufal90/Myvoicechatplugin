package com.naufal90.voicechat;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VoiceChatHandler implements EventRegistration {

    private final VoicechatServerApi voicechatApi;
    private final Map<UUID, Boolean> muteStatus;
    private final Map<UUID, String> groupStatus;

    public VoiceChatHandler(VoicechatServerApi voicechatApi) {
        this.voicechatApi = voicechatApi;
        this.muteStatus = new HashMap<>();
        this.groupStatus = new HashMap<>();
    }

    @Override
public <T extends Event> void registerEvent(Class<T> eventClass, Consumer<T> eventConsumer, int priority) {
    // Implementasi kosong atau sesuai kebutuhan
}
    
    @Override
    public void registerEvents(VoicechatApi api) {
        voicechatApi.registerEvent(PlayerConnectedEvent.class, this::onPlayerConnected);
        voicechatApi.registerEvent(PlayerDisconnectedEvent.class, this::onPlayerDisconnected);
        voicechatApi.registerEvent(MicrophonePacketEvent.class, this::onVoicePacket);
    }
      
    private void onPlayerConnected(PlayerConnectedEvent event) {
        UUID playerId = event.getPlayer().getUuid();
        Bukkit.getLogger().info("Player connected: " + playerId);
        muteStatus.put(playerId, false);
    }

    private void onPlayerDisconnected(PlayerDisconnectedEvent event) {
        UUID playerId = event.getPlayer().getUuid();
        Bukkit.getLogger().info("Player disconnected: " + playerId);
        muteStatus.remove(playerId);
        groupStatus.remove(playerId);
    }

    private void onVoicePacket(MicrophonePacketEvent event) {
        UUID playerId = event.getSender().getUuid();
        if (muteStatus.getOrDefault(playerId, false)) {
            event.cancel(); // Blokir suara jika pemain dalam keadaan mute
        }
    }

    public void toggleMute(Player player) {
        UUID playerId = player.getUniqueId();
        boolean isMuted = muteStatus.getOrDefault(playerId, false);
        muteStatus.put(playerId, !isMuted);

        player.sendMessage("Voice chat " + (isMuted ? "unmuted" : "muted"));
        Bukkit.getLogger().info(player.getName() + " mute status: " + !isMuted);
    }

    public void assignToGroup(Player player, String groupName) {
        UUID playerId = player.getUniqueId();
        groupStatus.put(playerId, groupName);
        player.sendMessage("You joined the group: " + groupName);
        Bukkit.getLogger().info(player.getName() + " joined group: " + groupName);
    }

    public void enableProximityVoice(Player player) {
        if (voicechatApi == null) return;

        UUID playerId = player.getUniqueId();
        LocationalAudioChannel channel = voicechatApi.createLocationalAudioChannel(playerId, serverLevel, new Position(x, y, z));
        if (channel != null) {
            channel.setPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
            player.sendMessage("Proximity voice enabled.");
            Bukkit.getLogger().info(player.getName() + " enabled proximity voice.");
        }
    }
}
