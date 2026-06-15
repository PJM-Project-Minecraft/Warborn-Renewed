package ru.liko.warbornrenewed.client;

import net.minecraft.client.Minecraft;
import ru.liko.warbornrenewed.packs.WarbornPackManager;

public class ClientPacksReloader {
    public static void reloadClient() {
        Minecraft.getInstance().execute(() -> {
            WarbornPackManager.loadPacks();
            Minecraft.getInstance().reloadResourcePacks();
        });
    }
}