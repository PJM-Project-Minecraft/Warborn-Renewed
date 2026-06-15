package ru.liko.warbornrenewed.client.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public final class VisionShaderRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisionShaderRegistry.class);
    private static final VisionShaderRegistry INSTANCE = new VisionShaderRegistry();
    private static final Field POST_CHAIN_PASSES_FIELD = locatePassesField();

    private final Map<String, ShaderEntry> shaders = new LinkedHashMap<>();
    private String currentActiveShader;
    private boolean internalShutdownInProgress;

    private VisionShaderRegistry() {
    }

    public static VisionShaderRegistry getInstance() {
        return INSTANCE;
    }

    public static List<PostPass> getPasses(PostChain chain) {
        try {
            if (POST_CHAIN_PASSES_FIELD == null) {
                return List.of();
            }
            @SuppressWarnings("unchecked")
            List<PostPass> passes = (List<PostPass>) POST_CHAIN_PASSES_FIELD.get(chain);
            return passes;
        } catch (Exception e) {
            LOGGER.error("Failed to access PostChain passes", e);
            return List.of();
        }
    }

    public boolean registerShader(String id,
            ResourceLocation shaderLocation,
            Predicate<Minecraft> activationCondition,
            Consumer<PostChain> configurer) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(shaderLocation, "shaderLocation");
        Objects.requireNonNull(activationCondition, "activationCondition");
        Objects.requireNonNull(configurer, "configurer");

        if (shaders.containsKey(id)) {
            LOGGER.warn("Shader with id '{}' is already registered", id);
            return false;
        }

        shaders.put(id, new ShaderEntry(shaderLocation, activationCondition, configurer));
        return true;
    }

    public void unregisterShader(String id) {
        ShaderEntry removed = shaders.remove(id);
        if (removed != null && Objects.equals(currentActiveShader, id)) {
            shutdownCurrent(Minecraft.getInstance());
        }
    }

    public Set<String> getRegisteredShaderIds() {
        return Set.copyOf(shaders.keySet());
    }

    public Optional<String> getCurrentActiveShaderId() {
        return Optional.ofNullable(currentActiveShader);
    }

    public boolean isShaderActive() {
        return currentActiveShader != null;
    }

    public boolean isShaderForceEnabled(String id) {
        ShaderEntry entry = shaders.get(id);
        return entry != null && Boolean.TRUE.equals(entry.forceEnabled);
    }

    public boolean setShaderEnabled(String id, boolean enabled) {
        ShaderEntry entry = shaders.get(id);
        if (entry == null) {
            return false;
        }

        entry.forceEnabled = enabled;

        if (!enabled && Objects.equals(currentActiveShader, id)) {
            shutdownCurrent(Minecraft.getInstance());
        }

        return true;
    }

    public void processShaders(Minecraft minecraft) {
        if (minecraft == null) {
            return;
        }

        if (minecraft.level == null || minecraft.player == null) {
            shutdownCurrent(minecraft);
            return;
        }

        ItemStack helmet = minecraft.player.getItemBySlot(EquipmentSlot.HEAD);
        if (!helmet.isEmpty() && helmet.getItem() instanceof WarbornArmorItem) {
            if (WarbornArmorItem.isHelmetOpen(helmet)) {
                shutdownCurrent(minecraft);
                return;
            }
        }

        String shaderToActivate = null;
        ShaderEntry entryToActivate = null;

        for (Map.Entry<String, ShaderEntry> entry : shaders.entrySet()) {
            ShaderEntry shaderEntry = entry.getValue();
            boolean activatedByCondition = shaderEntry.activationCondition.test(minecraft);
            boolean forceDisabled = Boolean.FALSE.equals(shaderEntry.forceEnabled);
            boolean forceEnabled = Boolean.TRUE.equals(shaderEntry.forceEnabled);

            boolean shouldBeActive = (activatedByCondition && !forceDisabled) || forceEnabled;

            if (shouldBeActive) {
                shaderToActivate = entry.getKey();
                entryToActivate = shaderEntry;
                break;
            }
        }

        GameRenderer gameRenderer = minecraft.gameRenderer;

        if (shaderToActivate == null) {
            if (currentActiveShader != null) {
                shutdownCurrent(minecraft);
            }
            return;
        }

        boolean needsReload = !shaderToActivate.equals(currentActiveShader);

        if (!needsReload && gameRenderer.currentEffect() == null) {
            needsReload = true;
        }

        if (needsReload) {
            try {
                internalShutdownInProgress = true;
                gameRenderer.loadEffect(entryToActivate.shaderLocation);
                currentActiveShader = shaderToActivate;
            } catch (Exception e) {
                LOGGER.error("Failed to load shader {}", shaderToActivate, e);
                currentActiveShader = null;
                return;
            } finally {
                internalShutdownInProgress = false;
            }
        }

        PostChain currentEffect = gameRenderer.currentEffect();
        if (currentEffect != null) {
            try {
                entryToActivate.configurer.accept(currentEffect);
            } catch (Exception e) {
                LOGGER.error("Failed to configure shader {}", shaderToActivate, e);
            }
        }
    }

    public void shutdown() {
        shutdownCurrent(Minecraft.getInstance());
    }

    public boolean isInternalShutdownInProgress() {
        return internalShutdownInProgress;
    }

    public void onResourceReload() {
        shutdown();
    }

    private void shutdownCurrent(Minecraft minecraft) {
        if (minecraft == null) {
            currentActiveShader = null;
            return;
        }

        if (currentActiveShader != null) {
            try {
                internalShutdownInProgress = true;
                minecraft.gameRenderer.shutdownEffect();
            } finally {
                internalShutdownInProgress = false;
            }
            currentActiveShader = null;
        }
    }

    private static final class ShaderEntry {
        final ResourceLocation shaderLocation;
        final Predicate<Minecraft> activationCondition;
        final Consumer<PostChain> configurer;
        Boolean forceEnabled;

        ShaderEntry(ResourceLocation shaderLocation,
                Predicate<Minecraft> activationCondition,
                Consumer<PostChain> configurer) {
            this.shaderLocation = shaderLocation;
            this.activationCondition = activationCondition;
            this.configurer = configurer;
        }
    }

    private static Field locatePassesField() {
        try {
            // In NeoForge 1.21.1, try to find the passes field using reflection
            Field field = PostChain.class.getDeclaredField("passes");
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            // Try alternative field names for different mappings
            try {
                for (Field field : PostChain.class.getDeclaredFields()) {
                    if (field.getType() == List.class) {
                        field.setAccessible(true);
                        return field;
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("Unable to locate PostChain passes field via reflection", ex);
            }
            LOGGER.error("Unable to locate PostChain passes field", e);
            return null;
        }
    }
}
