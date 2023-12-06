package fr.epsi.Enums;

public enum eGreeting {
    MORNING("greeting.morning"),
    AFTERNOON("greeting.afternoon"),
    EVENING("greeting.evening"),
    GOODBYE("greeting.goodbye");

    private final String key;

    eGreeting(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
