package ru.liko.warbornrenewed.registry;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import ru.liko.warbornrenewed.WarbornRenewed;

import java.util.EnumMap;
import java.util.function.Supplier;

/**
 * Собственные материалы брони для Warborn-Renewed.
 * Полностью независимая система без зависимостей от других модов.
 * 
 * Материалы основаны на реальных стандартах:
 * - NIJ Standard 0101.06 (США)
 * - ГОСТ Р 50744-95 (Россия)
 * - VPAM (Германия)
 * 
 * Реалистичные характеристики материалов и классов защиты.
 *
 * Forge 1.20.1 - ArmorMaterial реализуется как enum.
 */
public enum ModArmorMaterials implements ArmorMaterial {

        /**
         * КОЖА (LEATHER) - базовый уровень
         */
        LEATHER("leather",
                        createDefense(0, 0, 0, 0),
                        0, SoundEvents.ARMOR_EQUIP_LEATHER,
                        0.0F, 0.0F, () -> Ingredient.EMPTY),

        /**
         * КЕВЛАР (KEVLAR) - NIJ Level IIA/II
         * Мягкая баллистическая защита из арамидных волокон
         * Используется в: легких бронежилетах, полицейской броне
         * Реальные аналоги: DuPont Kevlar 29, Twaron
         */
        KEVLAR("kevlar",
                        createDefense(1, 3, 5, 2),
                        15, SoundEvents.ARMOR_EQUIP_LEATHER,
                        0.5F, 0.0F, () -> Ingredient.EMPTY),

        /**
         * КЕРАМИКА (CERAMIC) - NIJ Level III
         * Керамические пластины (Al2O3, SiC, B4C)
         * Используется в: тактических бронеплитах, военных жилетах
         * Реальные аналоги: alumina plates, silicon carbide
         */
        CERAMIC("ceramic",
                        createDefense(2, 5, 7, 3),
                        12, SoundEvents.ARMOR_EQUIP_IRON,
                        2.5F, 0.05F, () -> Ingredient.EMPTY),

        /**
         * СТАЛЬ AR500 - NIJ Level III
         * Высокопрочная баллистическая сталь
         * Используется в: дешевых бронеплитах, тренировочных жилетах
         * Реальные аналоги: AR500, AR550 steel plates
         */
        AR500_STEEL("ar500_steel",
                        createDefense(2, 4, 6, 2),
                        10, SoundEvents.ARMOR_EQUIP_IRON,
                        2.0F, 0.1F, () -> Ingredient.EMPTY),

        /**
         * ПОЛИЭТИЛЕН UHMWPE - NIJ Level III/IV
         * Ультравысокомолекулярный полиэтилен
         * Используется в: современных легких плитах, элитной броне
         * Реальные аналоги: Dyneema, Spectra Shield
         */
        UHMWPE("uhmwpe",
                        createDefense(3, 6, 8, 3),
                        18, SoundEvents.ARMOR_EQUIP_NETHERITE,
                        3.0F, 0.08F, () -> Ingredient.EMPTY),

        /**
         * КОМПОЗИТ (COMPOSITE) - NIJ Level IV
         * Многослойная композитная броня (керамика + UHMWPE + сталь)
         * Используется в: военных плитах ESAPI/XSAPI, спецназовской броне
         * Реальные аналоги: ESAPI plates, XSAPI plates
         */
        COMPOSITE("composite",
                        createDefense(3, 7, 9, 4),
                        15, SoundEvents.ARMOR_EQUIP_NETHERITE,
                        4.0F, 0.15F, () -> Ingredient.EMPTY),

        /**
         * ТИТАН (TITANIUM) - NIJ Level III+
         * Титановые сплавы (Ti-6Al-4V)
         * Используется в: авиационной броне, элитных шлемах, спецназе
         * Реальные аналоги: titanium alloy plates
         */
        TITANIUM("titanium",
                        createDefense(4, 7, 9, 4),
                        20, SoundEvents.ARMOR_EQUIP_NETHERITE,
                        4.5F, 0.2F, () -> Ingredient.EMPTY);

        private static final int[] HEALTH_FUNCTION_FOR_TYPE = new int[] { 13, 15, 16, 11 };

        private final String name;
        private final EnumMap<ArmorItem.Type, Integer> defense;
        private final int enchantability;
        private final SoundEvent equipSound;
        private final float toughness;
        private final float knockbackResistance;
        private final Supplier<Ingredient> repairIngredient;

        ModArmorMaterials(String name,
                        EnumMap<ArmorItem.Type, Integer> defense,
                        int enchantability,
                        SoundEvent equipSound,
                        float toughness,
                        float knockbackResistance,
                        Supplier<Ingredient> repairIngredient) {
                this.name = name;
                this.defense = defense;
                this.enchantability = enchantability;
                this.equipSound = equipSound;
                this.toughness = toughness;
                this.knockbackResistance = knockbackResistance;
                this.repairIngredient = repairIngredient;
        }

        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
                return HEALTH_FUNCTION_FOR_TYPE[type.ordinal()] * 25;
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
                return defense.getOrDefault(type, 0);
        }

        @Override
        public int getEnchantmentValue() {
                return enchantability;
        }

        @Override
        public SoundEvent getEquipSound() {
                return equipSound;
        }

        @Override
        public Ingredient getRepairIngredient() {
                return repairIngredient.get();
        }

        @Override
        public String getName() {
                return WarbornRenewed.MODID + ":" + name;
        }

        @Override
        public float getToughness() {
                return toughness;
        }

        @Override
        public float getKnockbackResistance() {
                return knockbackResistance;
        }

        private static EnumMap<ArmorItem.Type, Integer> createDefense(int boots, int leggings, int chestplate,
                        int helmet) {
                EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
                map.put(ArmorItem.Type.BOOTS, boots);
                map.put(ArmorItem.Type.LEGGINGS, leggings);
                map.put(ArmorItem.Type.CHESTPLATE, chestplate);
                map.put(ArmorItem.Type.HELMET, helmet);
                return map;
        }
}
