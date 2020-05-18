package transfarmer.arbitrarypatches.mixin.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Block.class)
public abstract class MixinBlock<T> {
    @Shadow
    @Final
    @Mutable
    private float velocityMultiplier;

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

    protected void setVelocityMultiplier(final float velocityMultiplier) {
        this.velocityMultiplier = velocityMultiplier;
    }

    @SuppressWarnings("unchecked")
    public T thiz() {
        return (T) this;
    }
}
