package fr.epsi.App.Managers;

import fr.epsi.App.Utils.ConsoleUtils;

import java.util.Calendar;
import java.util.ResourceBundle;

public class InputOutputManager {

    private final int hour;
    private final MessageManager messages;
    private final ConsoleUtils console;

    public InputOutputManager(MessageManager messageManager) {
        this.messages = messageManager;
        this.console = new ConsoleUtils();
        this.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public String GetInput() {
        ShowMessage(messages.PROMPT);
        String userInput = console.GetInput();
        // vérifier que la saisie utilisateur est valide
        while (userInput.isEmpty()) {
            ShowMessage(messages.INVALID);
            userInput = console.GetInput();
        }
        return userInput;
    }

    public String GetInput(String key) {
        ShowMessage(key);
        String userInput = console.GetInput();
        // vérifier que la saisie utilisateur est valide
        while (userInput.isEmpty()) {
            ShowMessage(messages.INVALID);
            userInput = console.GetInput();
        }
        return userInput;
    }

    public void ShowMessage(String key) {
        console.Print(messages.getSentence(key));
    }

    public boolean AskToContinue() {
        String userInput = GetInput(messages.CONTINUE);
        return !userInput.equalsIgnoreCase("N");
    }


    public void GetGreetingMessage() {
        if (hour < 12) {
            ShowMessage(messages.MORNING);
        } else if (hour < 18) {
            ShowMessage(messages.AFTERNOON);
        } else {
            ShowMessage(messages.EVENING);
        }
    }

    // This method is not used in the application, but is used in the unit tests
    public String GetGreetingMessage(int hour) {
        String key;
        if (hour < 12) {
            key = messages.MORNING;
        } else if (hour < 18) {
            key = messages.AFTERNOON;
        } else {
            key = messages.EVENING;
        }
        ShowMessage(key);
        return messages.getSentence(key);
    }

    public void ShowCongratulationMessage() {
        ShowMessage(messages.CONGRATULATIONS);
    }
}

