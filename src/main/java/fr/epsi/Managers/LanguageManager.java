package fr.epsi.Managers;

import java.util.*;


public class LanguageManager {
    private static final String LANG_FR = "fr";
    private static final String LANG_EN = "en";
    private static final String LANG_ES = "es";
    public static final String LANG_DEFAULT = LANG_EN;


    // Get the language resources from the user
    public static ResourceBundle getLanguageResourcesFromUser(Scanner sc) {
        try {
            System.out.println("Automatic Language Scan ? (Y/N)");
            String userLang = sc.nextLine().equalsIgnoreCase("Y") ? getSystemLanguage() : getUserSelectedLanguage(sc);
            String langCode = Optional.of(userLang)
                    .filter(lang -> Arrays.asList(LANG_FR, LANG_EN, LANG_ES).contains(lang))
                    .orElse(LANG_DEFAULT);
            Locale locale = Locale.forLanguageTag(langCode);
            return ResourceBundle.getBundle("messages", locale);
        } catch (MissingResourceException e) {
            throw new MissingResourceException("No resource bundle found for " + LANG_DEFAULT, "messages", LANG_DEFAULT);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return getLanguageResources(LANG_DEFAULT);
        }
    }

    // Get the language resources from a String parameter
    private static final Set<String> SUPPORTED_LANGUAGES = new HashSet<>(Arrays.asList(LANG_FR, LANG_EN, LANG_ES));

    public static ResourceBundle getLanguageResources(String lang) {
        if (!SUPPORTED_LANGUAGES.contains(lang)) {
            throw new MissingResourceException("No resource bundle found for " + lang, "messages", lang);
        }

        return ResourceBundle.getBundle("messages", new Locale(lang));
    }


    // Get the language resources from the system
    public static String getSystemLanguage() {
        try {
            return System.getProperty("user.language", LANG_DEFAULT);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return LANG_DEFAULT;
        }
    }

    // Get the language resources from the user
    private static String getUserSelectedLanguage(Scanner sc) {
        System.out.println("Please enter your language (fr/en/es)");
        String userLang = sc.nextLine();

        switch (userLang.toLowerCase()) {
            case LANG_FR:
            case LANG_EN:
            case LANG_ES:
                return userLang;
            default:
                return LANG_DEFAULT;
        }
    }
}