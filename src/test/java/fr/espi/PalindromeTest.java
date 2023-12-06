package fr.espi;
import fr.epsi.PalindromeManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PalindromeTest {
    @ParameterizedTest
    @ValueSource (strings = {"kayak", "radar", "ressasser", "s.o.s", "été", "jean", "sifflet", "gourde"})
    public void testMiroir(String chaîne) {
        // ETANT DONNÉ une chaine de caractère
        // QUAND on verifie si c'est un palindrome
        String résultat = PalindromeManager.ReverseString(chaîne);

        String inversion = new StringBuilder(chaîne).reverse().toString();

        // ALORS on obtient son miroir
        assertEquals(inversion, résultat);
    }
}
