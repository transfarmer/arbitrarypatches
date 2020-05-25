package transfarmer.arbitrarypatches.mixin.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import transfarmer.arbitrarypatches.config.Configuration;

import java.util.Random;

@Mixin(SoulSandBlock.class)
public abstract class SoulSandBlockMixin extends BlockMixin<SoulSandBlock> {
    @Unique
    private static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 16);

    @Inject(method = "getCollisionShape", at = @At("RETURN"), cancellable = true)
    public void getCollisionShape(final BlockState state, final BlockView view, final BlockPos pos,
                                  final EntityContext context, final CallbackInfoReturnable<VoxelShape> info) {
        if (Configuration.instance().brownSand) {
            info.setReturnValue(COLLISION_SHAPE);
        }
    }

    public float getVelocityMultiplier() {
        return Configuration.instance().brownSand ? 1 : super.getVelocityMultiplier();
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(final BlockState state, final Direction facing,
                                                final BlockState neighborState, final IWorld world, final BlockPos pos,
                                                final BlockPos neighborPos) {
        if (Configuration.instance().brownSand) {
            world.getBlockTickScheduler().schedule(pos, thiz(), this.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos);
    }

    @Inject(method = "scheduledTick", at = @At("RETURN"))
    public void scheduledTick(final BlockState state, final ServerWorld world, final BlockPos pos, final Random random,
                              final CallbackInfo info) {
        if (Configuration.instance().brownSand && FallingBlock.canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= 0) {
            world.spawnEntity(new FallingBlockEntity(world,
                    pos.getX() + 0.5,
                    pos.getY(),
                    pos.getZ() + 0.5,
                    world.getBlockState(pos)
            ));
        }
    }

    @Inject(method = "getTickRate", at = @At("RETURN"), cancellable = true)
    public void getTickRate(final WorldView worldView, final CallbackInfoReturnable<Integer> info) {
        if (Configuration.instance().brownSand) {
            info.setReturnValue(2);
        }
    }

    @Environment(EnvType.CLIENT)
    @SuppressWarnings("unused")
    public void randomDisplayTick(final BlockState state, final World world, final BlockPos pos, final Random random) {
        if (Configuration.instance().brownSand && random.nextInt(16) == 0
                && FallingBlock.canFallThrough(world.getBlockState(pos.down()))) {
            world.addParticle(new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, state),
                    pos.getX() + random.nextFloat(),
                    pos.getY() - 0.05,
                    pos.getZ() + random.nextFloat(),
                    0, 0, 0
            );
        }
    }
}
