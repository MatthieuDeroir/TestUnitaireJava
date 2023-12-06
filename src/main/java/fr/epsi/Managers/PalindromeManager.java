package fr.epsi.Managers;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PalindromeManager {


    // Check if the user input is a palindrome
    // Getting the user input from the IOManager
    public static String Check(IOManager ioManager, ResourceBundle messages) {
        String userInput = ioManager.GetInput();
        String reversedInput = ReverseString(userInput);

        if (userInput.equalsIgnoreCase(reversedInput)) {
            return reversedInput + System.lineSeparator() +  messages.getString("palindrome.response");
        } else {
            return reversedInput;
        }
    }

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

    public static String ReverseString(String input) {
        if (input.isEmpty()) {
            return input;
        }
        return new StringBuilder(input)
                .reverse()
                .toString();
    }
}
