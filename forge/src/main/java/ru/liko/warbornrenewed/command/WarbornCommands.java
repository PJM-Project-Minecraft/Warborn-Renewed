package ru.liko.warbornrenewed.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.liko.warbornrenewed.WarbornRenewed;
import ru.liko.warbornrenewed.packs.WarbornPackManager;

@Mod.EventBusSubscriber(modid = WarbornRenewed.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WarbornCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("warbornrenewed")
                        .then(Commands.literal("reload")
                                .requires(source -> source.hasPermission(2))
                                .executes(WarbornCommands::reloadPacks)
                        )
        );
    }

    private static int reloadPacks(CommandContext<CommandSourceStack> context) {
        try {
            int beforeCount = WarbornPackManager.getAllArmorDefs().size();
            WarbornPackManager.loadPacks();
            int afterCount = WarbornPackManager.getAllArmorDefs().size();

            // Notify all clients to reload their resource packs
            ru.liko.warbornrenewed.network.NetworkHandler.sendToAllClients(new ru.liko.warbornrenewed.network.ReloadPacksPacket());

            context.getSource().sendSuccess(
                    () -> Component.literal("§a[WarbornRenewed] §fPacks reloaded! Loaded §e" + afterCount + "§f armor definition(s)."),
                    true
            );

            if (beforeCount != afterCount) {
                context.getSource().sendSuccess(
                        () -> Component.literal("§7(was " + beforeCount + ", now " + afterCount + ")"),
                        false
                );
            }

            return afterCount;
        } catch (Exception e) {
            context.getSource().sendFailure(
                    Component.literal("§c[WarbornRenewed] §fFailed to reload packs: " + e.getMessage())
            );
            WarbornRenewed.LOGGER.error("Failed to reload packs", e);
            return 0;
        }
    }
}
