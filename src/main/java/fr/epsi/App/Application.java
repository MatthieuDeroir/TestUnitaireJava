package fr.epsi.App;

import fr.epsi.App.Enums.Weekdays;
import fr.epsi.App.Managers.InputOutputManager;
import fr.epsi.App.Managers.LanguageManager;
import fr.epsi.App.Business.Palindrome;
import fr.epsi.App.Managers.MessageManager;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;


public class Application {

    private final InputOutputManager inputOutputManager;
    private final ResourceBundle resourceBundle;

    // Application breakdown:
    // 1. The Application class is the entry point of the program.
    public Application() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        LanguageManager languageManager = new LanguageManager();
        resourceBundle = languageManager.getLanguageResourcesFromUser(scanner);
        MessageManager messageManager = new MessageManager(resourceBundle);
        this.inputOutputManager = new InputOutputManager(messageManager);
    }

    public void run() {
        do {
            processUserInput();
        } while (inputOutputManager.AskToContinue());

        inputOutputManager.ShowMessage(Weekdays.GOODBYE.getKey());
    }

    private void processUserInput() {
        inputOutputManager.GetGreetingMessage();
        System.out.println(Palindrome.Check(inputOutputManager.GetInput(), this.resourceBundle));
    }
}
