package ru.liko.warbornrenewed.content.armorset;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import javax.annotation.Nullable;

public class WarbornArmorModel extends GeoModel<WarbornArmorItem> {
    private final ArmorVisualSpec visuals;

    public WarbornArmorModel(ArmorVisualSpec visuals) {
        this.visuals = visuals;
    }

    @Override
    @Nullable
    public ResourceLocation getAnimationResource(WarbornArmorItem animatable) {
        // Return null if no animation file is specified
        // This prevents GeckoLib from trying to load non-existent animation files
        return visuals.animation();
    }

    @Override
    public ResourceLocation getModelResource(WarbornArmorItem animatable) {
        return visuals.model();
    }

    @Override
    public ResourceLocation getTextureResource(WarbornArmorItem animatable) {
        return visuals.texture();
    }
}
