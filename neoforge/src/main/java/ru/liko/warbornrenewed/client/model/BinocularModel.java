package ru.liko.warbornrenewed.client.model;

import net.minecraft.resources.ResourceLocation;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.item.BinocularItem;
import software.bernie.geckolib.model.GeoModel;

public class BinocularModel extends GeoModel<BinocularItem> {

    private static final ResourceLocation MODEL = Warbornrenewed.id("geo/item/binocular.geo.json");
    private static final ResourceLocation TEXTURE = Warbornrenewed.id("textures/binocol.png");

    @Override
    public ResourceLocation getModelResource(BinocularItem item) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(BinocularItem item) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(BinocularItem item) {
        return null; // Без анимаций
    }
}

