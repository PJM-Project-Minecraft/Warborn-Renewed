package ru.liko.warbornrenewed.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorModel;

public class WarbornCuriosArmorRenderer extends GeoArmorRenderer<WarbornArmorItem> {
    public WarbornCuriosArmorRenderer(WarbornArmorItem item) {
        super(new WarbornArmorModel(item.getVisuals()));
        item.getBones().apply(this);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, WarbornArmorItem animatable, BakedGeoModel model, RenderType renderType,
                             MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
                             int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
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


