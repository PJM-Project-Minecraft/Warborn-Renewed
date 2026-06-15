package ru.liko.warbornrenewed.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class WarbornConfig {
    public static final ModConfigSpec SPEC;
    public static final Common COMMON;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        COMMON = new Common(builder);
        SPEC = builder.build();
    }

    public static class Common {
        public final ModConfigSpec.BooleanValue armorIsDamageable;

        public Common(ModConfigSpec.Builder builder) {
            builder.comment("Warborn Renewed Configuration")
                    .push("general");

            armorIsDamageable = builder
                    .comment("Should armor take damage and break? If false, armor will have infinite durability.",
                            "Должна ли броня получать урон и ломаться? Если false, броня будет иметь бесконечную прочность.")
                    .translation("config.warbornrenewed.armor_is_damageable")
                    .define("armorIsDamageable", true);

            builder.pop();
        }
    }
}
