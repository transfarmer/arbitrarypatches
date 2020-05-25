package transfarmer.arbitrarypatches.mixin.client.gui.screen.ingame;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import transfarmer.arbitrarypatches.config.Configuration;
import transfarmer.farmerlib.MainClient;

@Environment(EnvType.CLIENT)
@Mixin(ContainerScreen.class)
public abstract class ContainerScreenMixin extends ScreenMixin {
    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    public void keyPressed(final int keyCode, final int scanCode, final int modifiers,
                           final CallbackInfoReturnable<Boolean> info) {
        if (keyCode == GLFW.GLFW_KEY_TAB && MainClient.CLIENT.options.keyInventory.matchesKey(keyCode, scanCode)
                && modifiers == 0 && Configuration.instance().client.tabClosesScreen) {
            this.onClose();
            info.setReturnValue(true);
        }
    }
}
