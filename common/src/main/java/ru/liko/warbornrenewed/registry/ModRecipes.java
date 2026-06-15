package ru.liko.warbornrenewed.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.recipe.DyeArmorRecipe;

public class ModRecipes {
        public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
                        .create(Registries.RECIPE_SERIALIZER, Warbornrenewed.MODID);

        public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DyeArmorRecipe>> DYE_ARMOR_SERIALIZER = RECIPE_SERIALIZERS
                        .register("crafting_special_dyearmor", DyeArmorRecipe.Serializer::new);
}
