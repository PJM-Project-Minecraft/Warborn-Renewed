package ru.liko.warbornrenewed;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;

public class Constants {
    public static final String MODID = "warbornrenewed";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}