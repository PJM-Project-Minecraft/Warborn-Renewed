package ru.liko.warbornrenewed.content.armorset;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import ru.liko.warbornrenewed.Warbornrenewed;

/**
 * Applies movement speed penalties/bonuses from armor to the vanilla movement
 * speed attribute.
 *
 * We keep using the custom attribute (armor_movement_speed) on items for clean
 * data separation and tooltips,
 * then translate the summed value into a single modifier on
 * Attributes.MOVEMENT_SPEED so it actually affects gameplay.
 */
@EventBusSubscriber(modid = Warbornrenewed.MODID)
public final class ArmorMovementHandler {
    // Stable ResourceLocation to identify the modifier
    private static final ResourceLocation ARMOR_SPEED_ID = Warbornrenewed.id("armor_movement_speed");

    private ArmorMovementHandler() {
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }
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
        AttributeModifier existing = movement.getModifier(ARMOR_SPEED_ID);
        if (existing != null) {
            movement.removeModifier(ARMOR_SPEED_ID);
        }

        // Only apply if non-zero to avoid clutter
        if (Math.abs(totalSpeedModifier) > 1.0E-6) {
            // Negative value slows down, positive speeds up
            AttributeModifier applied = new AttributeModifier(
                    ARMOR_SPEED_ID,
                    totalSpeedModifier,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
            movement.addPermanentModifier(applied);
        }
    }
}
