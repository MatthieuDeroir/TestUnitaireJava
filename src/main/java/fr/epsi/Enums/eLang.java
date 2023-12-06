package fr.epsi.Enums;

public enum eLang {
    ENGLISH("en"),

    SPANISH("es"),
    DEUTSCH("de"),
    FRENCH("fr");

    private final String key;

    eLang(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
