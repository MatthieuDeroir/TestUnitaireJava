package fr.epsi.Managers;


import fr.epsi.Enums.eGreeting;
import fr.epsi.Enums.eInput;

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
        ShowMessage(eInput.PROMPT.getKey());
        String userInput = sc.nextLine().toLowerCase();
        // vérifier que la saisie utilisateur est valide
        while (userInput.isEmpty()) {
            ShowMessage(eInput.INVALID.getKey());
            userInput = sc.nextLine().toLowerCase();
        }
        return userInput;
    }

    public String GetInput(String key) {
        ShowMessage(key);
        String userInput = sc.nextLine().toLowerCase();
        // vérifier que la saisie utilisateur est valide
        while (userInput.isEmpty()) {
            ShowMessage(eInput.INVALID.getKey());
            userInput = sc.nextLine().toLowerCase();
        }
        return userInput;
    }

    public void ShowMessage(String key) {
        System.out.println(messages.getString(key));
    }

    public boolean AskToContinue() {
        String userInput = GetInput(eInput.CONTINUE.getKey());
        return !userInput.equalsIgnoreCase("N");
    }


    public void GetGreetingMessage() {
        if (hour < 12) {
            ShowMessage(eGreeting.MORNING.getKey());
        } else if (hour < 18) {
            ShowMessage(eGreeting.AFTERNOON.getKey());
        } else {
            ShowMessage(eGreeting.EVENING.getKey());
        }
    }



    // This method is not used in the application, but is used in the unit tests
    public String GetGreetingMessage(int hour) {
        String key;
        if (hour < 12) {
            key = eGreeting.MORNING.getKey();
        } else if (hour < 18) {
            key = eGreeting.AFTERNOON.getKey();
        } else {
            key = eGreeting.EVENING.getKey();
        }
        ShowMessage(key);
        return messages.getString(key);
    }


}

