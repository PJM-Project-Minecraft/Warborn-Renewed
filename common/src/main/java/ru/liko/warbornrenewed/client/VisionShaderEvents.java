package ru.liko.warbornrenewed.client;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.client.shader.VisionShaderManager;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Warbornrenewed.MODID, value = Dist.CLIENT)
public final class VisionShaderEvents {

    private VisionShaderEvents() {
    }

    // Shader rendering is now handled by GameRendererMixin
    // This ensures the shader is applied AFTER all rendering including hands

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) {
            return;
        }

        if ((mc.player == null || mc.level == null) && VisionShaderManager.isShaderActive()) {
            VisionShaderManager.disableShader();
        }
    }

    @SubscribeEvent
    public static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        VisionShaderManager.disableShader();
    }

    @EventBusSubscriber(modid = Warbornrenewed.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static final class ClientInit {

        private ClientInit() {
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(VisionShaderManager::registerShaders);
        }

        @SubscribeEvent
        public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
            event.registerReloadListener(new SimplePreparableReloadListener<Void>() {
                @Override
                protected Void prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
                    return null;
                }

                @Override
                protected void apply(Void object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
                    VisionShaderManager.onResourceReload();
                }
            });
        }
    }
}
