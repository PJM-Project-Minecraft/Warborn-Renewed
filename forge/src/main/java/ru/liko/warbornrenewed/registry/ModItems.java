package ru.liko.warbornrenewed.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import ru.liko.warbornrenewed.WarbornRenewed;
import ru.liko.warbornrenewed.content.item.BinocularItem;
import ru.liko.warbornrenewed.content.item.RebBackpackItem;
import ru.liko.warbornrenewed.packs.CustomPackArmorItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            WarbornRenewed.MODID);
    private static final List<RegistryObject<? extends Item>> ARMOR_PIECES = new ArrayList<>();
    private static final List<RegistryObject<? extends Item>> ARMOR_PARTS = new ArrayList<>();
    private static final List<RegistryObject<RebBackpackItem>> REB_BACKPACKS = new ArrayList<>();

    // ==================== Equipment Items ====================
    public static final RegistryObject<BinocularItem> BINOCULAR = ITEMS.register("binocular",
            () -> new BinocularItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.UNCOMMON)));

    // ==================== Pack Armor Items (base items for custom packs) ====================
    public static final RegistryObject<CustomPackArmorItem> PACK_HELMET = ITEMS.register("pack_helmet",
            () -> new CustomPackArmorItem(ModArmorMaterials.KEVLAR, ArmorItem.Type.HELMET,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<CustomPackArmorItem> PACK_CHESTPLATE = ITEMS.register("pack_chestplate",
            () -> new CustomPackArmorItem(ModArmorMaterials.KEVLAR, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<CustomPackArmorItem> PACK_LEGGINGS = ITEMS.register("pack_leggings",
            () -> new CustomPackArmorItem(ModArmorMaterials.KEVLAR, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().stacksTo(1)));

    public static final RegistryObject<CustomPackArmorItem> PACK_BOOTS = ITEMS.register("pack_boots",
            () -> new CustomPackArmorItem(ModArmorMaterials.KEVLAR, ArmorItem.Type.BOOTS,
                    new Item.Properties().stacksTo(1)));

    // ==================== REB Backpacks ====================
    public static final RegistryObject<RebBackpackItem> REB_BACKPACK_DESERT = ITEMS.register("reb-backpack-desert",
            () -> new RebBackpackItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE), "desert"));

    public static final RegistryObject<RebBackpackItem> REB_BACKPACK_EMR = ITEMS.register("reb-backpack-emr",
            () -> new RebBackpackItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE), "emr"));

    public static final RegistryObject<RebBackpackItem> REB_BACKPACK_GREEN = ITEMS.register("reb-backpack-green",
            () -> new RebBackpackItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE), "green"));

    public static final RegistryObject<RebBackpackItem> REB_BACKPACK_MULTICAM = ITEMS.register("reb-backpack-multicam",
            () -> new RebBackpackItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE), "multicam"));

    public static final RegistryObject<RebBackpackItem> REB_BACKPACK_WHITE = ITEMS.register("reb-backpack-white",
            () -> new RebBackpackItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE), "white"));

    static {
        // Добавляем все рюкзаки в список для доступа
        REB_BACKPACKS.add(REB_BACKPACK_DESERT);
        REB_BACKPACKS.add(REB_BACKPACK_EMR);
        REB_BACKPACKS.add(REB_BACKPACK_GREEN);
        REB_BACKPACKS.add(REB_BACKPACK_MULTICAM);
        REB_BACKPACKS.add(REB_BACKPACK_WHITE);
    }

    private ModItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static void trackArmorPiece(RegistryObject<? extends Item> item) {
        ARMOR_PIECES.add(item);
    }

    public static void trackArmorPart(RegistryObject<? extends Item> item) {
        ARMOR_PARTS.add(item);
    }

    public static List<RegistryObject<? extends Item>> armorPieces() {
        return Collections.unmodifiableList(ARMOR_PIECES);
    }

    public static List<RegistryObject<? extends Item>> armorParts() {
        return Collections.unmodifiableList(ARMOR_PARTS);
    }

    public static List<RegistryObject<RebBackpackItem>> rebBackpacks() {
        return Collections.unmodifiableList(REB_BACKPACKS);
    }
}
