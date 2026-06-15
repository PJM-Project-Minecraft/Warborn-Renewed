package ru.liko.warbornrenewed.client.event;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.client.renderer.WarbornCuriosArmorLayer;
import ru.liko.warbornrenewed.registry.ModDataComponents;
import ru.liko.warbornrenewed.registry.ModItems;

/**
 * Регистрация клиентских рендер-слоев
 * По образцу ClientModEvents из WARBORN-1.20.1
 */
@EventBusSubscriber(modid = Warbornrenewed.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        ItemColor warbornArmorTint = (stack, tintIndex) -> {
            if (tintIndex != 0) {
                return 0xFFFFFFFF;
            }
            if (stack.has(ModDataComponents.ARMOR_COLOR.get())) {
                Integer c = stack.get(ModDataComponents.ARMOR_COLOR.get());
                if (c != null) {
                    return 0xFF000000 | (c & 0x00FFFFFF);
                }
            }
            return 0xFFFFFFFF;
        };
        var pieces = ModItems.armorPieces();
        java.util.List<Item> armorList = new java.util.ArrayList<>();
        pieces.forEach(ref -> armorList.add(ref.get()));
        armorList.add(ModItems.PACK_HELMET.get());
        armorList.add(ModItems.PACK_CHESTPLATE.get());
        armorList.add(ModItems.PACK_LEGGINGS.get());
        armorList.add(ModItems.PACK_BOOTS.get());

        if (!armorList.isEmpty()) {
            Item[] items = armorList.toArray(Item[]::new);
            event.register(warbornArmorTint, items);
        }
    }

    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.AddLayers event) {
        if (event == null) {
            return;
        }

        // В 1.21.1 getSkins() возвращает Set<PlayerSkin.Model>, но мы используем
        // строковые ID
        // Добавляем слой для стандартных моделей игрока
        for (var model : event.getSkins()) {
            PlayerRenderer playerRenderer = event.getSkin(model);
            if (playerRenderer != null) {
                playerRenderer.addLayer(new WarbornCuriosArmorLayer<>(playerRenderer));
            }
        }
    }
}
