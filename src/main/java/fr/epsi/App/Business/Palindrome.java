package fr.epsi.App.Business;

import fr.epsi.App.Enums.Weekdays;

import java.util.ResourceBundle;


public class Palindrome {

    // Check if the user input is a palindrome
    public static String Check(String userInput, ResourceBundle messages) {
        if (userInput == null || userInput.isEmpty()) {
            return messages.getString(Weekdays.INVALID.getKey());
        }

        String reversedInput = ReverseString(userInput);

        if (userInput.equalsIgnoreCase(reversedInput)) {
            return reversedInput + System.lineSeparator() + messages.getString(Weekdays.CONGRATULATIONS.getKey());
        } else {
            return reversedInput;
        }
    }

    // Reverse a String
    private static String ReverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}

