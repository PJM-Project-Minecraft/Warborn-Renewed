package ru.liko.warbornrenewed.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.client.event.InputEvent;
import ru.liko.warbornrenewed.WarbornRenewed;
import ru.liko.warbornrenewed.content.armorset.WarbornArmorItem;
import ru.liko.warbornrenewed.network.NVGTogglePacket;
import ru.liko.warbornrenewed.network.NetworkHandler;

/**
 * Client-side handler for the vision toggle key (NVG / Thermal helmets).
 */
@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = WarbornRenewed.MODID, value = Dist.CLIENT)
public class NVGToggleHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player == null) {
            return;
        }

        // Check if the vision toggle key was pressed
        if (KeyBindings.NVG_TOGGLE.consumeClick()) {
            handleVisionToggle(player);
        }
    }

    private static void handleVisionToggle(Player player) {
        // Check helmet slot for supported vision capability (NVG or Thermal)
        ItemStack helmet = player.getInventory().getArmor(3); // Helmet slot

        if (helmet.isEmpty() || !(helmet.getItem() instanceof WarbornArmorItem armorItem)) {
            return;
        }

        boolean supportsNVG = armorItem.hasVisionCapability(WarbornArmorItem.TAG_NVG);
        boolean supportsThermal = armorItem.hasVisionCapability(WarbornArmorItem.TAG_THERMAL);

        if (!supportsNVG && !supportsThermal) {
            return;
        }

        // Toggle generic vision state
        boolean currentState = WarbornArmorItem.isNVGDown(helmet);
        boolean newState = !currentState;
        WarbornArmorItem.setNVGDown(helmet, newState);

        // Animation will be handled by the AnimationController
        // which reads the NBT state

        // Send packet to server to sync state
        NetworkHandler.sendToServer(new NVGTogglePacket(newState));
    }
}
