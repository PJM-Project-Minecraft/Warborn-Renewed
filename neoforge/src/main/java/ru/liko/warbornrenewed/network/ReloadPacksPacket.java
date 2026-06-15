package ru.liko.warbornrenewed.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import ru.liko.warbornrenewed.Warbornrenewed;

public record ReloadPacksPacket() implements CustomPacketPayload {
    public static final Type<ReloadPacksPacket> TYPE = new Type<>(Warbornrenewed.id("reload_packs"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ReloadPacksPacket> STREAM_CODEC = StreamCodec.unit(new ReloadPacksPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ReloadPacksPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.flow().isClientbound()) {
                ru.liko.warbornrenewed.client.ClientPacksReloader.reloadClient();
            }
        });
    }
}