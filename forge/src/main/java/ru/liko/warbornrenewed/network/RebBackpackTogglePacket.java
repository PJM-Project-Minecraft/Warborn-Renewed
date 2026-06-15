package ru.liko.warbornrenewed.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import ru.liko.warbornrenewed.content.item.RebBackpackItem;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

public class RebBackpackTogglePacket {
    private final boolean enabled;

    public RebBackpackTogglePacket(boolean enabled) {
        this.enabled = enabled;
    }

    public RebBackpackTogglePacket(FriendlyByteBuf buf) {
        this.enabled = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.enabled);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            if (sender == null) return;

            if (!ModList.get().isLoaded("curios")) {
                return;
            }

            CuriosApi.getCuriosInventory(sender).ifPresent(handler -> {
                handler.findFirstCurio(itemStack -> itemStack.getItem() instanceof RebBackpackItem)
                        .ifPresent(slotResult -> {
                            ItemStack stack = slotResult.stack();
                            RebBackpackItem.setRebEnabled(stack, this.enabled);
                            handler.setEquippedCurio(slotResult.slotContext().identifier(),
                                    slotResult.slotContext().index(), stack);
                        });
            });

            playWrbDronesToggleSound(sender, this.enabled);
        });
        ctx.get().setPacketHandled(true);
    }

    private void playWrbDronesToggleSound(ServerPlayer player, boolean enabled) {
        if (!ModList.get().isLoaded("wrbdrones")) {
            return;
        }

        ResourceLocation soundId = new ResourceLocation("wrbdrones", enabled ? "reb_toggle_on" : "reb_toggle_off");
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(soundId);
        
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
