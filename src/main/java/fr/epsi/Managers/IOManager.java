package fr.epsi.Managers;


import fr.epsi.Enums.EGreeting;

import java.util.ResourceBundle;
import java.util.Scanner;

public class IOManager {

    private final Scanner sc;
    private final ResourceBundle messages;

    public IOManager(Scanner scanner, ResourceBundle messages) {
        this.sc = scanner;
        this.messages = messages;
    }

    public String GetInput() {
        ShowMessage("input.prompt");
        String userInput = sc.nextLine().toLowerCase();
        // vérifier que la saisie utilisateur est valide
        while (userInput.isEmpty()) {
            ShowMessage("input.invalid");
            userInput = sc.nextLine().toLowerCase();
        }
        return userInput;
    }

    public String GetInput(String key) {
        ShowMessage(key);
        String userInput = sc.nextLine().toLowerCase();
        // vérifier que la saisie utilisateur est valide
        while (userInput.isEmpty()) {
            ShowMessage("input.invalid");
            userInput = sc.nextLine().toLowerCase();
        }
        return userInput;
    }

    public void ShowMessage(String key) {
        System.out.println(messages.getString(key));
    }

    public boolean AskToContinue() {
        String userInput = GetInput("input.continue");
        return !userInput.equalsIgnoreCase("N");
    }

    public String GetGreeting() {
        int hour = java.time.LocalTime.now().getHour();

        if (hour < 12) {
            return EGreeting.MORNING.getKey();
        } else if (hour < 18) {
            return EGreeting.AFTERNOON.getKey();
        } else {
            return EGreeting.EVENING.getKey();
        }
    }
}

