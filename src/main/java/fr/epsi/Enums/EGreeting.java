package fr.epsi.Enums;

public enum EGreeting {
    MORNING("greeting.morning"),
    AFTERNOON("greeting.afternoon"),
    EVENING("greeting.evening"),
    GOODBYE("greeting.goodbye");

    private final String key;

    EGreeting(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
