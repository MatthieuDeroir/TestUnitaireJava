package fr.espi;
import fr.epsi.Managers.PalindromeManager;
import fr.epsi.Managers.LanguageManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PalindromeTest {
    @ParameterizedTest
    @ValueSource (strings = {"jean", "sifflet", "gourde"})
    public void testMiroir(String chaîne) {
        // ETANT DONNÉ une chaine de caractère
        // QUAND on verifie si c'est un palindrome
        String résultat = PalindromeManager.ReverseString(chaîne);

        String inversion = new StringBuilder(chaîne).reverse().toString();

        // ALORS on obtient son miroir
        assertEquals(inversion, résultat);
    }

    @ParameterizedTest
    @CsvSource
            ({
                    "kayak, fr",
                    "radar, en",
                    "ressasser, es",
                    "s.o.s, fr",
                    "été, en",
                    "reconocer, es"
            })

    public void testPalindrome(String chaîne, String lang) {
        // ETANT DONNÉ une chaine de caractère
        // QUAND on verifie si c'est un palindrome
        Locale locale = new Locale(lang);
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        String résultat = PalindromeManager.Check(chaîne, messages);

        // ALORS on obtient un message de confirmation
        String attendu = new StringBuilder(chaîne).reverse().toString() + System.lineSeparator() + LanguageManager.getLanguageResources(lang).getString("palindrome.response");
        assertEquals(attendu, résultat);
    }


}
