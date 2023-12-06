package fr.epsi;

public class PalindromeManager {


//    public static String Check(String userInput) {
//        String reversedInput = ReverseString(userInput);

//        if (userInput.equalsIgnoreCase(reversedInput)) {
//            return messages.getString("palindrome.response");
//        } else {
//            return reversedInput;
//        }
//    }

    public static String ReverseString(String input) {
        return new StringBuilder(input)
                .reverse()
                .toString();
    }

    private static String CapitalizeString(String input) {
        if (input.isEmpty()) {
            return input;
        }
        return Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();
    }
}
