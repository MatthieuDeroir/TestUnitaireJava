package fr.epsi.App;

import fr.epsi.App.Enums.eSentences;
import fr.epsi.App.Managers.IOManager;
import fr.epsi.App.Managers.LanguageManager;
import fr.epsi.App.Managers.PalindromeManager;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Application {

    private final IOManager ioManager;

    public Application() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ResourceBundle messages = LanguageManager.getLanguageResourcesFromUser(scanner);
        this.ioManager = new IOManager(scanner, messages);
    }

    public void run() {
        do {
            ioManager.GetGreetingMessage();
            processUserInput();
        } while (ioManager.AskToContinue());

        ioManager.ShowMessage(eSentences.GOODBYE.getKey());
    }

    private void processUserInput() {
        String input = ioManager.GetInput();
        System.out.println(PalindromeManager.Check(input, ioManager.getMessages()));
    }
}
