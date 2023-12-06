package fr.espi;

import fr.epsi.Managers.PalindromeManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PalindromeManagerTest {

    public void SetUp() {
        // Configuration initiale si nécessaire
    }


    @ParameterizedTest
    @CsvSource({
            "jean, fr",
            "sifflet, en",
            "gourde, es"
    })
    public void checkPalindrome_ReturnOnlyMirroredString(String chaine, String lang) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale(lang));

        // ETANT DONNÉ une chaine de caractère
        // QUAND on verifie si c'est un palindrome
        String resultat = PalindromeManager.Check(chaine, messages);

        String inversion = new StringBuilder(chaine).reverse().toString();

        // ALORS on obtient son miroir
        assertEquals(inversion, resultat);
    }

    @ParameterizedTest
    @CsvSource({
            "kayak, fr",
            "radar, en",
            "ressasser, es",
            "s.o.s, fr",
            "été, en",
            "reconocer, es"
    })
    public void checkPalindrome_ReturnPalindromWithMessageInUsersSelectedLanguage(String chaine, String lang) {
        // ETANT DONNÉ une chaine de caractère
        // QUAND on verifie si c'est un palindrome
        ResourceBundle messages = ResourceBundle.getBundle("messages", new Locale(lang));

        String resultat = PalindromeManager.Check(chaine, messages);

        // ALORS on obtient un message de confirmation
        String attendu = new StringBuilder(chaine).reverse().toString() + System.lineSeparator() + messages.getString("palindrome.response");
        assertEquals(attendu, resultat);
    }


}
