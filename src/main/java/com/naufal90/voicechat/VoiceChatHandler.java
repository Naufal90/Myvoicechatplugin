package com.naufal90.voicechat;

import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.*;
import de.maxhenkel.voicechat.api.audio.GroupAudioChannel;
import de.maxhenkel.voicechat.api.audio.LocationalAudioChannel;
import de.maxhenkel.voicechat.api.audio.PlayerAudioChannel;
import de.maxhenkel.voicechat.api.audio.Position;
import org.bukkit.Bukkit;

import java.util.UUID;

public class VoiceChatHandler implements VoicechatServerEventListener {
    private final VoicechatServerApi api;

    public VoiceChatHandler(VoicechatServerApi api) {
        this.api = api;
    }

    @EventListener
    public void onPlayerConnected(PlayerConnectedEvent event) {
        UUID playerId = event.getPlayer().getUuid();
        Bukkit.getLogger().info(playerId + " terhubung ke voice chat.");
    }

    @EventListener
    public void onPlayerDisconnected(PlayerDisconnectedEvent event) {
        UUID playerId = event.getPlayer().getUuid();
        Bukkit.getLogger().info(playerId + " terputus dari voice chat.");
    }

    @EventListener
    public void onVoicePacket(MicrophonePacketEvent event) {
        UUID playerId = event.getSender().getUuid();
        Bukkit.getLogger().info("Suara diterima dari: " + playerId);
    }

    public LocationalAudioChannel createProximityChannel(UUID playerId, Position pos) {
        return api.createLocationalAudioChannel(playerId, pos);
    }

    public GroupAudioChannel createGroupChannel(UUID groupId, String name) {
        return api.createGroupAudioChannel(groupId, name);
    }

    public void mutePlayer(UUID playerId, boolean mute) {
        PlayerAudioChannel channel = api.getPlayerAudioChannel(playerId);
        if (channel != null) {
            channel.setMuted(mute);
        }
    }
}
