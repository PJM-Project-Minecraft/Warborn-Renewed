package ru.liko.warbornrenewed.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import ru.liko.warbornrenewed.WarbornRenewed;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            WarbornRenewed.MODID);

    public static final RegistryObject<CreativeModeTab> ARMOR = TABS.register("armor",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.warbornrenewed.armor"))
                    .icon(() -> ModItems.armorPieces().stream()
                            .findFirst()
                            .map(entry -> new ItemStack(entry.get()))
                            .orElseGet(() -> new ItemStack(Items.NETHERITE_HELMET)))
                    .displayItems((parameters, output) -> {
                        // Добавляем всю броню
                        ModItems.armorPieces().forEach(entry -> output.accept(entry.get().getDefaultInstance()));
                        // Добавляем бинокль
                        output.accept(ModItems.BINOCULAR.get().getDefaultInstance());
                        // Добавляем рюкзаки РЭБ
                        ModItems.rebBackpacks().forEach(entry -> output.accept(entry.get().getDefaultInstance()));
                    })
                    .build());

    private ModCreativeTabs() {
    }

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
