package transfarmer.arbitrarypatches;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import transfarmer.arbitrarypatches.event.Events;
import transfarmer.farmerlib.event.TranslationEvent;

public class Main implements ModInitializer {
    public static final String MOD_ID = "arbitrarypatches";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        TranslationEvent.MANAGER.register(Events::onTranslation);
    }
}
