package fr.epsi.ManagersTest;

import fr.epsi.App.Business.Palindrome;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PalindromeManagerTest {
    @ParameterizedTest
    @CsvSource({
            "jean, fr",
            "sifflet, en",
            "gourde, es",
            "silencio, it",
            "schweigen, de",
            "silêncio, pt",
            "tăcere, ro",
            "тишина, ru",
            "寂静, zh",
            "静寂, ja",
            "고요, ko",
            "चुप्पी, hi",
    })
    public void checkPalindrome_ReturnOnlyMirroredString(String chaine, String lang) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", Locale.forLanguageTag(lang));

        // ETANT DONNÉ une chaine de caractère
        // QUAND on verifie si c'est un palindrome
        String resultat = Palindrome.Check(chaine, messages);

        String inversion = new StringBuilder(chaine).reverse().toString();

        // ALORS on obtient son miroir
        assertEquals(inversion, resultat);
    }

    @ParameterizedTest
    @CsvSource({
            "s.o.s, fr",
            "radar, en",
            "reconocer, es",
            "reliefpfeiler, de",
            "anna, it",
            "arara, pt",
            "acca, ro",
            "шалаш, ru",
            "上海自来水来自海上, zh",
            "たけやぶやけた, ja",
            "기러기, ko",
            "नमन, hi",
    })
    public void checkPalindrome_ReturnPalindromWithMessageInUsersSelectedLanguage(String chaine, String lang) {
        // ETANT DONNÉ une chaine de caractère
        // QUAND on verifie si c'est un palindrome
        ResourceBundle messages = ResourceBundle.getBundle("messages", Locale.forLanguageTag(lang));

        String resultat = Palindrome.Check(chaine, messages);

        // ALORS on obtient un message de confirmation
        String attendu = new StringBuilder(chaine).reverse() + System.lineSeparator() + messages.getString("palindrome.response");
        assertEquals(attendu, resultat);
    }
}
