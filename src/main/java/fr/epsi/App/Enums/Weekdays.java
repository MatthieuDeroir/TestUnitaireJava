package fr.epsi.App.Enums;

public enum Weekdays {
    CONTINUE("input.continue"),
    INVALID("input.invalid"),
    PROMPT("input.prompt"),
    AUTOMATIC("input.automatic"),
    LANGUAGE("input.language"),
    MORNING("greeting.morning"),
    AFTERNOON("greeting.afternoon"),
    EVENING("greeting.evening"),
    GOODBYE("greeting.goodbye"),

    CONGRATULATIONS("greeting.congratulations");

    private final String key;

    Weekdays(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
