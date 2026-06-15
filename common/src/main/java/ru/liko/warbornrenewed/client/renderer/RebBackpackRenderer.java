package ru.liko.warbornrenewed.client.renderer;

import net.minecraft.resources.ResourceLocation;
import ru.liko.warbornrenewed.client.model.RebBackpackModel;
import ru.liko.warbornrenewed.content.item.RebBackpackItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

/**
 * Рендерер для предмета рюкзака РЭБ в инвентаре.
 * Для рендеринга на игроке используется RebBackpackCuriosRenderer.
 */
public class RebBackpackRenderer extends GeoItemRenderer<RebBackpackItem> {

    public RebBackpackRenderer(RebBackpackItem item) {
        super(new RebBackpackModel(item));
    }

    @Override
    public ResourceLocation getTextureLocation(RebBackpackItem animatable) {
        return animatable.getTextureLocation();
    }
}

