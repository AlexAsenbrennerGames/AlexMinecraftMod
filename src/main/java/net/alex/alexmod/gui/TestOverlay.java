package net.alex.alexmod.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class TestOverlay implements IGuiOverlay {
    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        Minecraft.getInstance().font.draw(poseStack, "Hello World", 2, 2, 0xFFFFFFFF);
    }
}
