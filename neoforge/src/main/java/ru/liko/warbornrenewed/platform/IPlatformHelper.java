package ru.liko.warbornrenewed.platform;

import net.minecraft.core.component.DataComponentType;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Get the base game directory.
     *
     * @return The base game directory.
     */
    java.nio.file.Path getGameDir();

    // Add methods for handling platform-specific DataComponent access if necessary,
    // though DataComponent manipulation on ItemStack is typically standard vanilla code in 1.20.5+
    
}
