package ru.liko.warbornrenewed.compat;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Интеграция с модом TACZ (Tac Zero).
 * Обеспечивает совместимость атрибута защиты от пуль с оружием TACZ.
 */
public class TACZIntegration {
    private static final Logger LOGGER = LoggerFactory.getLogger(TACZIntegration.class);

    public static final String TACZ_MOD_ID = "tacz";

    // Кэш для атрибута TACZ (инициализируется лениво)
    private static boolean initialized = false;
    private static Attribute taczBulletResistance = null;

    /**
     * Проверяет, загружен ли мод TACZ
     */
    public static boolean isTACZLoaded() {
        return ModList.get().isLoaded(TACZ_MOD_ID);
    }

    /**
     * Инициализирует интеграцию с TACZ.
     * Должен вызываться после полной загрузки реестров (например, в
     * FMLCommonSetupEvent).
     */
    public static void init() {
        if (initialized)
            return;
        initialized = true;

        if (!isTACZLoaded()) {
            LOGGER.info("TACZ mod not found, skipping TACZ integration");
            return;
        }

        try {
            // Получаем атрибут TACZ через встроенный реестр NeoForge
            // TACZ регистрирует атрибут как "tacz:tacz.bullet_resistance"
            ResourceLocation taczAttrId = new ResourceLocation(TACZ_MOD_ID, "tacz.bullet_resistance");
            taczBulletResistance = ForgeRegistries.ATTRIBUTES.getValue(taczAttrId);

            if (taczBulletResistance != null) {
                LOGGER.info("Successfully integrated with TACZ bullet_resistance attribute");
            } else {
                LOGGER.warn("TACZ mod found but bullet_resistance attribute not found in registry");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to initialize TACZ integration", e);
        }
    }

    /**
     * Возвращает атрибут TACZ bullet_resistance, или null если не доступен
     */
    @Nullable
    public static Attribute getTACZBulletResistance() {
        if (!initialized) {
            init();
        }
        return taczBulletResistance;
    }

    /**
     * Проверяет, доступна ли интеграция с TACZ
     */
    public static boolean isIntegrationAvailable() {
        return getTACZBulletResistance() != null;
    }

    /**
     * Возвращает поставщик атрибута TACZ для использования в ArmorAttributeSpec
     */
    public static Supplier<Attribute> getTACZBulletResistanceSupplier() {
        return () -> {
            Attribute attr = getTACZBulletResistance();
            if (attr == null) {
                throw new IllegalStateException("TACZ bullet_resistance attribute is not available");
            }
            return attr;
        };
    }
}
