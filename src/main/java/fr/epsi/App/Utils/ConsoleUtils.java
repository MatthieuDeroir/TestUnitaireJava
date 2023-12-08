package fr.epsi.App.Utils;

import java.util.Scanner;

public class ConsoleUtils {

    private final Scanner input;

    public ConsoleUtils() {
        input = new Scanner(System.in);
    }

    public void ClearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void ClearConsoleAndPrint(String message) {
        ClearConsole();
        System.out.println(message);
    }

    public void Print(String message) {
        System.out.println(message);
    }

    public String GetInput() {
        return input.nextLine();
    }
}
