package controller;

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

    private static final String TEMP_DIR = "testdata//";
    private static final String INDEX_FILE_ENDING = ".index";
    private static final String COMPARE_FILE_ENDING = ".comp";
    private static final String FILE_ENDING = ".quix";
    private static final boolean PRETTY_LOGGING = true;

    static String getCompareFileEnding(){
        return COMPARE_FILE_ENDING;
    }
    static String getIndexFileEnding(){
        return INDEX_FILE_ENDING;
    }
    static String getFileEnding(){
        return FILE_ENDING;
    }
    static String getTempDir(){ return TEMP_DIR; }

    public static boolean getIsPrettyLogging() { return PRETTY_LOGGING; }
}