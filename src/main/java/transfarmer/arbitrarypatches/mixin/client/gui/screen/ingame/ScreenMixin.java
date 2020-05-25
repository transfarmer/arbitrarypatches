package transfarmer.arbitrarypatches.mixin.client.gui.screen.ingame;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import transfarmer.arbitrarypatches.duck.client.gui.screen.ScreenDuck;

@Environment(EnvType.CLIENT)
@Mixin(Screen.class)
public abstract class ScreenMixin implements ScreenDuck {
    @Shadow public abstract void onClose();
}
