package transfarmer.arbitrarypatches.mixin.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Block.class)
public abstract class BlockMixin<T> {
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Shadow
    @Deprecated
    public BlockState getStateForNeighborUpdate(final BlockState state, final Direction facing,
                                                         final BlockState neighborState, final IWorld world,
                                                         final BlockPos pos, final BlockPos neighborPos) {
        return null;
    }

    @Shadow
    public abstract int getTickRate(final WorldView worldView);

    @Shadow public float getVelocityMultiplier() {
        return 0;
    }

    @SuppressWarnings("unchecked")
    public T thiz() {
        return (T) this;
    }
}
