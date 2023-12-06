package fr.epsi;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;


class LanguageManager {
    private static final String LANG_FR = "fr";
    private static final String LANG_EN = "en";
    private static final String LANG_ES = "es";
    private static final String LANG_DEFAULT = LANG_EN;

    static ResourceBundle getLanguageResources(Scanner sc) {
        System.out.println("Automatic Language Scan ? (O/N)");
        String userLang = sc.nextLine().equalsIgnoreCase("O") ? getSystemLanguage() : getUserSelectedLanguage(sc);

        Locale locale = new Locale(userLang);
        return ResourceBundle.getBundle("messages", locale);
    }

    private static String getSystemLanguage() {
        return System.getProperty("user.language", LANG_DEFAULT);
    }

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