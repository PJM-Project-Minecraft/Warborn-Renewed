package ru.liko.warbornrenewed.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ru.liko.warbornrenewed.Warbornrenewed;

/**
 * Собственные атрибуты для системы брони Warborn-Renewed.
 * Обеспечивает совместимость с модами оружия (TACZ, SuperbWarfare).
 */
public class ModAttributes {

        public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE,
                        Warbornrenewed.MODID);

        /**
         * ЗАЩИТА ОТ ПУЛЬ (Bullet Resistance)
         * 
         * Уменьшает урон от пуль на указанный процент.
         * Диапазон: 0.0 (0%) - 1.0 (100%)
         * 
         * Совместимость:
         * - SuperbWarfare
         * (com.atsuishio.superbwarfare.init.ModAttributes.BULLET_RESISTANCE)
         * - TACZ (через custom damage reduction)
         */
        public static final DeferredHolder<Attribute, Attribute> BULLET_RESISTANCE = ATTRIBUTES.register(
                        "bullet_resistance",
                        () -> new RangedAttribute(
                                        "attribute.warbornrenewed.bullet_resistance",
                                        0.0D, // Базовое значение
                                        0.0D, // Минимум
                                        1.0D // Максимум (100%)
                        ).setSyncable(true));

        /**
         * КЛАСС ЗАЩИТЫ (Protection Class)
         * 
         * Определяет класс баллистической защиты по стандартам NIJ/ГОСТ.
         * Используется для расчета пробития пулями разных калибров.
         * 
         * Классы защиты (NIJ Standard 0101.06):
         * - 0: Нет защиты
         * - 1: NIJ Level IIA - 9mm, .40 S&W (до 400 м/с)
         * - 2: NIJ Level II - 9mm, .357 Magnum (до 440 м/с)
         * - 3: NIJ Level IIIA - .44 Magnum, .357 SIG (до 470 м/с)
         * - 4: NIJ Level III - 7.62x51mm NATO (.308 Winchester)
         * - 5: NIJ Level IV - .30-06 AP, 7.62x54mmR AP
         * - 6: Специальная защита (экспериментальная)
         */
        public static final DeferredHolder<Attribute, Attribute> PROTECTION_CLASS = ATTRIBUTES.register(
                        "protection_class",
                        () -> new RangedAttribute(
                                        "attribute.warbornrenewed.protection_class",
                                        0.0D, // Базовое значение (нет защиты)
                                        0.0D, // Минимум
                                        6.0D // Максимум (специальная защита)
                        ).setSyncable(true));

        /**
         * МНОЖИТЕЛЬ УРОНА ОТ ВЗРЫВОВ (Blast Damage Multiplier)
         * 
         * Множитель урона от взрывов и осколков.
         * Значение < 1.0 = уменьшает урон
         * Значение = 1.0 = нормальный урон
         * Значение > 1.0 = увеличивает урон
         * 
         * Диапазон: 0.0 - 2.0
         */
        public static final DeferredHolder<Attribute, Attribute> BLAST_DAMAGE_MULTIPLIER = ATTRIBUTES.register(
                        "blast_damage_multiplier",
                        () -> new RangedAttribute(
                                        "attribute.warbornrenewed.blast_damage_multiplier",
                                        1.0D, // Базовое значение (нормальный урон)
                                        0.0D, // Минимум (полная защита)
                                        2.0D // Максимум (двойной урон)
                        ).setSyncable(true));

        /**
         * СКОРОСТЬ ДВИЖЕНИЯ (Movement Speed)
         * 
         * Множитель скорости движения при ношении брони.
         * Значение < 0.0 = замедление
         * Значение > 0.0 = ускорение
         * 
         * Диапазон: -0.5 - 0.2 (-50% до +20%)
         */
        public static final DeferredHolder<Attribute, Attribute> ARMOR_MOVEMENT_SPEED = ATTRIBUTES.register(
                        "armor_movement_speed",
                        () -> new RangedAttribute(
                                        "attribute.warbornrenewed.armor_movement_speed",
                                        0.0D, // Базовое значение (без изменений)
                                        -0.5D, // Минимум (-50% скорости)
                                        0.2D // Максимум (+20% скорости)
                        ).setSyncable(true));

        /**
         * Регистрация атрибутов в NeoForge.
         * Вызывается при инициализации мода.
         */
        public static void register(IEventBus eventBus) {
                ATTRIBUTES.register(eventBus);
        }

        /**
         * Вспомогательный метод для получения значения защиты от пуль.
         * 
         * @param livingEntity Сущность, у которой проверяется атрибут
         * @return Значение защиты от пуль (0.0 - 1.0)
         */
        public static double getBulletResistance(net.minecraft.world.entity.LivingEntity livingEntity) {
                var instance = livingEntity.getAttribute(BULLET_RESISTANCE);
                return instance != null ? instance.getValue() : 0.0D;
        }

        /**
         * Вспомогательный метод для получения класса защиты.
         * 
         * @param livingEntity Сущность, у которой проверяется атрибут
         * @return Класс защиты (0-6)
         */
        public static int getProtectionClass(net.minecraft.world.entity.LivingEntity livingEntity) {
                var instance = livingEntity.getAttribute(PROTECTION_CLASS);
                return instance != null ? (int) instance.getValue() : 0;
        }

        /**
         * Расчет пробития пули с учетом класса защиты и энергии пули.
         * 
         * @param protectionClass Класс защиты брони (0-6)
         * @param bulletEnergy    Энергия пули в джоулях
         * @return true если пуля пробивает броню, false если задерживается
         */
        public static boolean isPenetrated(int protectionClass, double bulletEnergy) {
                // Пороговые значения энергии для каждого класса защиты (в джоулях)
                double[] energyThresholds = {
                                0.0, // Class 0: Нет защиты
                                600.0, // Class 1 (IIA): 9mm FMJ (~600 J)
                                800.0, // Class 2 (II): 9mm +P (~800 J)
                                1000.0, // Class 3 (IIIA): .44 Magnum (~1000 J)
                                3500.0, // Class 4 (III): 7.62x51mm NATO (~3500 J)
                                5000.0, // Class 5 (IV): .30-06 AP (~5000 J)
                                8000.0 // Class 6: Специальная защита (~8000 J)
                };

                if (protectionClass < 0 || protectionClass >= energyThresholds.length) {
                        return true; // Неизвестный класс - пробивается
                }

                // Пуля пробивает, если её энергия превышает порог
                return bulletEnergy > energyThresholds[protectionClass];
        }

        /**
         * Расчет остаточного урона после пробития брони.
         * 
         * @param initialDamage    Исходный урон
         * @param bulletResistance Защита от пуль (0.0 - 1.0)
         * @param isPenetrated     Пробила ли пуля броню
         * @return Остаточный урон
         */
        public static double calculateDamage(double initialDamage, double bulletResistance, boolean isPenetrated) {
                if (!isPenetrated) {
                        // Броня полностью задержала пулю
                        return initialDamage * (1.0 - bulletResistance);
                } else {
                        // Пуля пробила броню - урон снижается частично
                        double damageReduction = bulletResistance * 0.5; // 50% эффективности при пробитии
                        return initialDamage * (1.0 - damageReduction);
                }
        }
}
