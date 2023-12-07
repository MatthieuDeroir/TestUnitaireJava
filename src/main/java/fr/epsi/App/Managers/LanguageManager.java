package fr.epsi.App.Managers;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import fr.epsi.App.Enums.eLang;
import fr.epsi.App.Services.TranslationService;


public class LanguageManager {
    private static final Map<String, String> languageMap;
    private static final List<String> languages;

    static {
        try {
            languageMap = initLanguageMap();
            languages = new ArrayList<>(languageMap.keySet());
            updateResourceBundles();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static final String LANG_DEFAULT = eLang.LANG_EN.getKey();

    private final TranslationService translationService;

    public LanguageManager(TranslationService translationService) {
        this.translationService = translationService;
    }

    private static Map<String, String> initLanguageMap() throws IOException, InterruptedException {
        Map<String, String> map = new HashMap<>();

        try {
            // Chemin vers le dossier resources
            Path resourcesPath = Paths.get(ClassLoader.getSystemResource("").toURI());

            // Recherche des fichiers de bundle
            Files.walk(resourcesPath)
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        String fileName = filePath.getFileName().toString();
                        if (fileName.startsWith("messages_") && fileName.endsWith(".properties")) {
                            String langCode = fileName.substring(9, fileName.length() - 11); // Extrait le code langue
                            map.put(langCode, langCode);
                        }
                    });
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return map;
    }


    private static void updateResourceBundles() throws IOException, InterruptedException {
        ResourceBundle englishBundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);

        int totalLanguages = languages.size() - 1; // Subtract 1 for English
        int processedLanguages = 0;

        System.out.println("Updating resource bundles...");

        for (String lang : languages) {
            if (!lang.equals("en")) {

                ResourceBundle currentBundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag(lang));
                Properties properties = bundleToProperties(currentBundle);

                boolean updated = addMissingKeys(englishBundle, properties, lang);
                updated |= removeObsoleteKeys(englishBundle, properties);

                if (updated) {
                    String ressourcesPath = "src/main/resources/";
                    savePropertiesToFile(properties, ressourcesPath + "messages_" + lang + ".properties");
                } else {
                }

                processedLanguages++;
                //Clear the console
                try {
                    new ProcessBuilder("/bin/bash", "-c", "clear").inheritIO().start().waitFor();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.printf("Progress: %.2f%%\n", (processedLanguages / (double) totalLanguages) * 100);
            }
        }

        System.out.println("Resource bundle updates completed.");
    }

    private static boolean addMissingKeys(ResourceBundle englishBundle, Properties properties, String targetLang) throws IOException, InterruptedException {
        boolean updated = false;
        for (String key : englishBundle.keySet()) {
            if (!properties.containsKey(key)) {
                String translatedValue = TranslationService.translate(englishBundle.getString(key), targetLang);
                properties.put(key, translatedValue);
                updated = true;
            }
        }
        return updated;
    }

    private static boolean removeObsoleteKeys(ResourceBundle englishBundle, Properties properties) {
        boolean updated = false;
        List<String> keysToRemove = new ArrayList<>();

        for (String key : properties.stringPropertyNames()) {
            if (!englishBundle.containsKey(key)) {
                keysToRemove.add(key);
                updated = true;
            }
        }

        keysToRemove.forEach(key -> {
            properties.remove(key);
        });

        return updated;
    }

    private static Properties bundleToProperties(ResourceBundle bundle) {
        Properties properties = new Properties();
        bundle.keySet().forEach(key -> properties.put(key, bundle.getString(key)));
        return properties;
    }

    private static void savePropertiesToFile(Properties properties, String filePath) throws IOException {
        try (OutputStream output = new FileOutputStream(filePath)) {
            properties.store(output, null);
        }
    }


    // Get the language resources from the user
    public static ResourceBundle getLanguageResourcesFromUser(Scanner sc) throws IOException, InterruptedException {
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
    public static ResourceBundle getLanguageResources(String lang) throws IOException, InterruptedException {
        System.out.println("Language: " + lang);
        if (lang == null || lang.isEmpty()) {
            System.err.println("No language provided, using default.");
            return ResourceBundle.getBundle("messages", Locale.forLanguageTag(LANG_DEFAULT));
        } else if (!languageMap.containsKey(lang)) {
            System.err.println("No locale found for language " + lang + ". Translating...");
            try {
                return getTranslatedResourceBundle(lang);
            } catch (Exception e) {
                System.err.println("An error occurred while translation: " + e.getMessage());
                System.err.println("Using default language:" + LANG_DEFAULT + ".");
                return ResourceBundle.getBundle("messages", Locale.forLanguageTag(LANG_DEFAULT));
            }
        } else {
            System.out.println("Using language " + lang);
            return ResourceBundle.getBundle("messages", Locale.forLanguageTag(lang));
        }
    }

    private static ResourceBundle getTranslatedResourceBundle(String langCode) throws IOException, InterruptedException {
        System.out.println("Starting translation for language code: " + langCode);

        // Obtenez le contenu du bundle par défaut (messages_en.properties)
        ResourceBundle defaultBundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag(LANG_DEFAULT));
        System.out.println("Default bundle loaded for language: " + LANG_DEFAULT);

        Properties properties = new Properties();

        // Traduisez chaque valeur de clé
        for (String key : defaultBundle.keySet()) {
            String originalValue = defaultBundle.getString(key);

            String translatedValue;
            try {
                translatedValue = TranslationService.translate(originalValue, langCode);
                //remove "+" from translated value
                translatedValue = translatedValue.replaceAll("\\+", "");
            } catch (Exception e) {
                translatedValue = originalValue; // Utiliser la valeur originale en cas d'erreur
            }

            properties.put(key, translatedValue);
        }

        // Créer un nouveau fichier properties avec les traductions
        String ressourcesPath = "src/main/resources/";
        File file = new File(ressourcesPath + "messages_" + langCode + ".properties");
        try (FileOutputStream fileOut = new FileOutputStream(file);
             OutputStreamWriter writer = new OutputStreamWriter(fileOut, StandardCharsets.UTF_8)) {
            properties.store(writer, "Translated resource bundle for language: " + langCode);
        }

        // Retournez un nouveau ResourceBundle basé sur le fichier créé
        return new PropertyResourceBundle(new FileInputStream(file));
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
        System.out.println("User language: " + userLang);
        return userLang.isEmpty() ? LANG_DEFAULT : userLang;
    }
}