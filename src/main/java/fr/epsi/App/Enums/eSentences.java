package fr.epsi.App.Enums;

public enum eSentences {
    CONTINUE("input.continue"),
    INVALID("input.invalid"),
    PROMPT("input.prompt"),
    LANGUAGE("input.language"),

    MORNING("greeting.morning"),
    AFTERNOON("greeting.afternoon"),
    EVENING("greeting.evening"),
    GOODBYE("greeting.goodbye"),

    CONGRATULATIONS("greeting.congratulations");

    private final String key;

    eSentences(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
