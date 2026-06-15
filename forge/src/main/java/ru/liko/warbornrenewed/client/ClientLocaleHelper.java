package ru.liko.warbornrenewed.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ClientLocaleHelper {

    private ClientLocaleHelper() {
    }

    /**
     * Returns the current client language code (e.g. "en_us", "ru_ru").
     * Only call from client side.
     */
    public static String getLanguageCode() {
        return Minecraft.getInstance().getLanguageManager().getSelected();
    }
}
