package ru.liko.warbornrenewed.compat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import ru.liko.warbornrenewed.content.item.RebBackpackItem;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.Optional;

/**
 * Интеграция с модом WRBDrones.
 * Предоставляет API для проверки наличия рюкзаков РЭБ на игроках.
 */
public final class WrbDronesIntegration {

    private WrbDronesIntegration() {
    }

    /**
     * Проверяет, загружен ли мод WRBDrones
     */
    public static boolean isWrbDronesLoaded() {
        return ModList.get().isLoaded("wrbdrones");
    }

    /**
     * Проверяет, загружен ли мод Curios
     */
    public static boolean isCuriosLoaded() {
        return ModList.get().isLoaded("curios");
    }

    /**
     * Получает рюкзак РЭБ с игрока, если он надет и включен.
     * 
     * @param player игрок для проверки
     * @return Optional с ItemStack рюкзака, если найден и включен
     */
    public static Optional<ItemStack> getActiveRebBackpack(Player player) {
        if (!isCuriosLoaded()) {
            return Optional.empty();
        }

        return CuriosApi.getCuriosInventory(player)
                .map(handler -> {
                    var stacksHandler = handler.getStacksHandler("back");
                    if (stacksHandler.isPresent()) {
                        var stacks = stacksHandler.get().getStacks();
                        for (int i = 0; i < stacks.getSlots(); i++) {
                            ItemStack stack = stacks.getStackInSlot(i);
                            if (!stack.isEmpty() && stack.getItem() instanceof RebBackpackItem) {
                                if (RebBackpackItem.isRebEnabled(stack)) {
                                    return stack;
                                }
                            }
                        }
                    }
                    return ItemStack.EMPTY;
                })
                .filter(stack -> !stack.isEmpty());
    }

    /**
     * Проверяет, есть ли у игрока активный рюкзак РЭБ
     * 
     * @param player игрок для проверки
     * @return true если есть включенный рюкзак РЭБ
     */
    public static boolean hasActiveRebBackpack(Player player) {
        return getActiveRebBackpack(player).isPresent();
    }

    /**
     * Получает радиус действия РЭБ рюкзака игрока.
     * 
     * @param player игрок для проверки
     * @return радиус глушения в блоках, или 0 если нет активного рюкзака
     */
    public static double getRebBackpackRadius(Player player) {
        if (hasActiveRebBackpack(player)) {
            return RebBackpackItem.REB_BACKPACK_RADIUS;
        }
        return 0.0;
    }

    /**
     * Вычисляет коэффициент воздействия РЭБ рюкзаков на сущность.
     * Проверяет всех игроков в зоне досягаемости.
     * 
     * @param entity сущность для проверки
     * @return коэффициент воздействия (0.0 - 1.0)
     */
    public static double getRebBackpackFactor(Entity entity) {
        if (entity == null || entity.level() == null) {
            return 0.0;
        }

        if (!isCuriosLoaded()) {
            return 0.0;
        }

        double maxFactor = 0.0;
        double checkRadius = RebBackpackItem.REB_BACKPACK_RADIUS + 10.0; // Немного больше для поиска

        // Ищем всех игроков в радиусе
        List<Player> nearbyPlayers = entity.level().getEntitiesOfClass(
                Player.class,
                entity.getBoundingBox().inflate(checkRadius),
                player -> !player.isSpectator());

        for (Player player : nearbyPlayers) {
            if (hasActiveRebBackpack(player)) {
                double distance = Math.sqrt(entity.distanceToSqr(player));
                double radius = RebBackpackItem.REB_BACKPACK_RADIUS;

                if (distance <= radius) {
                    double normalized = distance / radius;
                    double factor = 1.0 - normalized;
                    factor = factor * factor; // Квадратичное затухание
                    maxFactor = Math.max(maxFactor, factor);
                }
            }
        }

        return Math.min(1.0, maxFactor);
    }

    /**
     * Проверяет, находится ли сущность в зоне действия рюкзака РЭБ
     * 
     * @param entity сущность для проверки
     * @return true если сущность под воздействием РЭБ рюкзака
     */
    public static boolean isInRebBackpackZone(Entity entity) {
        return getRebBackpackFactor(entity) > 0.0;
    }
}
