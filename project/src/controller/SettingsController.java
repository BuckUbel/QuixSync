package controller;

import java.io.FileNotFoundException;

public class SettingsController {

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

    private static final int TABLE_ROW_HEIGHT = 16;
    private static final String TEMP_DIR = "testdata//";
    private static final String LOG_DIR = "logs//";
    private static final String INDEX_FILE_ENDING = ".index";
    private static final String COMPARE_FILE_ENDING = ".comp";
    private static final String FILE_ENDING = ".quix";
    private static final String LOGO_FILE = "img//Quix.png";
    private static final String HELP_FILE_LOGO = "img//Quix_transparent.png";
    private static final String LOADING_FILE_LOGO = "img//Quix_animated.gif";
    private static final String NO_NEXT_ACTION_STRING = "Nächste Aktion";
    public static final String LOGGER_MODE_JSON = "RollingFileJSONLogger";
    public static final String LOGGER_FILE_JSON = "logfile-old.json";
    public static final String LOGGER_MODE_NORMAL = "RollingFilePatternLogger";
    public static final String LOGGER_FILE_NORMAL = "logfile.log";
    private static final boolean PRETTY_LOGGING = true;
    private static final boolean STANDARD_IS_HARD_SYNC = false;
    private static final boolean STANDARD_IS_SLOW_MODE = false;

    static String getCompareFileEnding() {
        return COMPARE_FILE_ENDING;
    }

    static String getIndexFileEnding() {
        return INDEX_FILE_ENDING;
    }

    static String getFileEnding() {
        return FILE_ENDING;
    }

    static String getTempDir() {
        return TEMP_DIR;
    }

    static int getTableRowHeight() {
        return TABLE_ROW_HEIGHT;
    }

    public static String getLogoFile() {
        return LOGO_FILE;
    }

    public static String getHelpFileLogo() {
        return HELP_FILE_LOGO;
    }

    public static String getLoadingFileLogo() {
        return LOADING_FILE_LOGO;
    }

    public static String getNoNextActionString() {
        return NO_NEXT_ACTION_STRING;
    }

    public static String getLoggerMode() {
        return LOGGER_MODE_JSON;
    }

    public static String getLoggerFilePath() throws FileNotFoundException {
        switch (SettingsController.getLoggerMode()) {
            case SettingsController.LOGGER_MODE_JSON:
                return SettingsController.LOG_DIR + SettingsController.LOGGER_FILE_JSON;
            case SettingsController.LOGGER_MODE_NORMAL:
                return SettingsController.LOG_DIR + SettingsController.LOGGER_FILE_NORMAL;
            default:
                throw new FileNotFoundException("No Logger File");
        }
    }

    public static boolean getIsPrettyLogging() {
        return PRETTY_LOGGING;
    }

    public static boolean getIsHardSync() {
        return STANDARD_IS_HARD_SYNC;
    }

    public static boolean getIsSlowMode() {
        return STANDARD_IS_SLOW_MODE;
    }
}