package ru.liko.warbornrenewed.client.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import ru.liko.warbornrenewed.client.model.RebBackpackModel;
import ru.liko.warbornrenewed.content.item.RebBackpackItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

/**
 * Рендерер рюкзака РЭБ, сделанный по тому же принципу, что и броня:
 * через GeoArmorRenderer + привязка bone к части тела.
 *
 * Мы маппим {@code body} на bone {@code bipedBackpack}, чтобы модель всегда
 * корректно следовала за телом игрока (размер/ориентация как у брони).
 */
public class RebBackpackArmorRenderer extends GeoArmorRenderer<RebBackpackItem> {

    public RebBackpackArmorRenderer(RebBackpackItem item) {
        super(new RebBackpackModel(item));
    }

    @Override
    public ResourceLocation getTextureLocation(RebBackpackItem animatable) {
        return animatable.getTextureLocation();
    }

    @Override
    public RenderType getRenderType(RebBackpackItem animatable, ResourceLocation texture,
            MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
