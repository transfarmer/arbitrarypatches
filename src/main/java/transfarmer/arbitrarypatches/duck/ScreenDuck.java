package transfarmer.arbitrarypatches.duck;

import net.minecraft.client.gui.widget.AbstractButtonWidget;

public interface ScreenDuck {
    <T extends AbstractButtonWidget> T addButton(final T button);
}
