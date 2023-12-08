package fr.epsi.App.Managers;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import fr.epsi.App.Managers.ResourceBundleFactory;
import fr.epsi.App.Services.LanguageTranslationService;
import fr.epsi.App.Utils.ConsoleUtils;


public class LanguageManager {
    private static final Map<String, String> languageMap;
    private static final List<String> languages;
    private static ConsoleUtils console = new ConsoleUtils();
    // get the default language from the config.properties file
    public static final String LANG_DEFAULT = ResourceBundle.getBundle("config").getString("LANG_DEFAULT");
    static List<String> acceptedLanguages;
    ResourceBundleFactory bundleFactory = new ResourceBundleFactory(languages, LANG_DEFAULT);


    static {
        try {
            acceptedLanguages = LanguageTranslationService.getSupportedLanguages();
            languageMap = initLanguageMap();
            languages = new ArrayList<>(languageMap.keySet());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public LanguageManager() throws IOException, InterruptedException {
    }



    // Initialize the language map by searching for the files in the resources folder
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

    // Get the language resources from the user
    public ResourceBundle getLanguageResourcesFromUser(Scanner sc) throws IOException, InterruptedException {
        try {
            System.out.println("Automatic Language Scan ? (Y/N)");
            String userLang = sc.nextLine().equalsIgnoreCase("Y") ? getSystemLanguage() : getUserSelectedLanguage(sc);
            // replace the value of the key "LANG_DEFAULT" in the config.properties file
            ResourceBundle config = ResourceBundle.getBundle("config");
            config.keySet().stream().filter(key -> key.equals("LANG_DEFAULT")).forEach(key -> {
                try {
                    config.getString(key);
                    config.keySet().stream().filter(key1 -> key1.equals("LANG_DEFAULT")).forEach(key1 -> {
                        try {
                            config.getString(key1);
                        } catch (MissingResourceException e) {
                            System.err.println("No resource bundle found, using default.");
                        }
                    });
                } catch (MissingResourceException e) {
                    System.err.println("No resource bundle found, using default.");
                }
            });
            return getLanguageResources(userLang);
        } catch (MissingResourceException e) {
            System.err.println("No resource bundle found, using default.");
            return getLanguageResources(LANG_DEFAULT);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return getLanguageResources(LANG_DEFAULT);
        }
    }

    // Get the language resources from the user
    private String getUserSelectedLanguage(Scanner sc) {
        System.out.println("Please enter your language using digraphs (default: " + LANG_DEFAULT + ")");
        String userLang = sc.nextLine();
        if (userLang.equalsIgnoreCase("default")) {
            return LANG_DEFAULT;
        } else if (userLang.equalsIgnoreCase("auto")) {
            return getSystemLanguage();
        } else if (!acceptedLanguages.contains(userLang)) {
            System.err.println("Language not supported, retry.");
            System.out.println("Supported languages: " + acceptedLanguages);
            return getUserSelectedLanguage(sc);
        }
        return userLang;
    }

    // Get the language resources from a String parameter
    public ResourceBundle getLanguageResources(String lang) throws IOException, InterruptedException {
        if (lang == null || lang.isEmpty()) {
            System.err.println("No language provided, using default.");
            return bundleFactory.getResourceBundle(LANG_DEFAULT);
        } else if (!languageMap.containsKey(lang)) {
            System.out.println("No locale found for language " + lang + ". Translating...");
            try {
                return bundleFactory.createResourceBundle(lang);
            } catch (Exception e) {
                System.err.println("An error occurred while translation: " + e.getMessage());
                System.err.println("Using default language:" + LANG_DEFAULT + ".");
                return bundleFactory.getResourceBundle(LANG_DEFAULT);
            }
        } else {
            System.out.println("SELECTED LANGUAGE : " + lang);
            return bundleFactory.getResourceBundle(lang);
        }
    }

    private ResourceBundle getTranslatedResourceBundle(String langCode) throws IOException, InterruptedException {
        System.out.println("Starting translation for language code: " + langCode);

        // Obtenez le contenu du bundle par défaut (messages_en.properties)
        ResourceBundle defaultBundle = bundleFactory.getResourceBundle(LANG_DEFAULT);
        System.out.println("Default bundle loaded for language: " + LANG_DEFAULT);

        Properties properties = new Properties();
        int totalKeys = defaultBundle.keySet().size();
        int processedKeys = 0;

        // Traduisez chaque valeur de clé
        for (String key : defaultBundle.keySet()) {
            String originalValue = defaultBundle.getString(key);

            String translatedValue;
            try {
                translatedValue = LanguageTranslationService.translate(originalValue, langCode);
                translatedValue = translatedValue.replaceAll("\\+", " "); // Nettoyer la valeur traduite
                System.out.println(originalValue + " --> " + translatedValue);

            } catch (Exception e) {
                translatedValue = originalValue; // Utiliser la valeur originale en cas d'erreur
            }

            properties.put(key, translatedValue);
            processedKeys++;

            int progressPercentage = (int) (((double) processedKeys / totalKeys) * 100);
            console.ClearConsoleAndPrint("Translation progress: " + progressPercentage + "%");
        }

        // Créer un nouveau fichier properties avec les traductions
        String resourcesPath = "src/main/resources/";
        File file = new File(resourcesPath + "messages_" + langCode + ".properties");
        try (FileOutputStream fileOut = new FileOutputStream(file);
             OutputStreamWriter writer = new OutputStreamWriter(fileOut, StandardCharsets.UTF_8)) {
            properties.store(writer, "Translated resource bundle for language: " + langCode);
        }

        System.out.println("TRANSLATION COMPLETED: " + langCode);

        // Retournez un nouveau ResourceBundle basé sur le fichier créé
        return new PropertyResourceBundle(new FileInputStream(file));
    }


    // Get the language resources from the system
    public String getSystemLanguage() {
        try {
            return System.getProperty("user.language", LANG_DEFAULT);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return LANG_DEFAULT;
        }
    }
}