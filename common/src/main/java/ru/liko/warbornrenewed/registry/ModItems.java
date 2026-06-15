package ru.liko.warbornrenewed.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.item.BinocularItem;
import ru.liko.warbornrenewed.content.item.RebBackpackItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Warbornrenewed.MODID);
    private static final List<DeferredItem<? extends Item>> ARMOR_PIECES = new ArrayList<>();
    private static final List<DeferredItem<? extends Item>> ARMOR_PARTS = new ArrayList<>();
    private static final List<DeferredItem<RebBackpackItem>> REB_BACKPACKS = new ArrayList<>();

    // ==================== Equipment Items ====================
    public static final DeferredItem<BinocularItem> BINOCULAR = ITEMS.register("binocular",
            () -> new BinocularItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.UNCOMMON)));

    // ==================== REB Backpacks ====================
    public static final DeferredItem<RebBackpackItem> REB_BACKPACK_DESERT = ITEMS.register("reb-backpack-desert",
            () -> new RebBackpackItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE), "desert"));

    public static final DeferredItem<RebBackpackItem> REB_BACKPACK_EMR = ITEMS.register("reb-backpack-emr",
            () -> new RebBackpackItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE), "emr"));

    public static final DeferredItem<RebBackpackItem> REB_BACKPACK_GREEN = ITEMS.register("reb-backpack-green",
            () -> new RebBackpackItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE), "green"));

    public static final DeferredItem<RebBackpackItem> REB_BACKPACK_MULTICAM = ITEMS.register("reb-backpack-multicam",
            () -> new RebBackpackItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE), "multicam"));

    public static final DeferredItem<RebBackpackItem> REB_BACKPACK_WHITE = ITEMS.register("reb-backpack-white",
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

    public static void trackArmorPiece(DeferredItem<? extends Item> item) {
        ARMOR_PIECES.add(item);
    }

    public static void trackArmorPart(DeferredItem<? extends Item> item) {
        ARMOR_PARTS.add(item);
    }

    public static List<DeferredItem<? extends Item>> armorPieces() {
        return Collections.unmodifiableList(ARMOR_PIECES);
    }

    public static List<DeferredItem<? extends Item>> armorParts() {
        return Collections.unmodifiableList(ARMOR_PARTS);
    }

    public static List<DeferredItem<RebBackpackItem>> rebBackpacks() {
        return Collections.unmodifiableList(REB_BACKPACKS);
    }
}
