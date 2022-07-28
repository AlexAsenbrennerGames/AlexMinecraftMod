package net.alex.alexmod;

import com.mojang.logging.LogUtils;
import net.alex.alexmod.fastleafdecay.FldScheduler;
import net.alex.alexmod.gui.OverlayRegister;
import net.alex.alexmod.item.ModItems;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AlexMod.MOD_ID)
public class AlexMod
{
    public static final String MOD_ID = "alexmod";
    private static final Logger LOGGER = LogUtils.getLogger();
    static Random random = new Random();

    public AlexMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::notifyNeighbors);
        FldScheduler var10001 = FldScheduler.INSTANCE;
        Objects.requireNonNull(var10001);
        MinecraftForge.EVENT_BUS.addListener(var10001::tick);
        modEventBus.register(new OverlayRegister());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }

        @SubscribeEvent
        public static void onRegister(RegisterGuiOverlaysEvent event)
        {
            System.out.println(event.toString());
        }
    }

    public void onRegister(RegisterGuiOverlaysEvent event)
    {
        System.out.println(event.toString());
    }

    public void notifyNeighbors(BlockEvent.NeighborNotifyEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (!levelAccessor.isClientSide()) {
            BlockState notifierState = event.getState();
            BlockPos blockPos = event.getPos();
            // Unsure if this is the right value
            if (notifierState.isRandomlyTicking()) {
                Iterator var5 = event.getNotifiedSides().iterator();

                while(var5.hasNext()) {
                    Direction facing = (Direction)var5.next();
                    BlockPos offPos = blockPos.relative(facing);
                    if (levelAccessor.isAreaLoaded(offPos, 1)) {
                        BlockState state = levelAccessor.getBlockState(offPos);
                        if (state.is(BlockTags.LEAVES)) {
                            int minimum = 4;
                            int jitter = 11;
                            int delay = minimum + random.nextInt(jitter);
                            FldScheduler.INSTANCE.schedule(levelAccessor, offPos, delay);
                        }
                    }
                }
            }
        }

    }
}
