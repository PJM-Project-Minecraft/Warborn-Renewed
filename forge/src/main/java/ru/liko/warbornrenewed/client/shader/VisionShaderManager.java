package ru.liko.warbornrenewed.client.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.world.InteractionHand;
import ru.liko.warbornrenewed.WarbornRenewed;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import ru.liko.warbornrenewed.content.item.BinocularItem;
import ru.liko.warbornrenewed.registry.ModItems;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public final class VisionShaderManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisionShaderManager.class);

    private static final String NVG_SHADER_ID_PREFIX = "nvg/";
    private static final String THERMAL_SHADER_ID = "thermal";
    private static final String BINOCULAR_SHADER_ID = "binocular";
    private static final ResourceLocation DEFAULT_NVG_SHADER = WarbornRenewed.id("shaders/post/pnv10t.json");
    private static final ResourceLocation THERMAL_SHADER = WarbornRenewed.id("shaders/post/thermal.json");
    private static final ResourceLocation BINOCULAR_SHADER = WarbornRenewed.id("shaders/post/binocular.json");

    private VisionShaderManager() {
    }

    public static void registerShaders() {
        VisionShaderRegistry registry = VisionShaderRegistry.getInstance();
        registerNightVisionShaders(registry);
        registerThermalShader(registry);
        registerBinocularShader(registry);
    }

    private static void registerNightVisionShaders(VisionShaderRegistry registry) {
        for (RegistryObject<? extends Item> entry : ModItems.armorPieces()) {
            Item registryItem = entry.get();
            if (!(registryItem instanceof WarbornArmorItem armorItem)) {
                continue;
            }

            if (!armorItem.hasVisionCapability(WarbornArmorItem.TAG_NVG)) {
                continue;
            }

            ResourceLocation shaderLocation = armorItem.getNvgShader();
            if (shaderLocation == null) {
                shaderLocation = DEFAULT_NVG_SHADER;
            }

            String shaderId = NVG_SHADER_ID_PREFIX + armorItem.getItemId();

            boolean registered = registry.registerShader(
                    shaderId,
                    shaderLocation,
                    minecraft -> isNightVisionActive(minecraft, armorItem),
                    VisionShaderManager::configureNightVision);

            if (registered) {
                LOGGER.debug("Registered NVG shader '{}' with id '{}' for helmet '{}'", shaderLocation, shaderId,
                        armorItem.getItemId());
            } else {
                LOGGER.debug("NVG shader with id '{}' was already registered", shaderId);
            }
        }
    }

    private static void registerThermalShader(VisionShaderRegistry registry) {
        boolean thermalRegistered = registry.registerShader(
                THERMAL_SHADER_ID,
                THERMAL_SHADER,
                VisionShaderManager::isThermalVisionActive,
                VisionShaderManager::configureThermalVision);

        if (thermalRegistered) {
            LOGGER.debug("Registered Thermal shader with id '{}'", THERMAL_SHADER_ID);
        } else {
            LOGGER.debug("Thermal shader with id '{}' was already registered", THERMAL_SHADER_ID);
        }
    }

    private static void registerBinocularShader(VisionShaderRegistry registry) {
        boolean binocularRegistered = registry.registerShader(
                BINOCULAR_SHADER_ID,
                BINOCULAR_SHADER,
                VisionShaderManager::isBinocularActive,
                VisionShaderManager::configureBinocular);

        if (binocularRegistered) {
            LOGGER.debug("Registered Binocular shader with id '{}'", BINOCULAR_SHADER_ID);
        } else {
            LOGGER.debug("Binocular shader with id '{}' was already registered", BINOCULAR_SHADER_ID);
        }
    }

    public static void processShaders(Minecraft minecraft) {
        VisionShaderRegistry.getInstance().processShaders(minecraft);
    }

    public static void disableShader() {
        VisionShaderRegistry.getInstance().shutdown();
    }

    public static boolean isShaderActive() {
        return VisionShaderRegistry.getInstance().isShaderActive();
    }

    public static void onResourceReload() {
        VisionShaderRegistry.getInstance().onResourceReload();
    }

    public static boolean isThermalShaderActive() {
        return VisionShaderRegistry.getInstance()
                .getCurrentActiveShaderId()
                .filter(THERMAL_SHADER_ID::equals)
                .isPresent();
    }

    private static boolean isNightVisionActive(Minecraft minecraft, WarbornArmorItem targetHelmet) {
        if (minecraft == null || minecraft.player == null) {
            return false;
        }

        ItemStack helmet = minecraft.player.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.isEmpty() || !(helmet.getItem() instanceof WarbornArmorItem armorItem)) {
            return false;
        }

        if (armorItem != targetHelmet) {
            return false;
        }

        if (!armorItem.hasVisionCapability(WarbornArmorItem.TAG_NVG)) {
            return false;
        }

        if (!WarbornArmorItem.isNVGDown(helmet)) {
            return false;
        }

        if (WarbornArmorItem.isHelmetOpen(helmet)) {
            return false;
        }

        return true;
    }

    private static boolean isThermalVisionActive(Minecraft minecraft) {
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

        if (!WarbornArmorItem.isNVGDown(helmet)) {
            return false;
        }

        if (WarbornArmorItem.isHelmetOpen(helmet)) {
            return false;
        }

        return true;
    }

    private static void configureNightVision(PostChain postChain) {
        Minecraft minecraft = Minecraft.getInstance();
        float time = 0.0F;
        if (minecraft != null) {
            if (minecraft.level != null) {
                time = (minecraft.level.getGameTime() + minecraft.getFrameTime())
                        / 20.0F;
            } else {
                time = minecraft.getFrameTime() / 20.0F;
            }
        }

        List<PostPass> passes = VisionShaderRegistry.getPasses(postChain);
        for (PostPass pass : passes) {
            if (pass.getEffect().getUniform("NightVisionEnabled") != null) {
                pass.getEffect().safeGetUniform("NightVisionEnabled").set(1.0F);
            }
            if (pass.getEffect().getUniform("VignetteEnabled") != null) {
                pass.getEffect().safeGetUniform("VignetteEnabled").set(1.0F);
            }
            if (pass.getEffect().getUniform("Time") != null) {
                pass.getEffect().safeGetUniform("Time").set(time);
            }
        }
    }

    private static void configureThermalVision(PostChain postChain) {
        Minecraft minecraft = Minecraft.getInstance();
        float time = 0.0F;
        if (minecraft != null) {
            if (minecraft.level != null) {
                time = (minecraft.level.getGameTime() + minecraft.getFrameTime())
                        / 20.0F;
            } else {
                time = minecraft.getFrameTime() / 20.0F;
            }
        }

        List<PostPass> passes = VisionShaderRegistry.getPasses(postChain);
        for (PostPass pass : passes) {
            if (pass.getEffect().getUniform("VignetteEnabled") != null) {
                pass.getEffect().safeGetUniform("VignetteEnabled").set(1.0F);
            }
            if (pass.getEffect().getUniform("VignetteRadius") != null) {
                pass.getEffect().safeGetUniform("VignetteRadius").set(0.65F);
            }
            if (pass.getEffect().getUniform("Brightness") != null) {
                pass.getEffect().safeGetUniform("Brightness").set(1.1F);
            }
            if (pass.getEffect().getUniform("NoiseAmplification") != null) {
                pass.getEffect().safeGetUniform("NoiseAmplification").set(2.2F);
            }
            if (pass.getEffect().getUniform("Time") != null) {
                pass.getEffect().safeGetUniform("Time").set(time);
            }
        }
    }

    // ==================== Binocular Methods ====================

    private static boolean isBinocularActive(Minecraft minecraft) {
        if (minecraft == null || minecraft.player == null) {
            return false;
        }

        // Проверяем использует ли игрок бинокль
        ItemStack mainHand = minecraft.player.getMainHandItem();
        ItemStack offHand = minecraft.player.getOffhandItem();

        boolean usingMainHand = mainHand.getItem() instanceof BinocularItem && minecraft.player.isUsingItem()
                && minecraft.player.getUsedItemHand() == InteractionHand.MAIN_HAND;
        boolean usingOffHand = offHand.getItem() instanceof BinocularItem && minecraft.player.isUsingItem()
                && minecraft.player.getUsedItemHand() == InteractionHand.OFF_HAND;

        return usingMainHand || usingOffHand;
    }

    private static void configureBinocular(PostChain postChain) {
        Minecraft minecraft = Minecraft.getInstance();
        float time = 0.0F;
        if (minecraft != null) {
            if (minecraft.level != null) {
                time = (minecraft.level.getGameTime() + minecraft.getFrameTime())
                        / 20.0F;
            } else {
                time = minecraft.getFrameTime() / 20.0F;
            }
        }

        List<PostPass> passes = VisionShaderRegistry.getPasses(postChain);
        for (PostPass pass : passes) {
            if (pass.getEffect().getUniform("Time") != null) {
                pass.getEffect().safeGetUniform("Time").set(time);
            }
        }
    }

    public static boolean isBinocularShaderActive() {
        return VisionShaderRegistry.getInstance()
                .getCurrentActiveShaderId()
                .filter(BINOCULAR_SHADER_ID::equals)
                .isPresent();
    }
}


