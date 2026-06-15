package ru.liko.warbornrenewed.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class WarbornConfig {
    public static final ForgeConfigSpec SPEC;
    public static final Common COMMON;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new Common(builder);
        SPEC = builder.build();
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue armorIsDamageable;

        public Common(ForgeConfigSpec.Builder builder) {
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
