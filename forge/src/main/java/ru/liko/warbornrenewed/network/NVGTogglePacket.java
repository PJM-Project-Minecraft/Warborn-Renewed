package ru.liko.warbornrenewed.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import ru.liko.warbornrenewed.sound.WarbornSoundPlayer;

import java.util.function.Supplier;

public class NVGTogglePacket {
    private final boolean nvgDown;

    public NVGTogglePacket(boolean nvgDown) {
        this.nvgDown = nvgDown;
    }

    public NVGTogglePacket(FriendlyByteBuf buf) {
        this.nvgDown = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.nvgDown);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                ItemStack helmet = player.getInventory().getArmor(3);
                if (!helmet.isEmpty() && helmet.getItem() instanceof WarbornArmorItem armorItem) {
                    boolean supportsNVG = armorItem.hasVisionCapability(WarbornArmorItem.TAG_NVG);
                    boolean supportsThermal = armorItem.hasVisionCapability(WarbornArmorItem.TAG_THERMAL);
                    if (supportsNVG || supportsThermal) {
                        WarbornArmorItem.setNVGDown(helmet, this.nvgDown);
                        player.getInventory().setChanged();
                        player.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, helmet);
                        WarbornSoundPlayer.playVisionToggle(player, helmet, this.nvgDown);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
