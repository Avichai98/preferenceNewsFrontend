package com.avichai98.preferencenews.Utilities;

import java.util.HashMap;

public class LanguageManager {
    private final HashMap<String, String> languages;

    public LanguageManager() {
        languages = new HashMap<>();
        addAllLanguages();
    }

    private void addAllLanguages() {
        languages.put("Arabic", "ar");
        languages.put("Azerbaijani", "az");
        languages.put("Bengali", "bn");
        languages.put("Bulgarian", "bg");
        languages.put("Chinese", "zh");
        languages.put("Czech", "cs");
        languages.put("Danish", "da");
        languages.put("Dutch", "nl");
        languages.put("English", "en");
        languages.put("Estonian", "et");
        languages.put("Finnish", "fi");
        languages.put("French", "fr");
        languages.put("Georgian", "ka");
        languages.put("German", "de");
        languages.put("Greek", "el");
        languages.put("Gujarati", "gu");
        languages.put("Hebrew", "he");
        languages.put("Hindi", "hi");
        languages.put("Hungarian", "hu");
        languages.put("Indonesian", "id");
        languages.put("Italian", "it");
        languages.put("Japanese", "ja");
        languages.put("Kannada", "kn");
        languages.put("Korean", "ko");
        languages.put("Latvian", "lv");
        languages.put("Lithuanian", "lt");
        languages.put("Malayalam", "ml");
        languages.put("Marathi", "mr");
        languages.put("Nepali", "ne");
        languages.put("Norwegian", "no");
        languages.put("Oriya", "or");
        languages.put("Persian", "fa");
        languages.put("Polish", "pl");
        languages.put("Portuguese", "pt");
        languages.put("Punjabi", "pa");
        languages.put("Romanian", "ro");
        languages.put("Russian", "ru");
        languages.put("Serbian", "sr");
        languages.put("Sinhala", "si");
        languages.put("Slovak", "sk");
        languages.put("Slovenian", "sl");
        languages.put("Spanish", "es");
        languages.put("Swedish", "sv");
        languages.put("Tamil", "ta");
        languages.put("Telugu", "te");
        languages.put("Thai", "th");
        languages.put("Turkish", "tr");
        languages.put("Ukrainian", "uk");
        languages.put("Urdu", "ur");
        languages.put("Vietnamese", "vi");
        languages.put("Welsh", "cy");
    }

    public HashMap<String, String> getLanguages() {
        return languages;
    }

    public String getCodeForLanguage(String language) {
        return languages.getOrDefault(language, "en"); // Default to "en"
    }
}

