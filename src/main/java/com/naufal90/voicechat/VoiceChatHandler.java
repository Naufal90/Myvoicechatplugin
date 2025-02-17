package com.naufal90.voicechat;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.*;
import de.maxhenkel.voicechat.api.position.Position;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.function.Consumer;

public class VoiceChatHandler implements EventRegistration {

    private final VoicechatApi api;

    public VoiceChatHandler(VoicechatApi api) {
        this.api = api;
    }

    @Override
    public <T extends Event> void registerEvent(Class<T> eventClass, Consumer<T> eventConsumer, int priority) {
        api.registerEvent(eventClass, eventConsumer);
    }

    public void registerEvents() {
        api.registerEvent(PlayerConnectedEvent.class, this::onPlayerConnected);
        api.registerEvent(PlayerDisconnectedEvent.class, this::onPlayerDisconnected);
        api.registerEvent(MicrophonePacketEvent.class, this::onVoicePacket);
    }

    private void onPlayerConnected(PlayerConnectedEvent event) {
        UUID playerId = event.getConnection().getPlayer().getUuid();
        System.out.println("Player connected: " + playerId);
    }

    private void onPlayerDisconnected(PlayerDisconnectedEvent event) {
        UUID playerId = event.getConnection().getPlayer().getUuid();
        System.out.println("Player disconnected: " + playerId);
    }

    private void onVoicePacket(MicrophonePacketEvent event) {
        UUID playerId = event.getSender().getUuid();
        System.out.println("Voice packet received from: " + playerId);
    }

    public void createVoiceChannel(UUID playerId, ServerLevel serverLevel, double x, double y, double z) {
        LocationalAudioChannel channel = api.createLocationalAudioChannel(playerId, serverLevel, new Position(x, y, z));
        System.out.println("Created voice channel for: " + playerId);
    }
}
