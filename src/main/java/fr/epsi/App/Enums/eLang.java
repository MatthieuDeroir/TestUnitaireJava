package fr.epsi.App.Enums;

public enum eLang {

    //EUROPEAN LANGUAGES
    LANG_EN("en"),
    LANG_FR("fr"),
    LANG_ES("es"),
    LANG_DE("de"),
    LANG_IT("it"),
    LANG_PT("pt"),
    LANG_PL("pl"),
    LANG_RU("ru"),
    LANG_RO("ro"),

    //ASIAN LANGUAGES
    LANG_JA("ja"),
    LANG_KO("ko"),
    LANG_ZH("zh"),
    LANG_HI("hi"),
    LANG_AR("ar");


    private final String key;

    eLang(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
