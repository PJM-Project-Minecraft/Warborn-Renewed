package ru.liko.warbornrenewed.mixin.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Unique;
import ru.liko.warbornrenewed.client.shader.VisionShaderManager;
import ru.liko.warbornrenewed.client.shader.VisionShaderRegistry;
import ru.liko.warbornrenewed.Warbornrenewed;

import javax.annotation.Nullable;

/**
 * Mixin to apply vision shaders to all rendering, including first-person hands
 * This fixes the issue where shaders don't apply to player hands
 * Updated for Minecraft 1.21.1
 */
@OnlyIn(Dist.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Nullable
    private PostChain postEffect;

    /**
     * Inject at the end of renderLevel to ensure shader is applied after all
     * rendering
     * In 1.21.1, renderLevel takes DeltaTracker instead of partialTick and long
     * nanoTime
     */
    @Inject(method = "renderLevel", at = @At("TAIL"))
    private void warbornrenewed$applyVisionShader(DeltaTracker deltaTracker, CallbackInfo ci) {
        // Update and process shader after all world rendering (including hands)
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
