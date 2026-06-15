package ru.liko.warbornrenewed.sound;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import ru.liko.warbornrenewed.registry.ModSoundEvents;

/**
 * Utility for playing mod-specific sound effects, handling both client and server contexts.
 */
public final class WarbornSoundPlayer {

    private static final float DEFAULT_VOLUME = 1.0F;

    private WarbornSoundPlayer() {
    }

    public static void playArmorEquip(Player player, EquipmentSlot slot) {
        SoundEvent sound = slot == EquipmentSlot.HEAD
            ? ModSoundEvents.ARMOR_HELMET_EQUIP.get()
            : ModSoundEvents.ARMOR_BODY_EQUIP.get();
        play(player, sound, 1.0F);
    }

    public static void playArmorUnequip(Player player, EquipmentSlot slot) {
        SoundEvent sound = slot == EquipmentSlot.HEAD
            ? ModSoundEvents.ARMOR_HELMET_UNEQUIP.get()
            : ModSoundEvents.ARMOR_BODY_UNEQUIP.get();
        play(player, sound, 1.0F);
    }

    public static void playVisionToggle(Player player, ItemStack helmetStack, boolean activated) {
        if (!(helmetStack.getItem() instanceof WarbornArmorItem armorItem)) {
            return;
        }

        boolean thermalOnly = armorItem.hasVisionCapability(WarbornArmorItem.TAG_THERMAL)
            && !armorItem.hasVisionCapability(WarbornArmorItem.TAG_NVG);

        SoundEvent sound = thermalOnly
            ? (activated
                ? ModSoundEvents.VISION_THERMAL_ENABLE.get()
                : ModSoundEvents.VISION_THERMAL_DISABLE.get())
            : (activated
                ? ModSoundEvents.VISION_NVG_ENABLE.get()
                : ModSoundEvents.VISION_NVG_DISABLE.get());

        play(player, sound, activated ? 0.95F : 1.05F);
    }

    private static void play(Player player, SoundEvent sound, float pitch) {
        if (sound == null) {
            return;
        }

        if (player.level().isClientSide()) {
            player.playSound(sound, DEFAULT_VOLUME, pitch);
        } else {
            player.level().playSound(
                null,
                player.getX(),
                player.getY() + player.getEyeHeight(),
                player.getZ(),
                sound,
                SoundSource.PLAYERS,
                DEFAULT_VOLUME,
                pitch
            );
        }
    }
}
