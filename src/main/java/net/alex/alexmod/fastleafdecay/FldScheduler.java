package net.alex.alexmod.fastleafdecay;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.LogicalSide;

public class FldScheduler {
    public static FldScheduler INSTANCE = new FldScheduler();
    List<FldScheduler.BlockUpdate> plannedUpdates = new ArrayList();
    List<FldScheduler.BlockUpdate> scheduledUpdates = new ArrayList();

    public void schedule(LevelAccessor levelAccessor, BlockPos pos, int delay) {
        this.plannedUpdates.add(new FldScheduler.BlockUpdate(levelAccessor, pos, delay));
    }

    public void tick(ServerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == Phase.END) {
            if (!this.plannedUpdates.isEmpty()) {
                this.scheduledUpdates.addAll(this.plannedUpdates);
                this.plannedUpdates.clear();
            }
            Iterator iterator = this.scheduledUpdates.iterator();

            while(iterator.hasNext()) {
                FldScheduler.BlockUpdate scheduledUpdate = (FldScheduler.BlockUpdate)iterator.next();
                --scheduledUpdate.tick;
                if (scheduledUpdate.tick <= 0) {
                    iterator.remove();
                    LevelAccessor levelAccessor = (LevelAccessor)scheduledUpdate.levelAccessorReference.get();
                    if (levelAccessor != null && levelAccessor.isAreaLoaded(scheduledUpdate.pos, 1)) {
                        BlockState state = levelAccessor.getBlockState(scheduledUpdate.pos);
                        if (state.is(BlockTags.LEAVES)) {
                            state.tick((ServerLevel)levelAccessor, scheduledUpdate.pos, levelAccessor.getRandom());
                            state.randomTick((ServerLevel)levelAccessor, scheduledUpdate.pos, levelAccessor.getRandom());
                        }
                    }
                }
            }
        }

    }

    class BlockUpdate {
        WeakReference<LevelAccessor> levelAccessorReference;
        BlockPos pos;
        int tick;

        public BlockUpdate(LevelAccessor levelAccessor, BlockPos pos, int tick) {
            this.levelAccessorReference = new WeakReference(levelAccessor);
            this.pos = pos;
            this.tick = tick;
        }
    }
}
