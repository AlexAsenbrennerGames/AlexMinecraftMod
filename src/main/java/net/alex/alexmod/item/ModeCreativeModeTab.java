package net.alex.alexmod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModeCreativeModeTab {
    public static final CreativeModeTab ALEX_TAB = new CreativeModeTab("alextab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.testItem.get());
        }
    };
}
