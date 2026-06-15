package ru.liko.warbornrenewed.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import ru.liko.warbornrenewed.WarbornRenewed;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(WarbornRenewed.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        INSTANCE.registerMessage(packetId++, NVGTogglePacket.class, NVGTogglePacket::encode, NVGTogglePacket::new, NVGTogglePacket::handle);
        INSTANCE.registerMessage(packetId++, RebBackpackTogglePacket.class, RebBackpackTogglePacket::encode, RebBackpackTogglePacket::new, RebBackpackTogglePacket::handle);
        INSTANCE.registerMessage(packetId++, ReloadPacksPacket.class, ReloadPacksPacket::encode, ReloadPacksPacket::new, ReloadPacksPacket::handle);
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToAllClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
