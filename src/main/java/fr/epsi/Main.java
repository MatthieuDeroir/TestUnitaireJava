package fr.epsi;

import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

    private static final String LANG_FR = "fr";
    private static final String LANG_EN = "en";
    private static final String LANG_ES = "es";
    private static final String LANG_DEFAULT = LANG_EN;


    public static void main(String[] args) {
        String userLang = "en";
        boolean run = true;
        Scanner sc = new Scanner(System.in);
        ResourceBundle messages = LanguageManager.getLanguageResources(sc);

        //recuperer l'heure actuelle

        int heure = GetHour();

        // Ecrire Bonjour ou bonsoir en fonction de l'heure
        while (run) {
            greetUser(heure, messages);
            // Demander à l'utilisateur de saisir un mot et récupérer la saisie utilisateur
            String userinput = askInput(sc, messages);

            // Inverser la saisie utilisateur
            String reversedUserInput = ReverseString(userinput);

            // Si la saisie utilisateur est un palindrome afficher "Bien-dit !" dans la langue de l'utilisateur
            palindrome(userinput, reversedUserInput, messages);

            // Demander à l'utilisateur s'il veut continuer
            run = askContinue(sc, messages);
        }

        // Dire au revoir dans la langue de l'utilisateur
        sayGoodbye(messages);

    }

    static String ReverseString(String userinput) {
        return new StringBuffer(userinput).reverse().toString();
    }

    static String CapitalizeString(String userinput) {
        return userinput.substring(0, 1).toUpperCase() + userinput.substring(1);
    }

    static Integer GetHour() {
        return Calendar.HOUR_OF_DAY;
    }

    static String askInput(Scanner sc, ResourceBundle messages) {
        System.out.println(messages.getString("input.prompt"));
        return sc.nextLine().toLowerCase();
    }

    static void greetUser(int hour, ResourceBundle messages) {
        if (hour < 12) {
            System.out.println(messages.getString("greeting.morning"));
        } else if (hour < 18) {
            System.out.println(messages.getString("greeting.afternoon"));
        } else {
            System.out.println(messages.getString("greeting.evening"));
        }
    }

    static void sayGoodbye(ResourceBundle messages) {
        System.out.println(messages.getString("greeting.goodbye"));
    }

    static void palindrome(String userinput, String reversedUserInput, ResourceBundle messages) {
        if (userinput.equals(reversedUserInput)) {
            System.out.println(messages.getString("palindrome.response"));
        } else {
            reversedUserInput = CapitalizeString(reversedUserInput);
            System.out.println(reversedUserInput);
        }
    }

    static Boolean askContinue(Scanner sc, ResourceBundle messages) {
        System.out.println(messages.getString("continue.prompt"));
        String userinput = sc.nextLine();
        return !userinput.equals("N") && !userinput.equals("n");
    }
}