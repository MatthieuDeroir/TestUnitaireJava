package fr.epsi.App.Managers;

import java.io.*;
import java.util.*;

import fr.epsi.App.Services.LanguageTranslationService;

public class ResourceBundleFactory {

    public List<String> languages;
    private final String ressourceBundleName = "messages";
    private final String resourceBundlePath = "src/main/resources/";
    private final String ressourceBundleExtension = ".properties";
    private final String ressourceBundleDefault = "en";

    public ResourceBundleFactory(List<String> existingLanguages, String defaultLang) throws IOException, InterruptedException {
        this.languages = existingLanguages;
        updateResourceBundles(defaultLang);
    }

    public ResourceBundle getResourceBundle(String lang) {
        return ResourceBundle.getBundle(ressourceBundleName, Locale.forLanguageTag(lang));
    }


    public ResourceBundle createResourceBundle(String langCode) throws IOException, InterruptedException {
        if (langCode.equals(LanguageManager.LANG_DEFAULT)) {
            return getResourceBundle(langCode);
        }

        System.out.println("Starting translation for language code: " + langCode);
        ResourceBundle defaultBundle = getResourceBundle(LanguageManager.LANG_DEFAULT);
        Properties properties = new Properties();

        for (String key : defaultBundle.keySet()) {
            String originalValue = defaultBundle.getString(key);
            String translatedValue = LanguageTranslationService.translate(originalValue, langCode);
            // replace "+" with " " in the translated value
            translatedValue = translatedValue.replaceAll("\\+", " ");
            System.out.println("Translated: " + originalValue + " -> " + translatedValue);
            properties.put(key, translatedValue);
            // Afficher la progression, etc.
        }


        savePropertiesToFile(properties, langCode);
        String filePath = this.resourceBundlePath + ressourceBundleName + "_" + langCode + ressourceBundleExtension;
        return new PropertyResourceBundle(new FileInputStream(filePath));
    }


    public void updateResourceBundles(String defaultLang) throws IOException, InterruptedException {
        ResourceBundle englishBundle = getResourceBundle(defaultLang);
        int totalLanguages = languages.size() - 1; // Exclude English
        int processedLanguages = 0;

        System.out.println("Updating resource bundles...");

        for (String lang : languages) {
            if (!lang.equals(defaultLang)) {
                System.out.println("Processing language bundle: " + lang);

                ResourceBundle currentBundle = getResourceBundle(lang);
                Properties properties = bundleToProperties(currentBundle);

                boolean updated = addMissingKeys(englishBundle, properties, lang);
                updated |= removeObsoleteKeys(englishBundle, properties);

                if (updated) {
                    savePropertiesToFile(properties, lang);
                    System.out.println("Resource bundle for '" + lang + "' updated.");
                } else {
                    System.out.println("No updates needed for '" + lang + "'.");
                }

                processedLanguages++;
                int progressPercentage = (int) ((processedLanguages / (double) totalLanguages) * 100);
                System.out.println("Progress: " + progressPercentage + "%");
            }
        }

        System.out.println("Resource bundle updates completed.");
    }


    private boolean addMissingKeys(ResourceBundle englishBundle, Properties properties, String targetLang) throws IOException, InterruptedException {
        boolean updated = false;
        for (String key : englishBundle.keySet()) {
            if (!properties.containsKey(key)) {
                String translatedValue = LanguageTranslationService.translate(englishBundle.getString(key), targetLang);
                properties.put(key, translatedValue);
                updated = true;
            }
        }
        return updated;
    }

    private boolean removeObsoleteKeys(ResourceBundle englishBundle, Properties properties) {
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

    private Properties bundleToProperties(ResourceBundle bundle) {
        Properties properties = new Properties();
        bundle.keySet().forEach(key -> properties.put(key, bundle.getString(key)));
        return properties;
    }

    private void savePropertiesToFile(Properties properties, String lang) throws IOException {
        String filePath = resourceBundlePath + ressourceBundleName + "_" + lang + ressourceBundleExtension;
        try (OutputStream output = new FileOutputStream(filePath)) {
            properties.store(output, null);
        }
    }
}
