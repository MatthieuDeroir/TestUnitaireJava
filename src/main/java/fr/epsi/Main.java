package fr.epsi;

import fr.epsi.Enums.eGreeting;
import fr.epsi.Managers.IOManager;
import fr.epsi.Managers.LanguageManager;
import fr.epsi.Managers.PalindromeManager;

import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ResourceBundle messages = LanguageManager.getLanguageResourcesFromUser(sc);
        IOManager ioManager = new IOManager(sc, messages);

        do {
            // Ecrire Bonjour ou bonsoir en fonction de l'heure et de la langue de l'utilisateur
            ioManager.GetGreetingMessage();
            // Demander à l'utilisateur de saisir un mot et récupérer la saisie utilisateur
            // Si la saisie utilyisateur est un palindrome afficher "Bien-dit !" dans la langue de l'utilisateur
            // Sinon afficher la saisie utilisateur inversée
            System.out.println(PalindromeManager.Check(ioManager.GetInput(), messages));
            // Demander à l'utilisateur s'il veut continuer
        } while (ioManager.AskToContinue());
        // Dire au revoir dans la langue de l'utilisateur
        ioManager.ShowMessage(eGreeting.GOODBYE.getKey());

    }
}