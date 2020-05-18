package transfarmer.arbitrarypatches.mixin.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SoulSandBlock.class)
public abstract class MixinSoulSandBlock extends MixinBlock<SoulSandBlock> {
    @Shadow
    @Final
    @Mutable
    protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 16);

    @Inject(method = "<init>(Lnet/minecraft/block/Block$Settings;)V", at = @At("RETURN"))
    public void constructor(final Block.Settings settings, final CallbackInfo info) {
        this.setVelocityMultiplier(1);
    }

    @SuppressWarnings("unused")
    public void onBlockAdded(final BlockState state, final World world, final BlockPos pos, final BlockState oldState,
                             final boolean moved) {
        world.getBlockTickScheduler().schedule(pos, thiz(), this.getTickRate(world));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(final BlockState state, final Direction facing,
                                                final BlockState neighborState, final IWorld world, final BlockPos pos,
                                                final BlockPos neighborPos) {
        world.getBlockTickScheduler().schedule(pos, thiz(), this.getTickRate(world));

        return super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }

    @Inject(method = "scheduledTick", at = @At("TAIL"))
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random,
                              final CallbackInfo info) {
        if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0) {
            final FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, world.getBlockState(pos));

            world.spawnEntity(fallingBlockEntity);
        }
    }

    @Inject(method = "getTickRate", at = @At("RETURN"), cancellable = true)
    public void getTickRate(final WorldView worldView, final CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(2);
    }

    public boolean canFallThrough(final BlockState state) {
        final Material material = state.getMaterial();

        return state.isAir() || state.getBlock() == Blocks.FIRE || material.isLiquid() || material.isReplaceable();
    }

    @Environment(EnvType.CLIENT)
    @SuppressWarnings("unused")
    public void randomDisplayTick(final BlockState state, final World world, final BlockPos pos, final Random random) {
        if (random.nextInt(16) == 0) {
            if (this.canFallThrough(world.getBlockState(pos.down()))) {
                final double x = pos.getX() + random.nextFloat();
                final double y = pos.getY() - 0.05;
                final double z = pos.getZ() + random.nextFloat();

                world.addParticle(new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, state), x, y, z, 0, 0, 0);
            }
        }

    }

    @Environment(EnvType.CLIENT)
    public int getColor(final BlockState state) {
        return 0xFFFFFF;
    }
}
