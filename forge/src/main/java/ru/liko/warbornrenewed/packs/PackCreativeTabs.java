package ru.liko.warbornrenewed.packs;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import ru.liko.warbornrenewed.WarbornRenewed;
import ru.liko.warbornrenewed.packs.PackDef;
import ru.liko.warbornrenewed.platform.Services;
import ru.liko.warbornrenewed.registry.ModItems;

import java.util.List;

/**
 * Динамическая регистрация креативных вкладок для каждого загруженного пака.
 * Вызывать registerPackTabs() ПОСЛЕ WarbornPackManager.loadPacks()
 * и register(eventBus) ПОСЛЕ registerPackTabs().
 */
public class PackCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> PACK_TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, WarbornRenewed.MODID);

    /**
     * Создаёт вкладку для каждого обнаруженного пака.
     * Должен быть вызван после WarbornPackManager.loadPacks().
     */
    public static void registerPackTabs() {
        for (String packName : WarbornPackManager.getPackNames()) {
            PackDef packInfo = WarbornPackManager.getPackInfo(packName);
            String tabTitle = packInfo != null && packInfo.getTabName() != null ? 
                    packInfo.getTabName() : "WRB: " + formatPackName(packName);

            PACK_TABS.register("pack_" + packName, () -> CreativeModeTab.builder()
                    .title(Component.literal(tabTitle))
                    .icon(() -> createIconStack(packName, packInfo))
                    .displayItems((params, output) -> {
                        List<ArmorDef> defs = WarbornPackManager.getPackDefs(packName);
                        for (ArmorDef def : defs) {
                            Item item = getItemForType(def.getType());
                            if (item != null) {
                                ItemStack stack = new ItemStack(item);
                                Services.ITEM_DATA.setArmorPackId(stack, def.getId());
                                output.accept(stack);
                            }
                        }
                    })
                    .build()
            );
        }
    }

    public static void register(IEventBus eventBus) {
        PACK_TABS.register(eventBus);
    }

    private static ItemStack createIconStack(String packName, PackDef packInfo) {
        if (packInfo != null && packInfo.getIconItem() != null) {
            String iconId = packInfo.getIconItem();
            
            // Check if it's a dynamic armor item first
            ArmorDef armorDef = WarbornPackManager.getArmorDef(iconId);
            if (armorDef != null) {
                Item item = getItemForType(armorDef.getType());
                if (item != null) {
                    ItemStack stack = new ItemStack(item);
                    Services.ITEM_DATA.setArmorPackId(stack, armorDef.getId());
                    return stack;
                }
            }

            net.minecraft.resources.ResourceLocation iconLoc;
            if (iconId.contains(":")) {
                iconLoc = new net.minecraft.resources.ResourceLocation(iconId.split(":")[0], iconId.split(":")[1]);
            } else {
                iconLoc = new net.minecraft.resources.ResourceLocation("warbornrenewed", iconId);
            }
            Item iconItem = net.minecraftforge.registries.ForgeRegistries.ITEMS.getValue(iconLoc);
            if (iconItem != null && iconItem != Items.AIR) {
                ItemStack stack = new ItemStack(iconItem);
                
                // If it's one of our mod's dynamic items, try associating the icon id as armor def id
                if (!iconId.contains(":") || iconId.startsWith("warbornrenewed:")) {
                    String cleanId = iconId.contains(":") ? iconId.split(":")[1] : iconId;
                    if (WarbornPackManager.getArmorDef(cleanId) != null) {
                        Services.ITEM_DATA.setArmorPackId(stack, cleanId);
                    }
                }
                return stack;
            }
        }

        List<ArmorDef> defs = WarbornPackManager.getPackDefs(packName);
        if (!defs.isEmpty()) {
            Item item = getItemForType(defs.get(0).getType());
            if (item != null) {
                ItemStack stack = new ItemStack(item);
                Services.ITEM_DATA.setArmorPackId(stack, defs.get(0).getId());
                return stack;
            }
        }
        return new ItemStack(Items.LEATHER_HELMET);
    }

    private static Item getItemForType(String type) {
        if (type == null) return ModItems.PACK_HELMET.get();
        return switch (type.toLowerCase()) {
            case "chestplate" -> ModItems.PACK_CHESTPLATE.get();
            case "leggings" -> ModItems.PACK_LEGGINGS.get();
            case "boots" -> ModItems.PACK_BOOTS.get();
            default -> ModItems.PACK_HELMET.get();
        };
    }

    private static String formatPackName(String packName) {
        // example_pack -> Example Pack
        String[] parts = packName.split("[_\\-]");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                if (sb.length() > 0) sb.append(' ');
                sb.append(Character.toUpperCase(part.charAt(0)));
                if (part.length() > 1) sb.append(part.substring(1));
            }
        }
        return sb.toString();
    }
}
