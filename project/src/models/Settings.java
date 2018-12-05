package models;

public class Settings {

    public final transient static int TABLE_ROW_HEIGHT = 16;

    public final transient static String SETTINGS_FILE = "settings//config.json";
    public final transient static String LOGGER_MODE_JSON = "RollingFileJSONLogger";
    public final transient static String LOGGER_MODE_NORMAL = "RollingFilePatternLogger";


    public boolean PRETTY_LOGGING = true;
    public boolean STANDARD_IS_HARD_SYNC = false;
    public boolean STANDARD_IS_SLOW_MODE = false;
    public boolean IS_DAEMON = false;


    public String COMPARE_FILE_ENDING = ".comp";
    public String INDEX_FILE_ENDING = ".index";
    public String FILE_ENDING = ".quix";

    public String LOGO_FILE = "img//Quix.png";
    public String HELP_FILE_LOGO = "img//Quix_transparent.png";
    public String LOADING_FILE_LOGO = "img//Quix_animated.gif";

    public String TEMP_DIR = "testdata//";

    public String LOG_DIR = "logs//";
    public String LOGGER_FILE_JSON = "logfile-old.json";
    public String LOGGER_FILE_NORMAL = "logfile.log";
    public String LOGGER_MODE = "RollingFileJSONLogger";

    public String NO_NEXT_ACTION_STRING = "Nächste Aktion";


}
