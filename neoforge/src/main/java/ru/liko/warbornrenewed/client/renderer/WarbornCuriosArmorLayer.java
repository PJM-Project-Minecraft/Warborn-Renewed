package ru.liko.warbornrenewed.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import ru.liko.warbornrenewed.content.item.RebBackpackItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

/**
 * Рендер-слой для отображения брони Warborn в Curios слотах
 * ТОЧНАЯ КОПИЯ WarbornUniformLayer из WARBORN-1.20.1
 */
public class WarbornCuriosArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {

    public WarbornCuriosArmorLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    @SuppressWarnings({ "deprecation", "unchecked" })
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T entity,
            float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch) {

        // Используем тот же метод что и в оригинале - findCurios
        // Примечание: метод deprecated, но используется для совместимости
        @SuppressWarnings("removal")
        List<SlotResult> curios = CuriosApi.getCuriosHelper().findCurios(entity,
                stack -> stack.getItem() instanceof WarbornArmorItem);

        for (SlotResult slotResult : curios) {
            if (slotResult.slotContext().visible()) {
                ItemStack itemStack = slotResult.stack();
                WarbornArmorItem armorItem = (WarbornArmorItem) itemStack.getItem();

                try {
                    // Создаем рендерер с правильными костями
                    WarbornCuriosArmorRenderer armorRenderer = new WarbornCuriosArmorRenderer(armorItem);

                    // Копируем свойства из родительской модели
                    this.getParentModel().copyPropertiesTo(armorRenderer);

                    // Определяем правильный слот на основе типа брони
                    EquipmentSlot slot = armorItem.getType().getSlot();

                    // Подготавливаем рендерер
                    armorRenderer.prepForRender(entity, itemStack, slot, this.getParentModel());

                    // В GeckoLib 4.7.1 renderToBuffer имеет другую сигнатуру
                    armorRenderer.renderToBuffer(
                            poseStack,
                            bufferSource.getBuffer(
                                    RenderType.entityTranslucent(armorRenderer.getTextureLocation(armorItem))),
                            packedLight,
                            OverlayTexture.NO_OVERLAY,
                            0xFFFFFFFF // packed ARGB color (white, full alpha)
                    );
                } catch (Exception e) {
                    // Игнорируем ошибки рендеринга, чтобы не крашить игру
                    // Это может происходить, если модель еще не загружена
                }
            }
        }

        // ==================== РЮКЗАКИ (как броня) ====================
        @SuppressWarnings("removal")
        List<SlotResult> backpacks = CuriosApi.getCuriosHelper().findCurios(entity,
                stack -> stack.getItem() instanceof RebBackpackItem);

        for (SlotResult slotResult : backpacks) {
            if (!slotResult.slotContext().visible()) {
                continue;
            }

            ItemStack itemStack = slotResult.stack();
            if (!(itemStack.getItem() instanceof RebBackpackItem backpackItem)) {
                continue;
            }

            try {
                RebBackpackArmorRenderer backpackRenderer = new RebBackpackArmorRenderer(backpackItem);

                // Копируем свойства из родительской модели (как у брони)
                this.getParentModel().copyPropertiesTo(backpackRenderer);

                // Explicitly sync states to ensure correct positioning
                backpackRenderer.crouching = this.getParentModel().crouching;
                backpackRenderer.young = this.getParentModel().young;
                backpackRenderer.riding = this.getParentModel().riding;

                // Маппим к телу как CHEST (по сути — навес на торс)
                EquipmentSlot slot = EquipmentSlot.CHEST;
                backpackRenderer.prepForRender(entity, itemStack, slot, this.getParentModel());

                backpackRenderer.renderToBuffer(
                        poseStack,
                        bufferSource.getBuffer(
                                RenderType.entityTranslucent(backpackRenderer.getTextureLocation(backpackItem))),
                        packedLight,
                        OverlayTexture.NO_OVERLAY,
                        0xFFFFFFFF // packed ARGB color (white, full alpha)
                );
            } catch (Exception e) {
                // Игнорируем ошибки рендеринга, чтобы не крашить игру
            }
        }
    }
}
