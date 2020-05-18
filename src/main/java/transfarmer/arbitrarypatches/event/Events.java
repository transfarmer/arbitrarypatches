package transfarmer.arbitrarypatches.event;

import transfarmer.farmerlib.event.TranslationEvent;

public class Events {
    public static void onTranslation(final TranslationEvent event) {
        final String value = event.getValue();
        final String[] words = value.split(" ");

        for (int i = 0, length = words.length; i < length; i++) {
            final String word = words[i];
            if (!word.toLowerCase().contains("soulbound")) {
                final boolean upper;

                if (word.contains("Soul")) {
                    upper = true;
                } else if (word.contains("soul")) {
                    upper = false;
                } else {
                    continue;
                }

                words[i] = word.replaceFirst(upper ? "Soul" : "soul", upper ? "Brown" : "brown");
            }
        }

        event.setValue(String.join(" ", words));
    }
}
