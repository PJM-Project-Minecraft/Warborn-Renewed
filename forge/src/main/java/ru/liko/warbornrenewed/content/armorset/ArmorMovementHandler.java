package ru.liko.warbornrenewed.content.armorset;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import ru.liko.warbornrenewed.WarbornRenewed;

import java.util.UUID;

/**
 * Applies movement speed penalties/bonuses from armor to the vanilla movement
 * speed attribute.
 *
 * We keep using the custom attribute (armor_movement_speed) on items for clean
 * data separation and tooltips,
 * then translate the summed value into a single modifier on
 * Attributes.MOVEMENT_SPEED so it actually affects gameplay.
 */
@EventBusSubscriber(modid = WarbornRenewed.MODID)
public final class ArmorMovementHandler {
    // Stable UUID to identify the modifier
    private static final UUID ARMOR_SPEED_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    private static final String ARMOR_SPEED_NAME = "warbornrenewed:armor_movement_speed";

    private ArmorMovementHandler() {
    }

    @SubscribeEvent
    public static void onEntityTick(PlayerTickEvent event) {
        if (event.phase != net.minecraftforge.event.TickEvent.Phase.START) {
            return;
        }
        LivingEntity entity = event.player;
        if (entity.level().isClientSide) {
            // Only run on server to avoid double-applying; attribute is synced to client
            return;
        }

        // Sum movement speed modifiers from worn armor items.
        double totalSpeedModifier = 0.0D; // e.g., -0.12 for -12%

        for (EquipmentSlot slot : new EquipmentSlot[] { EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS,
                EquipmentSlot.FEET }) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (stack.isEmpty() || !(stack.getItem() instanceof WarbornArmorItem armorItem))
                continue;

            // Find the armor set for this item and sum its movement speed attribute
            for (WarbornArmorSet armorSet : WarbornArmorRegistry.registeredSets()) {
                for (var deferred : armorSet.pieces()) {
                    if (deferred.get() == armorItem) {
                        // Movement speed is now handled via the item's default attribute modifiers
                        // This handler can be used for additional dynamic calculations if needed
                        break;
                    }
                }
            }
        }

        // Apply to vanilla movement speed as a MULTIPLY_BASE modifier
        AttributeInstance movement = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movement == null)
            return;

        // Remove previous modifier
        AttributeModifier existing = movement.getModifier(ARMOR_SPEED_UUID);
        if (existing != null) {
            movement.removeModifier(ARMOR_SPEED_UUID);
        }

        // Only apply if non-zero to avoid clutter
        if (Math.abs(totalSpeedModifier) > 1.0E-6) {
            // Negative value slows down, positive speeds up
            AttributeModifier applied = new AttributeModifier(
                    ARMOR_SPEED_UUID,
                    ARMOR_SPEED_NAME,
                    totalSpeedModifier,
                    AttributeModifier.Operation.MULTIPLY_BASE);
            movement.addPermanentModifier(applied);
        }
    }
}
