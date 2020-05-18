package transfarmer.arbitrarypatches;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import transfarmer.arbitrarypatches.config.ClientConfig;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class MainClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register((final BlockState state, final BlockRenderView view, final BlockPos pos, final int index) -> 0x5B4538, Blocks.SOUL_SAND);
        ColorProviderRegistry.ITEM.register((final ItemStack stack, final int tintIndex) -> 0x5B4538, Items.SOUL_SAND);

        AutoConfig.register(ClientConfig.class, JanksonConfigSerializer::new);
    }
}
