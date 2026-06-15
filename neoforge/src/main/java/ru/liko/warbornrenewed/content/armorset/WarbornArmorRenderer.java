package ru.liko.warbornrenewed.content.armorset;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import javax.annotation.Nullable;

public class WarbornArmorRenderer extends GeoArmorRenderer<WarbornArmorItem> {
    private final ArmorVisualSpec visuals;

    public WarbornArmorRenderer(ArmorVisualSpec visuals, ArmorBonesSpec bones) {
        super(new WarbornArmorModel(visuals));
        this.visuals = visuals;
        bones.apply(this);
    }

    @Override
    public ResourceLocation getTextureLocation(WarbornArmorItem animatable) {
        ItemStack stack = this.currentStack;
        if (stack != null) {
            // TODO: Check for variant using DataComponents in 1.21.1
            String variant = ""; // Placeholder
            if (!variant.isEmpty() && visuals.variants().containsKey(variant)) {
                return visuals.variants().get(variant);
            }
        }
        return super.getTextureLocation(animatable);
    }

    @Override
    public RenderType getRenderType(WarbornArmorItem animatable, ResourceLocation texture,
            @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void actuallyRender(com.mojang.blaze3d.vertex.PoseStack poseStack, WarbornArmorItem animatable, software.bernie.geckolib.cache.object.BakedGeoModel model,
            net.minecraft.client.renderer.RenderType renderType, net.minecraft.client.renderer.MultiBufferSource bufferSource, com.mojang.blaze3d.vertex.VertexConsumer buffer,
            boolean isReRender, float partialTick, int packedLight, int packedOverlay,
            int colour) {
        ItemStack stack = this.currentStack;
        if (stack != null && ru.liko.warbornrenewed.platform.Services.ITEM_DATA.hasArmorColor(stack)) {
            int customColor = ru.liko.warbornrenewed.platform.Services.ITEM_DATA.getArmorColor(stack);
            int alpha = colour & 0xFF000000;
            colour = alpha | (customColor & 0x00FFFFFF);
        }
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, colour);
    }
}
