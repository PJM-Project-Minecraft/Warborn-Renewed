package ru.liko.warbornrenewed.packs;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import ru.liko.warbornrenewed.platform.Services;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.model.GeoModel;

import java.util.List;
import java.util.function.Consumer;

public class CustomPackArmorItem extends ArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public CustomPackArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Here you can add custom animation controllers based on pack logic if needed
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<CustomPackArmorItem> renderer;

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack stack,
                    EquipmentSlot slot, HumanoidModel<?> defaultModel) {
                if (renderer == null) {
                    renderer = new PackRenderer();
                }
                renderer.prepForRender(livingEntity, stack, slot, defaultModel);
                return renderer;
            }
        });
    }

    private static class PackRenderer extends GeoArmorRenderer<CustomPackArmorItem> {
        public PackRenderer() {
            super(new PackModel());
            ((PackModel) this.getGeoModel()).renderer = this;
        }

        public ItemStack getCurrentStack() {
            return this.currentStack;
        }

        @Override
        public void actuallyRender(com.mojang.blaze3d.vertex.PoseStack poseStack, CustomPackArmorItem animatable, BakedGeoModel model, net.minecraft.client.renderer.RenderType renderType,
                                 net.minecraft.client.renderer.MultiBufferSource bufferSource, com.mojang.blaze3d.vertex.VertexConsumer buffer, boolean isReRender, float partialTick,
                                 int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            ItemStack stack = this.currentStack;
            if (stack != null && ru.liko.warbornrenewed.platform.Services.ITEM_DATA.hasArmorColor(stack)) {
                int color = ru.liko.warbornrenewed.platform.Services.ITEM_DATA.getArmorColor(stack);
                red = ((color >> 16) & 0xFF) / 255.0F;
                green = ((color >> 8) & 0xFF) / 255.0F;
                blue = (color & 0xFF) / 255.0F;
            }
            super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick,
                    packedLight, packedOverlay, red, green, blue, alpha);
        }
    }

    private static final ResourceLocation FALLBACK_MODEL = new ResourceLocation("warbornrenewed", "geo/default_armor.geo.json");
    private static final ResourceLocation FALLBACK_TEXTURE = new ResourceLocation("warbornrenewed", "textures/armor/default.png");
    private static final ResourceLocation FALLBACK_ANIMATION = new ResourceLocation("warbornrenewed", "animations/default.animation.json");

    private static class PackModel extends GeoModel<CustomPackArmorItem> {
        public PackRenderer renderer;

        private ArmorDef getDefFromStack() {
            if (renderer == null) return null;
            ItemStack stack = renderer.getCurrentStack();
            if (stack == null || stack.isEmpty()) return null;
            String packId = Services.ITEM_DATA.getArmorPackId(stack);
            if (packId == null || packId.isEmpty()) return null;
            return WarbornPackManager.getArmorDef(packId);
        }

        private ResourceLocation buildResource(String raw, String requiredPrefix, String requiredSuffix) {
            ResourceLocation loc = new ResourceLocation(raw);
            String path = loc.getPath();
            if (!path.startsWith(requiredPrefix)) {
                path = requiredPrefix + path;
            }
            if (!path.endsWith(requiredSuffix)) {
                path = path + requiredSuffix;
            }
            return new ResourceLocation(loc.getNamespace(), path);
        }

        @Override
        public ResourceLocation getModelResource(CustomPackArmorItem animatable) {
            ArmorDef def = getDefFromStack();
            if (def != null && def.getModelId() != null) {
                try {
                    return buildResource(def.getModelId(), "geo/", ".geo.json");
                } catch (Exception e) {
                    // Invalid ResourceLocation, fall through to default
                }
            }
            return FALLBACK_MODEL;
        }

        @Override
        public ResourceLocation getTextureResource(CustomPackArmorItem animatable) {
            ArmorDef def = getDefFromStack();
            if (def != null) {
                try {
                    if (def.getTextureId() != null && !def.getTextureId().isEmpty()) {
                        return buildResource(def.getTextureId(), "textures/", ".png");
                    } else if (def.getModelId() != null) {
                        return buildResource(def.getModelId(), "textures/", ".png");
                    }
                } catch (Exception e) {
                    // Invalid ResourceLocation, fall through to default
                }
            }
            return FALLBACK_TEXTURE;
        }

        @Override
        public ResourceLocation getAnimationResource(CustomPackArmorItem animatable) {
            ArmorDef def = getDefFromStack();
            if (def != null) {
                try {
                    if (def.getAnimationId() != null && !def.getAnimationId().isEmpty()) {
                        return buildResource(def.getAnimationId(), "animations/", ".animation.json");
                    }
                } catch (Exception e) {
                    // Invalid ResourceLocation, fall through to default
                }
            }
            return FALLBACK_ANIMATION;
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        String id = Services.ITEM_DATA.getArmorPackId(stack);
        if (id != null && !id.isEmpty()) {
            String locale = Services.PLATFORM.getCurrentLocale();
            String displayName = WarbornPackManager.getDisplayName(id, locale);
            if (displayName != null && !displayName.isEmpty()) {
                return Component.literal(displayName);
            }
        }
        return super.getName(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @javax.annotation.Nullable net.minecraft.world.level.Level level,
            List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        String id = Services.ITEM_DATA.getArmorPackId(stack);
        ArmorDef def = null;
        if (id != null && !id.isEmpty()) {
            def = WarbornPackManager.getArmorDef(id);
            if (def != null) {
                tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.pack_id", id)
                        .withStyle(ChatFormatting.DARK_GRAY));
            }
        }

        String materialName;
        if (def != null && def.getMaterial() != null && !def.getMaterial().isEmpty()) {
            materialName = def.getMaterial();
        } else {
            materialName = getMaterial().getName();
            // В Minecraft 1.20.1 material name может содержать namespace (например, "modid:material_name")
            if (materialName.contains(":")) {
                materialName = materialName.split(":")[1];
            }
        }

        String materialKey = "material.warbornrenewed." + materialName;
        Component materialDisplayName = Component.translatable(materialKey).withStyle(ChatFormatting.GOLD);
        tooltipComponents.add(Component.translatable("tooltip.warbornrenewed.material", materialDisplayName)
                .withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    // Custom stats getters intended to be used by mixins / modifiers events
    public int getDefense(ItemStack stack) {
        String id = Services.ITEM_DATA.getArmorPackId(stack);
        if (id != null && !id.isEmpty()) {
            ArmorDef def = WarbornPackManager.getArmorDef(id);
            if (def != null) {
                return def.getDefense();
            }
        }
        return this.getDefense();
    }

    public float getToughness(ItemStack stack) {
        String id = Services.ITEM_DATA.getArmorPackId(stack);
        if (id != null && !id.isEmpty()) {
            ArmorDef def = WarbornPackManager.getArmorDef(id);
            if (def != null) {
                return def.getToughness();
            }
        }
        return this.getToughness();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        String id = Services.ITEM_DATA.getArmorPackId(stack);
        if (id != null && !id.isEmpty()) {
            ArmorDef def = WarbornPackManager.getArmorDef(id);
            if (def != null && def.getDurability() > 0) {
                return def.getDurability();
            }
        }
        return super.getMaxDamage(stack);
    }
}
