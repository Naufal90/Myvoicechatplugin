package com.naufal90.voicechat;

import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.*;
import de.maxhenkel.voicechat.api.audio.*;
import de.maxhenkel.voicechat.api.Position;
import de.maxhenkel.voicechat.api.players.Player;
import de.maxhenkel.voicechat.api.players.VoicechatPlayer;
import de.maxhenkel.voicechat.api.events.Listener;
import java.util.UUID;

public class VoiceChatHandler {

    private final VoicechatServerApi api;

    public VoiceChatHandler(VoicechatServerApi api) {
        this.api = api;
    }

    @Listener
    public void onPlayerConnect(PlayerConnectedEvent event) {
        VoicechatPlayer player = event.getVoicechatPlayer();
        UUID playerId = player.getUuid();
        System.out.println("Player connected: " + playerId);
    }

    @Listener
    public void onPlayerDisconnect(PlayerDisconnectedEvent event) {
        VoicechatPlayer player = event.getVoicechatPlayer();
        UUID playerId = player.getUuid();
        System.out.println("Player disconnected: " + playerId);
    }

    @Listener
    public void onMicrophonePacket(MicrophonePacketEvent event) {
        VoicechatPlayer sender = event.getSender();
        UUID senderId = sender.getUuid();
        System.out.println("Microphone packet received from: " + senderId);
    }

    public LocationalAudioChannel createProximityChannel(UUID playerId, Position pos) {
        return api.createLocationalAudioChannel(playerId, pos);
    }

    public GroupAudioChannel createGroupChannel(UUID groupId, String name) {
        return api.createGroupAudioChannel(groupId, name);
    }

    public void mutePlayer(UUID playerId) {
        PlayerAudioChannel channel = api.getPlayerAudioChannel(playerId);
        if (channel != null) {
            channel.setMuted(true);
        }
    }

    public void unmutePlayer(UUID playerId) {
        PlayerAudioChannel channel = api.getPlayerAudioChannel(playerId);
        if (channel != null) {
            channel.setMuted(false);
        }
    }
}
