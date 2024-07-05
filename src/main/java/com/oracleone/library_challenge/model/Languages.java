package com.oracleone.library_challenge.model;

public enum Languages {
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    JAPANESE("ja"),
    CHINESE("zh"),
    KOREAN("ko");

    private String language;

    Languages(String language) {
        this.language = language;
    }

    public static Languages fromString(String text) {
        for (Languages lang : Languages.values()) {
            if (lang.language.equalsIgnoreCase(text) ) {
                return lang;
            }
        }
        throw new IllegalArgumentException("Language not found: " + text);
    }

    public String getLanguage() {
        return language;
    }
}
