package ru.liko.warbornrenewed.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReloadPacksPacket {

    public ReloadPacksPacket() {
    }

    public ReloadPacksPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide().isClient()) {
                ru.liko.warbornrenewed.client.ClientPacksReloader.reloadClient();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}