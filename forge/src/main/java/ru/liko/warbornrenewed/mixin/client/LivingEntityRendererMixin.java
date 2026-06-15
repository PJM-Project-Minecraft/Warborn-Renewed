package ru.liko.warbornrenewed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import ru.liko.warbornrenewed.client.shader.VisionShaderManager;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;

/**
 * When the thermal shader is active we render living entities at full
 * brightness with no overlay.
 * This makes them stand out against the post-processing effect, simulating a
 * thermal highlight.
 * Updated for Minecraft 1.21.1
 */
@OnlyIn(Dist.CLIENT)
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>>
        extends EntityRenderer<T> implements RenderLayerParent<T, M> {

    private static final int FULL_BRIGHT_LIGHT = 15728880;

    protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * In 1.21.1 the render method signature is:
     * render(T entity, float entityYaw, float partialTick, PoseStack poseStack,
     * MultiBufferSource bufferSource, int packedLight)
     */
    @ModifyVariable(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), argsOnly = true, index = 6)
    private int warbornrenewed$forceFullBright(int packedLight) {
        if (warbornrenewed$isThermalVisionActive()) {
            return FULL_BRIGHT_LIGHT;
        }
        return packedLight;
    }

    @Unique
    private boolean warbornrenewed$isThermalVisionActive() {
        if (!VisionShaderManager.isThermalShaderActive()) {
            return false;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null || minecraft.player == null) {
            return false;
        }

        ItemStack helmet = minecraft.player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty() || !(helmet.getItem() instanceof WarbornArmorItem armorItem)) {
            return false;
        }

        if (!armorItem.hasVisionCapability(WarbornArmorItem.TAG_THERMAL)) {
            return false;
        }

        return !WarbornArmorItem.isHelmetOpen(helmet);
    }
}
