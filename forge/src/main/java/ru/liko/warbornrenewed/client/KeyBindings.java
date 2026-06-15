package ru.liko.warbornrenewed.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;
import ru.liko.warbornrenewed.WarbornRenewed;

/**
 * Client-side key bindings for WarBorn Renewed
 */
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = WarbornRenewed.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyBindings {

    public static final String CATEGORY = "key.categories." + WarbornRenewed.MODID;

    public static final KeyMapping NVG_TOGGLE = new KeyMapping(
            "key." + WarbornRenewed.MODID + ".nvg_toggle",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_N, // Default: N key
            CATEGORY);

    public static final KeyMapping REB_BACKPACK_TOGGLE = new KeyMapping(
            "key." + WarbornRenewed.MODID + ".reb_backpack_toggle",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B, // Default: B key (Backpack)
            CATEGORY);

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(NVG_TOGGLE);
        event.register(REB_BACKPACK_TOGGLE);
    }
}
