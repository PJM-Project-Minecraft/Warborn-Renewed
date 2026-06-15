package ru.liko.warbornrenewed.mixin.client;

import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.liko.warbornrenewed.client.BinocularClientEvents;

/**
 * Forge 1.20.1: снижаем поворот камеры при активном зуме бинокля (аналог NeoForge CalculatePlayerTurnEvent).
 */
@OnlyIn(Dist.CLIENT)
@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Unique
    private static final String TURN = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V";

    @Redirect(method = "turnPlayer", at = @At(value = "INVOKE", target = TURN))
    private void warbornrenewed$scaleBinocularLook(LocalPlayer player, double yaw, double pitch) {
        if (BinocularClientEvents.isUsingBinocular(player)) {
            double m = BinocularClientEvents.BINOCULAR_LOOK_SENS_MULTIPLIER;
            player.turn(yaw * m, pitch * m);
        } else {
            player.turn(yaw, pitch);
        }
    }
}
