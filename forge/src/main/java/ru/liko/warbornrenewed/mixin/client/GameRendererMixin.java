package ru.liko.warbornrenewed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.liko.warbornrenewed.client.shader.VisionShaderManager;
import ru.liko.warbornrenewed.client.shader.VisionShaderRegistry;
import ru.liko.warbornrenewed.WarbornRenewed;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Nullable
    private PostChain postEffect;

    @Inject(method = "renderLevel", at = @At("TAIL"))
    private void warbornrenewed$applyVisionShader(float partialTicks, long finishTimeNano, PoseStack poseStack, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null && mc.level != null && mc.player != null) {
            VisionShaderManager.processShaders(mc);
        }
    }

    @Inject(method = "checkEntityPostEffect", at = @At("HEAD"), cancellable = true)
    private void warbornrenewed$keepVisionShader(net.minecraft.world.entity.Entity entity, CallbackInfo ci) {
        if (warbornrenewed$isVisionPostEffectActive()) {
            ci.cancel();
        }
    }

    @Inject(method = "shutdownEffect", at = @At("HEAD"), cancellable = true)
    private void warbornrenewed$preventExternalShutdown(CallbackInfo ci) {
        if (warbornrenewed$isVisionPostEffectActive()
                && !VisionShaderRegistry.getInstance().isInternalShutdownInProgress()) {
            ci.cancel();
        }
    }

    @Unique
    private boolean warbornrenewed$isVisionPostEffectActive() {
        return VisionShaderRegistry.getInstance().isShaderActive();
    }
}
