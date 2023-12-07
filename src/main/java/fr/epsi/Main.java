package fr.epsi;

import fr.epsi.App.Application;

public class Main {

    public static void main(String[] args) {
        try {
            Application application = new Application();
            application.run();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
