package ru.liko.warbornrenewed.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import ru.liko.warbornrenewed.Warbornrenewed;
import ru.liko.warbornrenewed.content.item.RebBackpackItem;
import ru.liko.warbornrenewed.network.NetworkHandler;
import ru.liko.warbornrenewed.network.RebBackpackTogglePacket;
import top.theillusivec4.curios.api.CuriosApi;

/**
 * Клиентский обработчик бинда переключения РЭБ на рюкзаке.
 */
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Warbornrenewed.MODID, value = Dist.CLIENT)
public class RebBackpackToggleHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) {
            return;
        }

        if (KeyBindings.REB_BACKPACK_TOGGLE.consumeClick()) {
            handleToggle(player);
        }
    }

    private static void handleToggle(LocalPlayer player) {
        if (!ModList.get().isLoaded("curios")) {
            return;
        }

        ItemStack backpack = findEquippedRebBackpack(player);
        if (backpack.isEmpty()) {
            return;
        }

        boolean newState = !RebBackpackItem.isRebEnabled(backpack);

        // Обновляем NBT локально для мгновенного отображения
        RebBackpackItem.setRebEnabled(backpack, newState);

        // Синхронизируем с сервером (сервер также проиграет звук)
        NetworkHandler.sendToServer(new RebBackpackTogglePacket(newState));
    }

    private static ItemStack findEquippedRebBackpack(LocalPlayer player) {
        return CuriosApi.getCuriosInventory(player)
                .map(inv -> inv.getStacksHandler("back"))
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .map(handler -> handler.getStacks())
                .map(stacks -> {
                    for (int i = 0; i < stacks.getSlots(); i++) {
                        ItemStack stack = stacks.getStackInSlot(i);
                        if (!stack.isEmpty() && stack.getItem() instanceof RebBackpackItem) {
                            return stack;
                        }
                    }
                    return ItemStack.EMPTY;
                })
                .orElse(ItemStack.EMPTY);
    }
}
