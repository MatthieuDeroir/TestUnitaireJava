package fr.epsi.Managers;

import java.util.*;

import fr.epsi.Enums.eLang;


public class LanguageManager {
    private static final Map<String, String> languageMap = initLanguageMap();
    public static final String LANG_DEFAULT = eLang.LANG_EN.getKey();

    private static Map<String, String> initLanguageMap() {
        Map<String, String> map = new HashMap<>();
        map.put("fr", eLang.LANG_FR.getKey());
        map.put("en", eLang.LANG_EN.getKey());
        map.put("es", eLang.LANG_ES.getKey());
        map.put("de", eLang.LANG_DE.getKey());
        map.put("it", eLang.LANG_IT.getKey());
        map.put("pt", eLang.LANG_PT.getKey());
        map.put("ru", eLang.LANG_RU.getKey());
        map.put("zh", eLang.LANG_ZH.getKey());
        map.put("ja", eLang.LANG_JA.getKey());
        map.put("ko", eLang.LANG_KO.getKey());
        map.put("ar", eLang.LANG_AR.getKey());
        map.put("hi", eLang.LANG_HI.getKey());
        map.put("pl", eLang.LANG_PL.getKey());
        map.put("ro", eLang.LANG_RO.getKey());
        return map;
    }

    // Get the language resources from the user
    public static ResourceBundle getLanguageResourcesFromUser(Scanner sc) {
        try {
            System.out.println("Automatic Language Scan ? (Y/N)");
            String userLang = sc.nextLine().equalsIgnoreCase("Y") ? getSystemLanguage() : getUserSelectedLanguage(sc);
            return getLanguageResources(userLang);
        } catch (MissingResourceException e) {
            System.err.println("No resource bundle found, using default.");
            return getLanguageResources(LANG_DEFAULT);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return getLanguageResources(LANG_DEFAULT);
        }
    }

    // Get the language resources from a String parameter
    public static ResourceBundle getLanguageResources(String lang) {
        String langCode = Optional.ofNullable(languageMap.get(lang))
                .orElse(LANG_DEFAULT);
        Locale locale = Locale.forLanguageTag(langCode);
        return ResourceBundle.getBundle("messages", locale);
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
        System.out.println("Please enter your language (fr, en, es, ...): (default: " + LANG_DEFAULT + ")");
        String userLang = sc.nextLine().toLowerCase();
        return languageMap.getOrDefault(userLang, LANG_DEFAULT);
    }
}