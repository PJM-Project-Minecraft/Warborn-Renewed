package ru.liko.warbornrenewed.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.liko.warbornrenewed.Warbornrenewed;

import java.util.function.UnaryOperator;

/**
 * Регистрация DataComponents для хранения данных на предметах
 * Заменяет NBT теги в Minecraft 1.21+
 */
public class ModDataComponents {

        public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister
                        .createDataComponents(Registries.DATA_COMPONENT_TYPE, Warbornrenewed.MODID);

        /**
         * Состояние NVG - опущен ли прибор ночного видения
         */
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> NVG_DOWN = DATA_COMPONENTS
                        .registerComponentType("nvg_down",
                                        builder -> builder.persistent(Codec.BOOL)
                                                        .networkSynchronized(
                                                                        net.minecraft.network.codec.ByteBufCodecs.BOOL));

        /**
         * Состояние шлема - открыт ли визор/забрало
         */
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> HELMET_OPEN = DATA_COMPONENTS
                        .registerComponentType("helmet_open",
                                        builder -> builder.persistent(Codec.BOOL)
                                                        .networkSynchronized(
                                                                        net.minecraft.network.codec.ByteBufCodecs.BOOL));

        /**
         * Вариант текстуры (скина) брони
         */
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ARMOR_VARIANT = DATA_COMPONENTS
                        .registerComponentType("armor_variant",
                                        builder -> builder.persistent(Codec.STRING)
                                                        .networkSynchronized(
                                                                        net.minecraft.network.codec.ByteBufCodecs.STRING_UTF8));

        /**
         * Цвет брони для покраски
         */
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ARMOR_COLOR = DATA_COMPONENTS
                        .registerComponentType("armor_color",
                                        builder -> builder.persistent(Codec.INT)
                                                        .networkSynchronized(
                                                                        net.minecraft.network.codec.ByteBufCodecs.INT));

        /**
         * Состояние РЭБ (включен/выключен)
         */
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> REB_ENABLED = DATA_COMPONENTS
                        .registerComponentType("reb_enabled",
                                        builder -> builder.persistent(Codec.BOOL)
                                                        .networkSynchronized(
                                                                        net.minecraft.network.codec.ByteBufCodecs.BOOL));
        /**
         * Уникальный ID брони из пака
         */
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> PACK_ID = DATA_COMPONENTS
                                        .registerComponentType("pack_id",
                                        builder -> builder.persistent(Codec.STRING)
                                                        .networkSynchronized(
                                                                        net.minecraft.network.codec.ByteBufCodecs.STRING_UTF8));
        public static void register(IEventBus eventBus) {
                DATA_COMPONENTS.register(eventBus);
        }
}
