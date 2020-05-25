package transfarmer.arbitrarypatches;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import transfarmer.arbitrarypatches.client.render.block.entity.SoulSandBlockEntityRenderer;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class MainClient implements ClientModInitializer {
    public static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(Main.SOUL_SAND, SoulSandBlockEntityRenderer::new);
    }
}
