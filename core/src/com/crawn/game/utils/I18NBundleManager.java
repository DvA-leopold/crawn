package com.crawn.game.utils;

import com.badlogic.gdx.utils.I18NBundle;
import com.crawn.game.model.resource.manager.ResourceManager;

import java.util.ArrayList;
import java.util.Collections;

public class I18NBundleManager {
    static {
        initialize();
    }

    public static String getString(String key) {
        return myBundle.get(key);
    }

//    public static String getChainString(String key, String ...arguments) {
//        return myBundle.format(key, arguments);
//    }

    public static String getLanguageCode() {
        return languageArray.get(counter).getLanguageCode();
    }

    public static void changeLanguage() {
        counter = (counter >= Language.values().length - 1) ? 0 : counter + 1;
        changeLanguage(languageArray.get(counter));
    }

    public static void changeLanguage(Language newLanguage) {
        final String languageCode = "_" + newLanguage.getLanguageCode();
        final String countryCode = "_" + newLanguage.getCountryCode();
        final String variant = "_" + newLanguage.getLocaleVariant();
        myBundle = (I18NBundle) ResourceManager.instance().get("Bundle" + languageCode + countryCode + variant);
    }

    private static void initialize() {
        languageArray = new ArrayList<>(Language.values().length);
        Collections.addAll(languageArray, Language.values());
        myBundle = (I18NBundle) ResourceManager.instance().get("Bundle");
    }


    static private ArrayList<Language> languageArray;

    static private int counter = 0;
    static private I18NBundle myBundle;
}

enum Language {
    ENGLISH("en", "EN", "VAR1"),
    RUSSIAN("ru", "RU", "VAR1");

    Language(String languageCode, String countryCode, String localeVariant) {
        this.languageCode = languageCode;
        this.countryCode = countryCode;
        this.localeVariant = localeVariant;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getLocaleVariant() {
        return localeVariant;
    }


    final private String languageCode, countryCode, localeVariant;
}