package ru.liko.warbornrenewed.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import ru.liko.warbornrenewed.sound.WarbornSoundPlayer;

/**
 * Packet to toggle the vision attachment state (NVG/Thermal) for supported
 * helmets.
 */
public record NVGTogglePacket(boolean nvgDown) implements CustomPacketPayload {

    public static final Type<NVGTogglePacket> TYPE = new Type<>(Warbornrenewed.id("nvg_toggle"));

    public static final StreamCodec<RegistryFriendlyByteBuf, NVGTogglePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, NVGTogglePacket::nvgDown,
            NVGTogglePacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(NVGTogglePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                // Check helmet slot for supported vision capability
                ItemStack helmet = player.getInventory().getArmor(3); // Helmet slot

                if (!helmet.isEmpty() && helmet.getItem() instanceof WarbornArmorItem armorItem) {
                    boolean supportsNVG = armorItem.hasVisionCapability(WarbornArmorItem.TAG_NVG);
                    boolean supportsThermal = armorItem.hasVisionCapability(WarbornArmorItem.TAG_THERMAL);

                    if (supportsNVG || supportsThermal) {
                        // Update vision state on server
                        WarbornArmorItem.setNVGDown(helmet, packet.nvgDown());

                        // Force sync to tracking clients by re-setting the slot
                        player.getInventory().setChanged();
                        player.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, helmet);

                        // Broadcast toggle sound to nearby players
                        WarbornSoundPlayer.playVisionToggle(player, helmet, packet.nvgDown());
                    }
                }
            }
        });
    }
}
