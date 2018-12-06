package controller;

import fileWriter.JSONCreator;
import logger.Logger;
import models.Settings;

import java.io.File;
import java.io.FileNotFoundException;

abstract public class SettingsController {

    private static Settings global_settings;

    public static void loadSettings() {
        if (new File(Settings.SETTINGS_FILE).exists()) {
            SettingsController.global_settings = (Settings) JSONCreator.read(Settings.SETTINGS_FILE, Settings.class);
        } else {
            SettingsController.global_settings = new Settings();
            JSONCreator.save(Settings.SETTINGS_FILE, SettingsController.global_settings, true);
        }
    }

    public static void saveSettings() {
        JSONCreator.save(Settings.SETTINGS_FILE, global_settings);
    }

    public enum TAB_NUMBER {
        WHOLE_SYNC(0),
        INDEXING(1),
        COMPARE(2),
        SYNC(3),
        SETTINGS(4);

        private final int value;

        TAB_NUMBER(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    static String getCompareFileEnding() {
        return global_settings.COMPARE_FILE_ENDING;
    }

    static String getIndexFileEnding() {
        return global_settings.INDEX_FILE_ENDING;
    }

    static String getFileEnding() {
        return global_settings.FILE_ENDING;
    }

    static int getTableRowHeight() {
        return global_settings.TABLE_ROW_HEIGHT;
    }

    public static String getLanguage() {
        return global_settings.LANGUAGE;
    }

    public static String getTempDir() {
        return global_settings.TEMP_DIR;
    }

    public static String getLogFileDir() {
        return global_settings.LOG_DIR;
    }

    public static String getLogArchiveDir() {
        return global_settings.LOG_DIR + "\\archive";
    }

    public static String getLogoFile() {
        return global_settings.LOGO_FILE;
    }

    public static String getHelpFileLogo() {
        return global_settings.HELP_FILE_LOGO;
    }

    public static String getLoadingFileLogo() {
        return global_settings.LOADING_FILE_LOGO;
    }

    public static String getNoNextActionString() {
        return global_settings.NO_NEXT_ACTION_STRING;
    }

    public static String getLoggerMode() {
        return global_settings.LOGGER_MODE;
    }

    public static String getLoggerFilePath() throws FileNotFoundException {
        switch (SettingsController.getLoggerMode()) {
            case Settings.LOGGER_MODE_JSON:
                return global_settings.LOG_DIR + global_settings.LOGGER_FILE_JSON;
            case Settings.LOGGER_MODE_NORMAL:
                return global_settings.LOG_DIR + global_settings.LOGGER_FILE_NORMAL;
            default:
                throw new FileNotFoundException("No Logger File");
        }
    }

    public static String getMergeLoggerFilePath() throws FileNotFoundException {
        switch (SettingsController.getLoggerMode()) {
            case Settings.LOGGER_MODE_JSON:
                return global_settings.LOG_DIR + "merge" + global_settings.LOGGER_FILE_JSON;
            case Settings.LOGGER_MODE_NORMAL:
                return global_settings.LOG_DIR + "merge" + global_settings.LOGGER_FILE_NORMAL;
            default:
                throw new FileNotFoundException("No Logger File");
        }
    }

    public static boolean getIsPrettyLogging() {
        return global_settings.PRETTY_LOGGING;
    }

    public static boolean getIsHardSync() {
        return global_settings.STANDARD_IS_HARD_SYNC;
    }

    public static boolean getIsFastMode() {
        return global_settings.STANDARD_IS_FAST_MODE;
    }

    public static boolean getIsDaemon() {
        return global_settings.IS_DAEMON;
    }

    public static void setPrettyLogging(boolean PRETTY_LOGGING) {
        global_settings.PRETTY_LOGGING = PRETTY_LOGGING;
    }

    public static void setStandardIsHardSync(boolean STANDARD_IS_HARD_SYNC) {
        global_settings.STANDARD_IS_HARD_SYNC = STANDARD_IS_HARD_SYNC;
    }

    public static void setStandardIsFastMode(boolean STANDARD_IS_FAST_MODE) {
        global_settings.STANDARD_IS_FAST_MODE = STANDARD_IS_FAST_MODE;
    }

    public static void setIsDaemon(boolean IS_DAEMON) {
        global_settings.IS_DAEMON = IS_DAEMON;
    }

    public static void setCompareFileEnding(String COMPARE_FILE_ENDING) {
        global_settings.COMPARE_FILE_ENDING = COMPARE_FILE_ENDING;
    }

    public static void setIndexFileEnding(String INDEX_FILE_ENDING) {
        global_settings.INDEX_FILE_ENDING = INDEX_FILE_ENDING;
    }

    public static void setFileEnding(String FILE_ENDING) {
        global_settings.FILE_ENDING = FILE_ENDING;
    }

    public static void setLogoFile(String LOGO_FILE) {
        global_settings.LOGO_FILE = LOGO_FILE;
    }

    public static void setHelpFileLogo(String HELP_FILE_LOGO) {
        global_settings.HELP_FILE_LOGO = HELP_FILE_LOGO;
    }

    public static void setLoadingFileLogo(String LOADING_FILE_LOGO) {
        global_settings.LOADING_FILE_LOGO = LOADING_FILE_LOGO;
    }

    private static String pathToSavePath(String tmp) {
        String TEMP_DIR = tmp;
        if (!TEMP_DIR.substring(TEMP_DIR.length() - 2).equals("\\\\")) {
            if (TEMP_DIR.substring(TEMP_DIR.length() - 1).equals("\\")) {
                TEMP_DIR += "\\";
            } else {
                TEMP_DIR += "\\\\";
            }
        }
        if (TEMP_DIR.substring(TEMP_DIR.length() - 2).equals("\\\\")) {
            // no problem
        }
        return TEMP_DIR;
    }

    public static void setTempDir(String TEMP_DIR) {

        TEMP_DIR = pathToSavePath(TEMP_DIR);
        File tempDir = new File(TEMP_DIR);
        if (!(tempDir.isDirectory() && tempDir.exists())) {
            if (tempDir.mkdirs()) {
                global_settings.TEMP_DIR = TEMP_DIR;
            }
        } else {
            global_settings.TEMP_DIR = TEMP_DIR;
        }
    }

    public static void setLogDir(String LOG_DIR) {
        LOG_DIR = pathToSavePath(LOG_DIR);
        File logDir = new File(LOG_DIR);
        File logArchiveDir = new File(SettingsController.getLogArchiveDir());
        if (!(logDir.isDirectory() && logDir.exists())) {
            if (logDir.mkdirs()) {
                if (logArchiveDir.mkdirs()) {
                    global_settings.LOG_DIR = LOG_DIR;
                }
            }
        } else {
            global_settings.LOG_DIR = LOG_DIR;
        }
    }

    public static void setLoggerFileJson(String LOGGER_FILE_JSON) {
        global_settings.LOGGER_FILE_JSON = LOGGER_FILE_JSON;
    }

    public static void setLoggerFileNormal(String LOGGER_FILE_NORMAL) {
        global_settings.LOGGER_FILE_NORMAL = LOGGER_FILE_NORMAL;
    }

    public static void setLoggerMode(String LOGGER_MODE) {
        global_settings.LOGGER_MODE = LOGGER_MODE;
    }

    public static void setNoNextActionString(String NO_NEXT_ACTION_STRING) {
        global_settings.NO_NEXT_ACTION_STRING = NO_NEXT_ACTION_STRING;
    }
}