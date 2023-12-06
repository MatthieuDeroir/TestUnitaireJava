package fr.epsi.Managers;

import java.util.*;

import fr.epsi.Enums.eLang;


public class LanguageManager {
    public static String LANG_FR = eLang.LANG_FR.getKey();
    public static String LANG_EN = eLang.LANG_EN.getKey();
    public static String LANG_ES = eLang.LANG_ES.getKey();
    public static String LANG_DE = eLang.LANG_DE.getKey();
    public static String LANG_IT = eLang.LANG_IT.getKey();
    public static String LANG_PT = eLang.LANG_PT.getKey();
    public static String LANG_RU = eLang.LANG_RU.getKey();
    public static String LANG_ZH = eLang.LANG_ZH.getKey();
    public static String LANG_JA = eLang.LANG_JA.getKey();
    public static String LANG_KO = eLang.LANG_KO.getKey();
    public static String LANG_AR = eLang.LANG_AR.getKey();
    public static String LANG_PL = eLang.LANG_PL.getKey();
    public static String LANG_RO = eLang.LANG_RO.getKey();
    public static String LANG_HI = eLang.LANG_HI.getKey();
    public static final String LANG_DEFAULT = LANG_EN;

    public static final List<String> SUPPORTED_LANGUAGES = Arrays.asList(LANG_FR, LANG_EN, LANG_ES, LANG_DE, LANG_IT, LANG_PT, LANG_RU, LANG_ZH, LANG_JA, LANG_KO, LANG_AR, LANG_PL, LANG_RO, LANG_HI);

    // Get the language resources from the user
    public static ResourceBundle getLanguageResourcesFromUser(Scanner sc) {
        try {
            System.out.println("Automatic Language Scan ? (Y/N)");
            String userLang = sc.nextLine().equalsIgnoreCase("Y") ? getSystemLanguage() : getUserSelectedLanguage(sc);
            String langCode = Optional.of(userLang)
                    .filter(SUPPORTED_LANGUAGES::contains)
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
    public static ResourceBundle getLanguageResources(String lang) {
        if (!SUPPORTED_LANGUAGES.contains(lang)) {
            throw new MissingResourceException("No resource bundle found for " + lang, "messages", lang);
        }

        return ResourceBundle.getBundle("messages", Locale.forLanguageTag(lang));
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
        System.out.println("Please enter your language (fr, en, es, de, it, pt, ru, zh, ja, ko, ar, hi, pl, ro): (default: " + LANG_DEFAULT + "");
        String userLang = sc.nextLine().toLowerCase();

        switch (userLang) {
            case "fr":
                return eLang.LANG_FR.getKey();
            case "en":
                return eLang.LANG_EN.getKey();
            case "es":
                return eLang.LANG_ES.getKey();
            case "de":
                return eLang.LANG_DE.getKey();
            case "it":
                return eLang.LANG_IT.getKey();
            case "pt":
                return eLang.LANG_PT.getKey();
            case "ru":
                return eLang.LANG_RU.getKey();
            case "zh":
                return eLang.LANG_ZH.getKey();
            case "ja":
                return eLang.LANG_JA.getKey();
            case "ko":
                return eLang.LANG_KO.getKey();
            case "ar":
                return eLang.LANG_AR.getKey();
            case "hi":
                return eLang.LANG_HI.getKey();
            case "pl":
                return eLang.LANG_PL.getKey();
            case "ro":
                return eLang.LANG_RO.getKey();
            default:
                System.out.println("Invalid language: " + userLang + ", using default language (" + LANG_DEFAULT + ")");
                return LANG_DEFAULT;
        }
    }
}