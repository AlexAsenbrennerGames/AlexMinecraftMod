package net.alex.alexmod.item;

import net.alex.alexmod.AlexMod;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AlexMod.MOD_ID);

    public static final RegistryObject<Item> testItem = ITEMS.register("test_item", () -> new Item(new Item.Properties().stacksTo(128).tab(ModeCreativeModeTab.ALEX_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
