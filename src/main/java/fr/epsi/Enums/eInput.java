package fr.epsi.Enums;

public enum eInput {
    CONTINUE("input.continue"),
    INVALID("input.invalid"),
    PROMPT("input.prompt"),
    LANGUAGE("input.language");

    private final String key;

    eInput(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
