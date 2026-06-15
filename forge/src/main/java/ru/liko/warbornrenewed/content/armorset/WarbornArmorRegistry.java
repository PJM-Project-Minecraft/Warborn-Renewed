package ru.liko.warbornrenewed.content.armorset;

import ru.liko.warbornrenewed.registry.ModItems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class WarbornArmorRegistry {
    private static final List<WarbornArmorSet> REGISTERED_SETS = new ArrayList<>();

    private WarbornArmorRegistry() {
    }

    public static WarbornArmorSet registerSet(WarbornArmorSet.Builder builder) {
        WarbornArmorSet set = builder.register(ModItems.ITEMS);
        REGISTERED_SETS.add(set);
        return set;
    }

    public static List<WarbornArmorSet> registeredSets() {
        return Collections.unmodifiableList(REGISTERED_SETS);
    }
}
