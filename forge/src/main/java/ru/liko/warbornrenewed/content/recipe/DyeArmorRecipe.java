package ru.liko.warbornrenewed.content.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;

import java.util.ArrayList;
import java.util.List;

public class DyeArmorRecipe extends CustomRecipe {

    public DyeArmorRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer input, Level level) {
        ItemStack armor = ItemStack.EMPTY;
        List<ItemStack> dyes = new ArrayList<>();

        for (int i = 0; i < input.getContainerSize(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof WarbornArmorItem) {
                    if (!armor.isEmpty()) {
                        return false;
                    }
                    armor = stack;
                } else if (stack.getItem() instanceof DyeItem) {
                    dyes.add(stack);
                } else {
                    return false;
                }
            }
        }

        return !armor.isEmpty() && !dyes.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingContainer input, RegistryAccess access) {
        ItemStack armor = ItemStack.EMPTY;
        int colorCount = 0;
        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;

        for (int i = 0; i < input.getContainerSize(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof WarbornArmorItem) {
                    armor = stack.copy();
                    armor.setCount(1);
                    if (ru.liko.warbornrenewed.platform.Services.ITEM_DATA.hasArmorColor(armor)) {
                        int existingColor = ru.liko.warbornrenewed.platform.Services.ITEM_DATA.getArmorColor(armor);
                        int r = (existingColor >> 16) & 0xFF;
                        int g = (existingColor >> 8) & 0xFF;
                        int b = existingColor & 0xFF;
                        totalRed += r;
                        totalGreen += g;
                        totalBlue += b;
                        colorCount++;
                    }
                } else if (stack.getItem() instanceof DyeItem dyeItem) {
                    DyeColor dyeColor = dyeItem.getDyeColor();
                    int color = dyeColor.getTextColor();
                    int r = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int b = color & 0xFF;
                    totalRed += r;
                    totalGreen += g;
                    totalBlue += b;
                    colorCount++;
                }
            }
        }

        if (armor.isEmpty() || colorCount == 0) {
            return ItemStack.EMPTY;
        }

        int avgRed = totalRed / colorCount;
        int avgGreen = totalGreen / colorCount;
        int avgBlue = totalBlue / colorCount;

        // Preserve color vibrance using vanilla blending logic
        int maxColorSum = 0;
        if (ru.liko.warbornrenewed.platform.Services.ITEM_DATA.hasArmorColor(armor)) {
            int existingColor = ru.liko.warbornrenewed.platform.Services.ITEM_DATA.getArmorColor(armor);
            int r = (existingColor >> 16) & 0xFF;
            int g = (existingColor >> 8) & 0xFF;
            int b = existingColor & 0xFF;
            maxColorSum += Math.max(r, Math.max(g, b));
        }
        for (int i = 0; i < input.getContainerSize(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty() && stack.getItem() instanceof DyeItem dyeItem) {
                int color = dyeItem.getDyeColor().getTextColor();
                int r = (color >> 16) & 0xFF;
                int g = (color >> 8) & 0xFF;
                int b = color & 0xFF;
                maxColorSum += Math.max(r, Math.max(g, b));
            }
        }

        float averageMax = (float) maxColorSum / (float) colorCount;
        float maxAverage = (float) Math.max(avgRed, Math.max(avgGreen, avgBlue));

        if (maxAverage > 0.0F) {
            avgRed = (int) ((float) avgRed * averageMax / maxAverage);
            avgGreen = (int) ((float) avgGreen * averageMax / maxAverage);
            avgBlue = (int) ((float) avgBlue * averageMax / maxAverage);
        }

        int newColor = (avgRed << 16) | (avgGreen << 8) | avgBlue;

        ru.liko.warbornrenewed.platform.Services.ITEM_DATA.setArmorColor(armor, newColor);

        return armor;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < remaining.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.hasCraftingRemainingItem()) {
                remaining.set(i, stack.getCraftingRemainingItem());
            }
        }
        return remaining;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ru.liko.warbornrenewed.registry.ModRecipes.DYE_ARMOR_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<DyeArmorRecipe> {
        @Override
        public DyeArmorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            CraftingBookCategory category = CraftingBookCategory.MISC;
            String categoryStr = net.minecraft.util.GsonHelper.getAsString(json, "category", "misc");
            try {
                category = CraftingBookCategory.valueOf(categoryStr.toUpperCase());
            } catch (IllegalArgumentException ignored) {
            }
            return new DyeArmorRecipe(recipeId, category);
        }

        @Override
        public DyeArmorRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            CraftingBookCategory category = buffer.readEnum(CraftingBookCategory.class);
            return new DyeArmorRecipe(recipeId, category);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, DyeArmorRecipe recipe) {
            buffer.writeEnum(recipe.category());
        }
    }
}
