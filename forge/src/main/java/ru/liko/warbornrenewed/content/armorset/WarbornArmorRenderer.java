package ru.liko.warbornrenewed.content.armorset;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
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
            if (stack.hasTag() && stack.getTag().contains(WarbornArmorItem.TAG_VARIANT)) {
                String variant = stack.getTag().getString(WarbornArmorItem.TAG_VARIANT);
                if (!variant.isEmpty() && visuals.variants().containsKey(variant)) {
                    return visuals.variants().get(variant);
                }
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
    public void actuallyRender(PoseStack poseStack, WarbornArmorItem animatable, BakedGeoModel model,
            RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
            boolean isReRender, float partialTick, int packedLight, int packedOverlay,
            float red, float green, float blue, float alpha) {
        ItemStack stack = this.currentStack;
        if (stack != null && ru.liko.warbornrenewed.platform.Services.ITEM_DATA.hasArmorColor(stack)) {
            int color = ru.liko.warbornrenewed.platform.Services.ITEM_DATA.getArmorColor(stack);
            red = ((color >> 16) & 0xFF) / 255.0F;
            green = ((color >> 8) & 0xFF) / 255.0F;
            blue = (color & 0xFF) / 255.0F;
        }
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, red, green, blue, alpha);
    }
}
