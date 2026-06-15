package ru.liko.warbornrenewed.client.event;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.client.renderer.WarbornCuriosArmorLayer;

/**
 * Регистрация клиентских рендер-слоев
 * По образцу ClientModEvents из WARBORN-1.20.1
 */
@EventBusSubscriber(modid = Warbornrenewed.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

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
