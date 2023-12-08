package fr.epsi.ManagersTest;

import fr.epsi.App.Managers.LanguageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LanguageManagerTest {

    @Mock
    private Scanner mockScanner;

    private LanguageManager languageManager;

    @BeforeEach
    public void setUp() {
    }

    @ParameterizedTest
    @ValueSource(strings = {"fr", "en", "es", "de", "it", "pt", "ru", "zh", "ja", "ko", "hi", "ar", "ro"})
    public void getLanguageResourcesFromUser_ValidLanguage(String lang) throws IOException, InterruptedException {
        when(mockScanner.nextLine()).thenReturn(lang);
        ResourceBundle resources = languageManager.getLanguageResourcesFromUser(mockScanner);
        assertEquals(lang, resources.getLocale().getLanguage());
    }

    @Test
    public void getLanguageResourcesFromUser_InvalidLanguage() throws IOException, InterruptedException {
        when(mockScanner.nextLine()).thenReturn("invalid_language");
        ResourceBundle resources = languageManager.getLanguageResourcesFromUser(mockScanner);
        assertEquals(LanguageManager.LANG_DEFAULT, resources.getLocale().getLanguage());
    }

    @Test
    public void getLanguageResourcesFromUser_AutomaticLanguageScan() throws IOException, InterruptedException {
        when(mockScanner.nextLine()).thenReturn("Y");
        ResourceBundle resources = languageManager.getLanguageResourcesFromUser(mockScanner);
        String expectedLanguage = languageManager.getSystemLanguage();
        assertEquals(expectedLanguage, resources.getLocale().getLanguage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"fr", "en", "es", "de", "it", "pt", "ru", "zh", "ja", "ko", "hi", "ar", "ro"})
    public void getLanguageResources_ValidLanguage(String lang) throws IOException, InterruptedException {
        ResourceBundle resources = languageManager.getLanguageResources(lang);
        assertEquals(lang, resources.getLocale().getLanguage());
    }

    @Test
    public void getLanguageResources_InvalidLanguage() {
        assertThrows(MissingResourceException.class, () -> {
            languageManager.getLanguageResources("invalid_language");
        });
    }
}
