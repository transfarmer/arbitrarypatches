package transfarmer.arbitrarypatches.mixin.client.gui.widget;

import net.minecraft.client.gui.widget.SoundSliderWidget;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import transfarmer.arbitrarypatches.duck.client.gui.widget.SoundSliderWidgetDuck;

@Mixin(SoundSliderWidget.class)
public class SoundSliderWidgetMixin implements SoundSliderWidgetDuck {
    @Shadow @Final private SoundCategory category;

    @Override
    public SoundCategory getCategory() {
        return this.category;
    }
}
