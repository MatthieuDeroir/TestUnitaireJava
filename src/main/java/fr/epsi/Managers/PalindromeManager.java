package fr.epsi.Managers;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PalindromeManager {
    // Check if the user input is a palindrome
    // Getting the user input from a String parameter
    public static String Check(String userInput, ResourceBundle messages) {
        String reversedInput = ReverseString(userInput);

        if (userInput.equalsIgnoreCase(reversedInput)) {
            return reversedInput + System.lineSeparator() +  messages.getString("palindrome.response");
        } else {
            return reversedInput;
        }
    }

    // Reverse a String
    public static String ReverseString(String input) {
        if (input.isEmpty()) {
            return input;
        }
        return new StringBuilder(input)
                .reverse()
                .toString();
    }
}
