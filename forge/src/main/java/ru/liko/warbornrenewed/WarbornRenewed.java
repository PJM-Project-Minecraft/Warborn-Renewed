package ru.liko.warbornrenewed;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import ru.liko.warbornrenewed.config.WarbornConfig;
import ru.liko.warbornrenewed.network.NetworkHandler;
import ru.liko.warbornrenewed.packs.PackCreativeTabs;
import ru.liko.warbornrenewed.packs.WarbornPackManager;
import ru.liko.warbornrenewed.registry.ModAttributes;
import ru.liko.warbornrenewed.registry.ModCreativeTabs;
import ru.liko.warbornrenewed.registry.ModItems;
import ru.liko.warbornrenewed.registry.ModRecipes;
import ru.liko.warbornrenewed.registry.ModSoundEvents;
import ru.liko.warbornrenewed.setup.WarbornArmorSets;

@Mod(WarbornRenewed.MODID)
public class WarbornRenewed {
    public static final String MODID = "warbornrenewed";
    public static final Logger LOGGER = LogUtils.getLogger();

    public WarbornRenewed() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        // Register config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WarbornConfig.SPEC);

        // Initialize armor sets (must be before ModItems.register so DeferredRegister picks them up)
        WarbornArmorSets.bootstrap();

        // Register all deferred registries
        ModItems.register(modEventBus);
        ModAttributes.register(modEventBus);
        ModSoundEvents.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);

        // Register network packets
        NetworkHandler.register();

        // Load custom packs from warbornrenewed/packs directory
        WarbornPackManager.loadPacks();

        // Register dynamic creative tabs for each loaded pack
        PackCreativeTabs.registerPackTabs();
        PackCreativeTabs.register(modEventBus);

        LOGGER.debug("WarBorn Renewed core initialised (Forge)");
    }
    
    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}
