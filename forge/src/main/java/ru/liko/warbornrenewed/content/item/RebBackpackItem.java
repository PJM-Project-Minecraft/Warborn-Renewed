package ru.liko.warbornrenewed.content.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;
import ru.liko.warbornrenewed.WarbornRenewed;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

/**
 * Рюкзак РЭБ (радиоэлектронной борьбы) - носимый предмет,
 * создающий зону глушения дронов вокруг игрока.
 * Совместим с модом WRBDrones.
 */
public class RebBackpackItem extends Item implements GeoItem {

    // Тег NBT для состояния включения РЭБ
    public static final String NBT_REB_ENABLED = "reb_enabled";

    // Радиус действия РЭБ рюкзака (меньше чем у стационарного)
    public static final double REB_BACKPACK_RADIUS = 30.0;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final String variantId;
    private final ResourceLocation textureLocation;
    private final ResourceLocation itemTextureLocation;

    public RebBackpackItem(Properties properties, String variantId) {
        super(properties);
        this.variantId = variantId;
        this.textureLocation = WarbornRenewed.id("textures/reb-backpack-" + variantId + ".png");
        this.itemTextureLocation = WarbornRenewed.id("textures/item/reb-backpack-" + variantId + ".png");
    }

    /**
     * Получить идентификатор варианта (камуфляжа)
     */
    public String getVariantId() {
        return variantId;
    }

    /**
     * Получить путь к текстуре модели
     */
    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    /**
     * Получить путь к текстуре иконки предмета
     */
    public ResourceLocation getItemTextureLocation() {
        return itemTextureLocation;
    }

    /**
     * Проверить, включен ли РЭБ
     */
    public static boolean isRebEnabled(ItemStack stack) {
        return stack.hasTag() && stack.getTag() != null && stack.getTag().getBoolean("warborn_reb");
    }

    /**
     * Установить состояние РЭБ
     */
    public static void setRebEnabled(ItemStack stack, boolean enabled) {
        stack.getOrCreateTag().putBoolean("warborn_reb", enabled);
    }

    /**
     * Переключить состояние РЭБ
     */
    public static void toggleReb(ItemStack stack) {
        setRebEnabled(stack, !isRebEnabled(stack));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // Пока без анимаций, рюкзак статичный
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
