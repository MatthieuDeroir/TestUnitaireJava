package fr.epsi.Managers;


import fr.epsi.Enums.EGreeting;

import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Scanner;

public class IOManager {

    private final int hour;
    private final Scanner sc;
    private final ResourceBundle messages;

    public IOManager(Scanner scanner, ResourceBundle messages) {
        this.sc = scanner;
        this.messages = messages;
        this.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
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


    public void GetGreetingMessage() {
        if (hour < 12) {
            ShowMessage(EGreeting.MORNING.getKey());
        } else if (hour < 18) {
            ShowMessage(EGreeting.AFTERNOON.getKey());
        } else {
            ShowMessage(EGreeting.EVENING.getKey());
        }
    }


}

