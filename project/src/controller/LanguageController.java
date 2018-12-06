package controller;


import fileWriter.JSONCreator;
import models.Language;
import models.Settings;

import java.io.File;

public abstract class LanguageController {

    private final static String GERMAN = "GERMAN";
    private final static String ENGLISH = "ENGLISH";

    private final static String[] LANGUAGES =
            {
                    LanguageController.GERMAN,
                    LanguageController.ENGLISH
            };

    public enum LANG_CODE {
        GERMAN(0),
        ENGLISH(1);

        private final int value;

        LANG_CODE(final int newValue) {
            value = newValue;
        }

        int getValue() {
            return value;
        }
    }

    public static int getLangCode(String s) {
        return LANG_CODE.valueOf(s).getValue();
    }

    private static Language global_lang;

    public static String[] getAvailableLanguages() {
        return LanguageController.LANGUAGES;
    }

    public static void setLanguage(String lang) {
        for (String specificLang : LanguageController.LANGUAGES) {
            if (specificLang.contains(lang)) {
                LanguageController.loadLang(specificLang);
            }
        }
    }

    public static Language getLang() {
        return LanguageController.global_lang;
    }

    public static void saveLang(String s) {
        String path = "lang." + s + ".json";
        JSONCreator.save(path, LanguageController.global_lang);
    }


    public static void loadLang(String s) {

        String path = Settings.SETTINGS_DIR + "//" + "lang." + s + ".json";
        if (new File(path).exists()) {
            LanguageController.global_lang = (Language) JSONCreator.read(path, Language.class);
        } else {
            LanguageController.global_lang = new Language();
            JSONCreator.save(path, LanguageController.global_lang, true);
        }

    }
}
