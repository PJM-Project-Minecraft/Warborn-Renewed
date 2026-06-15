package ru.liko.warbornrenewed.platform;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class ForgeItemDataHelper implements IItemDataHelper {

    @Override
    public boolean isNvgDown(ItemStack stack) {
        if (stack.hasTag() && stack.getTag() != null) {
            return stack.getTag().getBoolean("nvg_down");
        }
        return false;
    }

    @Override
    public void setNvgDown(ItemStack stack, boolean down) {
        stack.getOrCreateTag().putBoolean("nvg_down", down);
    }

    @Override
    public boolean isHelmetOpen(ItemStack stack) {
        if (stack.hasTag() && stack.getTag() != null) {
            return stack.getTag().getBoolean("helmet_open");
        }
        return false;
    }

    @Override
    public void setHelmetOpen(ItemStack stack, boolean open) {
        stack.getOrCreateTag().putBoolean("helmet_open", open);
    }

    @Override
    public String getArmorVariant(ItemStack stack) {
        if (stack.hasTag() && stack.getTag() != null) {
            return stack.getTag().getString("armor_variant");
        }
        return "";
    }

    @Override
    public void setArmorVariant(ItemStack stack, String variant) {
        stack.getOrCreateTag().putString("armor_variant", variant);
    }

    @Override
    public int getArmorColor(ItemStack stack) {
        if (stack.hasTag() && stack.getTag() != null && stack.getTag().contains("armor_color")) {
            return stack.getTag().getInt("armor_color");
        }
        return 0;
    }

    @Override
    public void setArmorColor(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt("armor_color", color);
    }

    @Override
    public boolean hasArmorColor(ItemStack stack) {
        return stack.hasTag() && stack.getTag() != null && stack.getTag().contains("armor_color");
    }

    @Override
    public boolean isRebEnabled(ItemStack stack) {
        if (stack.hasTag() && stack.getTag() != null) {
            return stack.getTag().getBoolean("reb_enabled");
        }
        return false;
    }

    @Override
    public void setRebEnabled(ItemStack stack, boolean enabled) {
        stack.getOrCreateTag().putBoolean("reb_enabled", enabled);
    }

    @Override
    public String getArmorPackId(ItemStack stack) {
        if (stack.hasTag() && stack.getTag() != null) {
            return stack.getTag().getString("pack_id");
        }
        return "";
    }

    @Override
    public void setArmorPackId(ItemStack stack, String packId) {
        stack.getOrCreateTag().putString("pack_id", packId);
    }
}
