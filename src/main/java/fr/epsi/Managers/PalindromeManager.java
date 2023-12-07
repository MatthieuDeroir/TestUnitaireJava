package fr.epsi.Managers;
import fr.epsi.Enums.eInput;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;


public class PalindromeManager {

    // Check if the user input is a palindrome
    public static String Check(String userInput, ResourceBundle messages) {
        if (userInput == null || userInput.isEmpty()) {
            return messages.getString(eInput.INVALID.getKey());
        }

        String reversedInput = ReverseString(userInput);

        if (userInput.equalsIgnoreCase(reversedInput)) {
            return messages.getString("palindrome.response");
        } else {
            return reversedInput;
        }
    }

    // Reverse a String
    private static String ReverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}

