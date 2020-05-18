package transfarmer.arbitrarypatches.config;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry.Gui.Tooltip;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import transfarmer.arbitrarypatches.Main;

@Environment(EnvType.CLIENT)
@Config(name = Main.MOD_ID)
public class ClientConfig implements ConfigData {
    @Tooltip
    public boolean hideRecipeBook;
    @Tooltip
    public boolean tabClosesScreen;

    public static ClientConfig instance() {
        return AutoConfig.getConfigHolder(ClientConfig.class).getConfig();
    }
}
