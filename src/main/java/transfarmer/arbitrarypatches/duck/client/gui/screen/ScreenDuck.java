package transfarmer.arbitrarypatches.duck.client.gui.screen;

import net.minecraft.client.gui.widget.AbstractButtonWidget;

public interface ScreenDuck {
    <T extends AbstractButtonWidget> T addButton(final T button);
}
