package transfarmer.arbitrarypatches.config;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config.Gui.Background;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Category;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Gui.Excluded;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Gui.Tooltip;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Gui.TransitiveObject;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import transfarmer.arbitrarypatches.Main;

@Config(name = Main.MOD_ID)
@Background("minecraft:textures/block/andesite.png")
public class Configuration implements ConfigData {
    @Tooltip(count = 3)
    public boolean brownSand = true;

    @TransitiveObject
    @Category("client")
    @Environment(EnvType.CLIENT)
    public final Client client = new Client();

    public static Configuration instance() {
        if (Instance.instance == null) {
            try {
                Instance.instance = AutoConfig.getConfigHolder(Configuration.class).getConfig();
            } catch (final RuntimeException exception) {
                AutoConfig.register(Configuration.class, Toml4jConfigSerializer::new);
                Instance.instance = AutoConfig.getConfigHolder(Configuration.class).getConfig();
            }
        }

        return Instance.instance;
    }

    protected static class Instance {
        protected static Configuration instance;
    }

    public static class Client {
        @Environment(EnvType.CLIENT)
        @Tooltip
        public boolean hideRecipeBook = true;
        @Environment(EnvType.CLIENT)
        @Tooltip(count = 2)
        public boolean tabClosesScreen = true;
        @Environment(EnvType.CLIENT)
        @Tooltip
        public boolean disableMusic = true;

        @Environment(EnvType.CLIENT)
        @Excluded
        protected boolean brownSand = false;

        @Environment(EnvType.CLIENT)
        public void refresh() {
            this.tryDisableMusic();
            this.tryToggleBrownSand();
        }

        @Environment(EnvType.CLIENT)
        public void tryDisableMusic() {
            if (instance().client.disableMusic) {
                MinecraftClient.getInstance().options.setSoundVolume(SoundCategory.MUSIC, 0);
            }
        }

        @Environment(EnvType.CLIENT)
        public void tryToggleBrownSand() {
            final Configuration main = instance();

            if (this.brownSand != main.brownSand) {
                final int color;

                if (main.brownSand) {
                    color  = 0x5B4538;
                } else {
                    color = 0xFFFFFF;
                }

                ColorProviderRegistry.BLOCK.register((final BlockState state, final BlockRenderView view, final BlockPos pos, final int index) -> color, Blocks.SOUL_SAND);
                ColorProviderRegistry.ITEM.register((final ItemStack stack, final int tintIndex) -> color, Items.SOUL_SAND);

                final MinecraftClient client = MinecraftClient.getInstance();

                if (client.world != null) {
                    client.worldRenderer.reload();
                }

                this.brownSand = main.brownSand;
            }
        }
    }
}
