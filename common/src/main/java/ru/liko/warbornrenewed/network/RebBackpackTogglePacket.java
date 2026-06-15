package ru.liko.warbornrenewed.network;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.item.RebBackpackItem;
import top.theillusivec4.curios.api.CuriosApi;

/**
 * Клиент -> Сервер: переключение состояния РЭБ на надетом рюкзаке.
 */
public record RebBackpackTogglePacket(boolean enabled) implements CustomPacketPayload {

    public static final Type<RebBackpackTogglePacket> TYPE = new Type<>(Warbornrenewed.id("reb_toggle"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RebBackpackTogglePacket> STREAM_CODEC = StreamCodec
            .composite(
                    ByteBufCodecs.BOOL, RebBackpackTogglePacket::enabled,
                    RebBackpackTogglePacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RebBackpackTogglePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer sender))
                return;

            if (!ModList.get().isLoaded("curios")) {
                return;
            }

            // Ищем надетый рюкзак РЭБ в Curios backpack
            CuriosApi.getCuriosInventory(sender).ifPresent(handler -> {
                handler.findFirstCurio(itemStack -> itemStack.getItem() instanceof RebBackpackItem)
                        .ifPresent(slotResult -> {
                            ItemStack stack = slotResult.stack();
                            RebBackpackItem.setRebEnabled(stack, packet.enabled());

                            // Force sync by setting the stack again into the slot
                            handler.setEquippedCurio(slotResult.slotContext().identifier(),
                                    slotResult.slotContext().index(), stack);
                        });
            });

            // Проигрываем звуки WRBDrones (если мод установлен)
            playWrbDronesToggleSound(sender, packet.enabled());
        });
    }

    private static void playWrbDronesToggleSound(ServerPlayer player, boolean enabled) {
        if (!ModList.get().isLoaded("wrbdrones")) {
            return;
        }

        ResourceLocation soundId = enabled
                ? ResourceLocation.fromNamespaceAndPath("wrbdrones", "reb_toggle_on")
                : ResourceLocation.fromNamespaceAndPath("wrbdrones", "reb_toggle_off");

        SoundEvent sound = BuiltInRegistries.SOUND_EVENT.get(soundId);
        if (sound == null) {
            return;
        }

        player.level().playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                sound,
                SoundSource.BLOCKS,
                1.0F,
                1.0F);
    }
}
