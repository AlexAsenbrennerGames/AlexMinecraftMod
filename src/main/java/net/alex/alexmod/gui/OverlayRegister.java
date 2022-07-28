package net.alex.alexmod.gui;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OverlayRegister {

    public OverlayRegister() {
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }

    @SubscribeEvent
    public void onRegister(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("test", new TestOverlay());

    }
}
