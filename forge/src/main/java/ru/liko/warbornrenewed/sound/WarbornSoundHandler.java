package ru.liko.warbornrenewed.sound;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import ru.liko.warbornrenewed.WarbornRenewed;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;

/**
 * Global NeoForge event subscriber that reacts to armor changes and dispatches
 * the appropriate sounds.
 */
@EventBusSubscriber(modid = WarbornRenewed.MODID)
public final class WarbornSoundHandler {

    private WarbornSoundHandler() {
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (player.level().isClientSide()) {
            return;
        }

        // Skip the initial equipment sync when the player (re)joins the world
        if (player.tickCount < 20) {
            return;
        }

        EquipmentSlot slot = event.getSlot();
        if (!slot.isArmor()) {
            return;
        }

        ItemStack previous = event.getFrom();
        ItemStack current = event.getTo();

        // Проверяем, действительно ли изменился предмет (надевание/снятие),
        // а не просто изменилось состояние (прочность/износ)
        boolean previousEmpty = previous.isEmpty();
        boolean currentEmpty = current.isEmpty();
        boolean itemChanged = previousEmpty != currentEmpty || !ItemStack.isSameItem(previous, current);

        // Если предмет не изменился, это просто изменение состояния (прочность и т.д.)
        // и мы не должны воспроизводить звук
        if (!itemChanged) {
            return;
        }

        if (isWarbornArmor(previous)) {
            WarbornSoundPlayer.playArmorUnequip(player, slot);
        }

        if (isWarbornArmor(current)) {
            WarbornSoundPlayer.playArmorEquip(player, slot);
        }
    }

    private static boolean isWarbornArmor(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof WarbornArmorItem;
    }
}
