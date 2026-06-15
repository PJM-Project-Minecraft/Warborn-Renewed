package ru.liko.warbornrenewed.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import ru.liko.warbornrenewed.Warbornrenewed;

/**
 * Network handler for client-server communication using NeoForge Payload system
 */
public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(NetworkHandler::registerPayloads);
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Warbornrenewed.MODID)
                .versioned(PROTOCOL_VERSION);

        registrar.playToServer(
                NVGTogglePacket.TYPE,
                NVGTogglePacket.STREAM_CODEC,
                NVGTogglePacket::handle);

        registrar.playToServer(
                RebBackpackTogglePacket.TYPE,
                RebBackpackTogglePacket.STREAM_CODEC,
                RebBackpackTogglePacket::handle);

        registrar.playToClient(
                ReloadPacksPacket.TYPE,
                ReloadPacksPacket.STREAM_CODEC,
                ReloadPacksPacket::handle);
    }

    public static <MSG extends CustomPacketPayload> void sendToServer(MSG message) {
        PacketDistributor.sendToServer(message);
    }

    public static <MSG extends CustomPacketPayload> void sendToAllClients(MSG message) {
        PacketDistributor.sendToAllPlayers(message);
    }

    public static <MSG extends CustomPacketPayload> void sendToPlayer(MSG message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }
}
