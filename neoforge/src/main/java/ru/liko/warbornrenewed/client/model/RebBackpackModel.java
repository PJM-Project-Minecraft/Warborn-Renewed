package ru.liko.warbornrenewed.client.model;

import net.minecraft.resources.ResourceLocation;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.item.RebBackpackItem;
import software.bernie.geckolib.model.GeoModel;

/**
 * GeckoLib модель для рюкзака РЭБ.
 * Модель рендерится на спине игрока через Curios слот backpack.
 */
public class RebBackpackModel extends GeoModel<RebBackpackItem> {
    
    private static final ResourceLocation MODEL = Warbornrenewed.id("geo/reb-backpack.geo.json");
    private static final ResourceLocation ANIMATION = null; // Пока без анимаций
    
    private final ResourceLocation texture;

    public RebBackpackModel(RebBackpackItem item) {
        this.texture = item.getTextureLocation();
    }

    public RebBackpackModel(String variantId) {
        this.texture = Warbornrenewed.id("textures/reb-backpack-" + variantId + ".png");
    }

    @Override
    public ResourceLocation getModelResource(RebBackpackItem animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(RebBackpackItem animatable) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(RebBackpackItem animatable) {
        return ANIMATION;
    }
    
    /**
     * Получить путь к модели (статический)
     */
    public static ResourceLocation getModelPath() {
        return MODEL;
    }
}

