package transfarmer.arbitrarypatches.event;

import net.minecraft.util.Language;
import transfarmer.anvil.event.Listener;
import transfarmer.anvilevents.event.TranslationEvent;
import transfarmer.arbitrarypatches.config.Configuration;

public class Listeners {
    @Listener
    public static void onTranslation(final TranslationEvent event) {
        if (Configuration.instance().brownSand) {
            final String key = event.getKey();

            if (!key.equals("color.minecraft.brown") && !key.contains("text.autoconfig.arbitrarypatches.option.brownSand.@Tooltip")) {
                final String value = event.getValue();
                final String[] words = value.split(" ");

                for (int i = 0, length = words.length; i < length; i++) {
                    final String word = words[i];
                    if (!word.toLowerCase().contains("soulbound")) {
                        final String soul = key.equals("block.minecraft.soul_sand") ? words[0] : Language.getInstance().translate("block.minecraft.soul_sand").split(" ")[0];
                        final String brown = Language.getInstance().translate("color.minecraft.brown");
                        final boolean upper;

                        if (word.contains(soul)) {
                            upper = true;
                        } else if (word.contains(soul.toLowerCase())) {
                            upper = false;
                        } else {
                            continue;
                        }

                        words[i] = word.replaceFirst(upper ? soul : soul.toLowerCase(), upper ? brown : brown.toLowerCase());
                    }
                }

                event.setValue(String.join(" ", words));
            }
        }
    }
}
