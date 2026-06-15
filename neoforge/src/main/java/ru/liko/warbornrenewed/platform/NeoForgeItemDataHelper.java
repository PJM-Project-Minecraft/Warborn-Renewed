package ru.liko.warbornrenewed.platform;

import net.minecraft.world.item.ItemStack;
import ru.liko.warbornrenewed.registry.ModDataComponents;

public class NeoForgeItemDataHelper implements IItemDataHelper {

    @Override
    public boolean isNvgDown(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.NVG_DOWN.get(), false);
    }

    @Override
    public void setNvgDown(ItemStack stack, boolean down) {
        stack.set(ModDataComponents.NVG_DOWN.get(), down);
    }

    @Override
    public boolean isHelmetOpen(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.HELMET_OPEN.get(), false);
    }

    @Override
    public void setHelmetOpen(ItemStack stack, boolean open) {
        stack.set(ModDataComponents.HELMET_OPEN.get(), open);
    }

    @Override
    public String getArmorVariant(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.ARMOR_VARIANT.get(), "");
    }

    @Override
    public void setArmorVariant(ItemStack stack, String variant) {
        stack.set(ModDataComponents.ARMOR_VARIANT.get(), variant);
    }

    @Override
    public int getArmorColor(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.ARMOR_COLOR.get(), 0);
    }

    @Override
    public void setArmorColor(ItemStack stack, int color) {
        stack.set(ModDataComponents.ARMOR_COLOR.get(), color);
    }

    @Override
    public boolean hasArmorColor(ItemStack stack) {
        return stack.has(ModDataComponents.ARMOR_COLOR.get());
    }

    @Override
    public boolean isRebEnabled(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.REB_ENABLED.get(), false);
    }

    @Override
    public void setRebEnabled(ItemStack stack, boolean enabled) {
        stack.set(ModDataComponents.REB_ENABLED.get(), enabled);
    }

    @Override
    public String getArmorPackId(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.PACK_ID.get(), "");
    }

    @Override
    public void setArmorPackId(ItemStack stack, String packId) {
        stack.set(ModDataComponents.PACK_ID.get(), packId);
    }
}
