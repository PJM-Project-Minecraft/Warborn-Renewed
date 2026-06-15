package ru.liko.warbornrenewed.packs;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.jetbrains.annotations.Nullable;
import ru.liko.warbornrenewed.Warbornrenewed;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EventBusSubscriber(modid = Warbornrenewed.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PackResourceInjector {

    private static final Set<String> ALLOWED_ROOTS = Set.of("models", "textures", "animations", "lang", "sounds", "shaders", "geo");

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path gameDir = FMLPaths.GAMEDIR.get();
            Path packsDir = gameDir.resolve("warbornrenewed/packs");

            if (!Files.isDirectory(packsDir)) {
                try {
                    Files.createDirectories(packsDir);
                } catch (Exception e) {
                    System.err.println("[WarbornPacks] Failed to create packs directory: " + packsDir);
                    return;
                }
            }

            // Register each subfolder as a separate pack
            try (Stream<Path> stream = Files.list(packsDir)) {
                stream.filter(Files::isDirectory)
                      .forEach(packPath -> {
                          String packId = packPath.getFileName().toString();
                          if (!ResourceLocation.isValidNamespace(packId)) {
                              Warbornrenewed.LOGGER.warn("Skipping invalid pack directory name: {}", packId);
                              return;
                          }

                          // Use PathPackResources which is standard and safe
                          Pack.ResourcesSupplier resources = new net.minecraft.server.packs.PathPackResources.PathResourcesSupplier(packPath);
                          
                          Pack pack = Pack.readMetaAndCreate(
                                  new net.minecraft.server.packs.PackLocationInfo("warborn_pack_" + packId, Component.literal("Warborn: " + packId), PackSource.BUILT_IN, Optional.empty()),
                                  resources,
                                  PackType.CLIENT_RESOURCES,
                                  new net.minecraft.server.packs.PackSelectionConfig(true, Pack.Position.TOP, false)
                          );

                          if (pack != null) {
                              event.addRepositorySource(consumer -> consumer.accept(pack));
                          }
                      });
            } catch (Exception e) {
                Warbornrenewed.LOGGER.error("Failed to list packs for resource injection", e);
            }
        }
    }

    // Remove custom implementation as we use PathPackResources now
}
