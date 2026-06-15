package ru.liko.warbornrenewed.platform;

import net.minecraft.world.item.ItemStack;

public interface IItemDataHelper {
    boolean isNvgDown(ItemStack stack);
    void setNvgDown(ItemStack stack, boolean down);

    boolean isHelmetOpen(ItemStack stack);
    void setHelmetOpen(ItemStack stack, boolean open);

    String getArmorVariant(ItemStack stack);
    void setArmorVariant(ItemStack stack, String variant);

    int getArmorColor(ItemStack stack);
    void setArmorColor(ItemStack stack, int color);
    boolean hasArmorColor(ItemStack stack);

    boolean isRebEnabled(ItemStack stack);
    void setRebEnabled(ItemStack stack, boolean enabled);

    String getArmorPackId(ItemStack stack);
    void setArmorPackId(ItemStack stack, String packId);
}
