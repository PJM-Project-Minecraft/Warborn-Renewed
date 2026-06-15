package ru.liko.warbornrenewed.platform;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.DistExecutor;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public java.nio.file.Path getGameDir() {
        return net.minecraftforge.fml.loading.FMLPaths.GAMEDIR.get();
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getCurrentLocale() {
        return DistExecutor.runForDist(
                () -> () -> ru.liko.warbornrenewed.client.ClientLocaleHelper.getLanguageCode(),
                () -> () -> "en_us");
    }
}
