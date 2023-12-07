package fr.epsi.App.Managers;

import fr.epsi.App.Enums.eSentences;
import fr.epsi.App.Enums.eSentences;

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
        ShowMessage(eSentences.PROMPT.getKey());
        String userInput = sc.nextLine().toLowerCase();
        // vérifier que la saisie utilisateur est valide
        while (userInput.isEmpty()) {
            ShowMessage(eSentences.INVALID.getKey());
            userInput = sc.nextLine().toLowerCase();
        }
        return userInput;
    }

    public String GetInput(String key) {
        ShowMessage(key);
        String userInput = sc.nextLine().toLowerCase();
        // vérifier que la saisie utilisateur est valide
        while (userInput.isEmpty()) {
            ShowMessage(eSentences.INVALID.getKey());
            userInput = sc.nextLine().toLowerCase();
        }
        return userInput;
    }

    public void ShowMessage(String key) {
        System.out.println(messages.getString(key));
    }

    public boolean AskToContinue() {
        String userInput = GetInput(eSentences.CONTINUE.getKey());
        return !userInput.equalsIgnoreCase("N");
    }


    public void GetGreetingMessage() {
        if (hour < 12) {
            ShowMessage(eSentences.MORNING.getKey());
        } else if (hour < 18) {
            ShowMessage(eSentences.AFTERNOON.getKey());
        } else {
            ShowMessage(eSentences.EVENING.getKey());
        }
    }

    // This method is not used in the application, but is used in the unit tests
    public String GetGreetingMessage(int hour) {
        String key;
        if (hour < 12) {
            key = eSentences.MORNING.getKey();
        } else if (hour < 18) {
            key = eSentences.AFTERNOON.getKey();
        } else {
            key = eSentences.EVENING.getKey();
        }
        ShowMessage(key);
        return messages.getString(key);
    }

    public void ShowCongratulationMessage() {
        ShowMessage(eSentences.CONGRATULATIONS.getKey());
    }


    public int getHour() {
        return hour;
    }

    public Scanner getSc() {
        return sc;
    }

    public ResourceBundle getMessages() {
        return messages;
    }
}

