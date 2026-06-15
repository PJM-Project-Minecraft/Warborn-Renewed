package ru.liko.warbornrenewed.client.renderer;

import net.minecraft.world.item.ItemStack;
import ru.liko.warbornrenewed.content.armorset.ArmorBonesSpec;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

/**
 * Простой рендерер для отображения брони Warborn через Curios
 */
public class WarbornCuriosArmorRenderer extends GeoArmorRenderer<WarbornArmorItem> {

    public WarbornCuriosArmorRenderer(WarbornArmorItem item) {
        super(new WarbornArmorModel(item.getVisuals()));
        // Применяем правильные кости на основе типа брони
        ArmorBonesSpec bones = item.getBones();
        if (bones != null) {
            bones.apply(this);
        } else {
            // Если кости не заданы, используем значения по умолчанию
            ArmorBonesSpec.defaults(item.getType()).apply(this);
        }
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
