package fr.epsi.ManagersTest;

import fr.epsi.Managers.LanguageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LanguageManagerTest {

    @Mock
    private Scanner mockScanner;

    @BeforeEach
    public void setUp() {
    }

    @ParameterizedTest
    @ValueSource(strings = {"fr", "en", "es"})
    public void getLanguageResourcesFromUser_ValidLanguage(String lang) {
        when(mockScanner.nextLine()).thenReturn(lang);
        ResourceBundle resources = LanguageManager.getLanguageResourcesFromUser(mockScanner);
        assertEquals(lang, resources.getLocale().getLanguage());
    }

    @Test
    public void getLanguageResourcesFromUser_InvalidLanguage() {
        when(mockScanner.nextLine()).thenReturn("invalid_language");
        ResourceBundle resources = LanguageManager.getLanguageResourcesFromUser(mockScanner);
        assertEquals(LanguageManager.LANG_DEFAULT, resources.getLocale().getLanguage());
    }

    @Test
    public void getLanguageResourcesFromUser_AutomaticLanguageScan() {
        when(mockScanner.nextLine()).thenReturn("Y");
        ResourceBundle resources = LanguageManager.getLanguageResourcesFromUser(mockScanner);
        String expectedLanguage = LanguageManager.getSystemLanguage();
        assertEquals(expectedLanguage, resources.getLocale().getLanguage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"fr", "en", "es"})
    public void getLanguageResources_ValidLanguage(String lang) {
        ResourceBundle resources = LanguageManager.getLanguageResources(lang);
        assertEquals(lang, resources.getLocale().getLanguage());
    }

    @Test
    public void getLanguageResources_InvalidLanguage() {
        assertThrows(MissingResourceException.class, () -> {
            LanguageManager.getLanguageResources("invalid_language");
        });
    }
}
