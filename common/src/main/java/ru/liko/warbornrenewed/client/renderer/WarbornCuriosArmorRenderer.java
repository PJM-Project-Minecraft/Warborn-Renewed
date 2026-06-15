package ru.liko.warbornrenewed.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import ru.liko.warbornrenewed.content.armorset.ArmorBonesSpec;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorModel;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;

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
    public void actuallyRender(PoseStack poseStack, WarbornArmorItem animatable, BakedGeoModel model,
            RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
            boolean isReRender, float partialTick, int packedLight, int packedOverlay,
            int colour) {

        ItemStack stack = this.currentStack;
        if (stack != null && stack.has(ru.liko.warbornrenewed.registry.ModDataComponents.ARMOR_COLOR.get())) {
            Integer customColor = stack.get(ru.liko.warbornrenewed.registry.ModDataComponents.ARMOR_COLOR.get());
            if (customColor != null) {
                int alpha = colour & 0xFF000000;
                colour = alpha | (customColor & 0x00FFFFFF);
            }
        }

        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, colour);
    }
}
