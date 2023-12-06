package fr.epsi.testsu;

import java.util.Calendar;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        String userLang = System.getProperty("user.language");
        userLang = "fr";
        Scanner sc = new Scanner(System.in);


        boolean run = true;
        //recuperer l'heure actuelle

        int heure = Calendar.HOUR_OF_DAY;

        // Ecrire Bonjour ou bonsoir en fonction de l'heure
        while (run) {
            switch (userLang) {
                case "fr" -> {
                    if (heure < 18) {
                        System.out.println("Bonjour");
                    } else {
                        System.out.println("Bonsoir");
                    }
                }
                case "en" -> {
                    if (heure < 12) {
                        System.out.println("Good morning");
                    } else if (heure < 18) {
                        System.out.println("Good afternoon");
                    } else {
                        System.out.println("Good evening");
                    }
                }
                case "es" -> {
                    if (heure < 12) {
                        System.out.println("Buenos dias");
                    } else if (heure < 18) {
                        System.out.println("Buenas tardes");
                    } else {
                        System.out.println("Buenas noches");
                    }
                }
                default -> System.out.println("Hello");
            }
            // Demander à l'utilisateur de saisir un mot
            switch (userLang) {
                case "fr" -> System.out.println("Veuillez saisir un mot");
                case "en" -> System.out.println("Please enter a word");
                case "es" -> System.out.println("Por favor ingrese una palabra");
                default -> System.out.println("Please enter a word");
            }
            // Récuperer une saisie utilisateur
            String userinput = sc.nextLine();
            // lowerCase la saisie utilisateur
            userinput = userinput.toLowerCase();

            // Inverser la saisie utilisateur
            String reverse = new StringBuffer(userinput).reverse().toString();

            // Si la saisie utilisateur est un palindrome afficher "Bien-dit !" dans la langue de l'utilisateur
            if (userinput.equals(reverse)) {
                switch (userLang) {
                    case "fr" -> System.out.println("Bien-dit !");
                    case "en" -> System.out.println("Well said !");
                    case "es" -> System.out.println("Bien dicho !");
                    default -> System.out.println("Well said !");
                }
            } else {
                // Sinon afficher la saisie utilisateur inversée avec la première lettre en majuscule
                reverse = reverse.substring(0, 1).toUpperCase() + reverse.substring(1);
                System.out.println(reverse);
            }

            // Demander à l'utilisateur s'il veut continuer
            switch (userLang) {
                case "fr" -> System.out.println("Voulez-vous continuer ? (O/N)");
                case "en" -> System.out.println("Do you want to continue ? (Y/N)");
                case "es" -> System.out.println("¿Quieres continuar ? (S/N)");
                default -> System.out.println("Do you want to continue ? (Y/N)");
            }
            // Récuperer la saisie utilisateur
            userinput = sc.nextLine();
            // Si la saisie utilisateur est "N" ou "n" arrêter le programme
            if (userinput.equals("N") || userinput.equals("n")) {
                run = false;
            }
        }

        // Dire au revoir dans la langue de l'utilisateur
        switch (userLang) {
            case "fr" -> System.out.println("Au revoir");
            case "en" -> System.out.println("Goodbye");
            case "es" -> System.out.println("Adios");
            default -> System.out.println("Goodbye");
        }

    }
}