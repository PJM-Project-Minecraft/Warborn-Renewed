package ru.liko.warbornrenewed.client.event;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import ru.liko.warbornrenewed.WarbornRenewed;
import ru.liko.warbornrenewed.client.renderer.WarbornCuriosArmorLayer;
import ru.liko.warbornrenewed.registry.ModItems;

/**
 * Регистрация клиентских рендер-слоев и цветов предметов (повязки и т.д.)
 */
@EventBusSubscriber(modid = WarbornRenewed.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        ItemColor dyeableColor = (stack, tintIndex) -> {
            if (tintIndex != 0) {
                return 0xFFFFFFFF;
            }
            if (ru.liko.warbornrenewed.platform.Services.ITEM_DATA.hasArmorColor(stack)) {
                return 0xFF000000 | (ru.liko.warbornrenewed.platform.Services.ITEM_DATA.getArmorColor(stack) & 0x00FFFFFF);
            }
            return 0xFFFFFFFF;
        };
        java.util.List<net.minecraft.world.item.Item> armorList = new java.util.ArrayList<>();
        ModItems.armorPieces().forEach(ref -> armorList.add(ref.get()));
        armorList.add(ModItems.PACK_HELMET.get());
        armorList.add(ModItems.PACK_CHESTPLATE.get());
        armorList.add(ModItems.PACK_LEGGINGS.get());
        armorList.add(ModItems.PACK_BOOTS.get());
        
        net.minecraft.world.item.Item[] dyeableArmor = armorList.toArray(net.minecraft.world.item.Item[]::new);
        if (dyeableArmor.length > 0) {
            event.getItemColors().register(dyeableColor, dyeableArmor);
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
