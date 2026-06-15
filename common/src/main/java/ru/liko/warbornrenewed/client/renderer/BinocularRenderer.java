package ru.liko.warbornrenewed.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import ru.liko.warbornrenewed.client.model.BinocularModel;
import ru.liko.warbornrenewed.content.item.BinocularItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BinocularRenderer extends GeoItemRenderer<BinocularItem> {

    public BinocularRenderer() {
        super(new BinocularModel());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack,
                             MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        
        // Скрываем модель в first person когда игрок использует бинокль (зумит)
        if (transformType.firstPerson()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.player.isUsingItem()) {
                ItemStack useItem = mc.player.getUseItem();
                if (useItem.getItem() instanceof BinocularItem) {
                    // Не рендерим модель - игрок смотрит через бинокль
                    return;
                }
            }
        }
        
        super.renderByItem(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
    }
}
