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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.Nullable;
import ru.liko.warbornrenewed.WarbornRenewed;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = WarbornRenewed.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
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

            try (Stream<Path> stream = Files.list(packsDir)) {
                stream.filter(Files::isDirectory)
                      .forEach(packPath -> {
                          String packId = packPath.getFileName().toString();
                          // In 1.20.1 Forge, use standard Pack.readMetaAndCreate or similar
                          // Forge 1.20.1 signature might be different, checking previous usage
                          // Previous: Pack.readMetaAndCreate("warborn_dynamic_models", ...)
                          
                          // We use PathPackResources for each pack
                          event.addRepositorySource(consumer -> {
                              Pack pack = Pack.readMetaAndCreate(
                                      "warborn_pack_" + packId,
                                      Component.literal("Warborn: " + packId),
                                      false,
                                      (id) -> new net.minecraft.server.packs.PathPackResources(id, packPath, true),
                                      PackType.CLIENT_RESOURCES,
                                      Pack.Position.TOP,
                                      PackSource.BUILT_IN
                              );
                              if (pack != null) {
                                  consumer.accept(pack);
                              }
                          });
                      });
            } catch (Exception e) {
                WarbornRenewed.LOGGER.error("Failed to list packs for resource injection", e);
            }
        }
    }

    // Remove custom implementation as we use PathPackResources now
}
