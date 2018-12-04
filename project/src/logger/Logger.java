package logger;

import controller.SettingsController;
import org.apache.logging.log4j.LogManager;
import views.mainView;

public class Logger {

    private static mainView window;
    private static Boolean system = false;

    public static void setWindow(mainView window) {
        Logger.window = window;
    }

    // Log4J
    private static org.apache.logging.log4j.Logger log = LogManager.getLogger(SettingsController.getLoggerMode());

    public static void print(String s) {
        if (system) {
            System.out.println(s);
        } else {
            Logger.write(s, false);
        }
    }

    public static void printErr(String s) {
        if (system) {
            System.err.println(s);
        } else {
            Logger.write(s, true);
        }
    }

    public static void print(Object o) {
        if (system) {
            System.out.println(o);
        } else {
            Logger.write(String.valueOf(o), false);
        }
    }

    public static void printErr(Object o) {
        if (system) {
            System.err.println(o);
        } else {
            Logger.write(String.valueOf(o), true);
        }
    }

    private static void write(String s) {
        Logger.write(s,false);
    }

    private static void write(String s, boolean isError) {
        if (!isError){
            log.info(s);
        }else{
            log.error(s);
        }
    }
}
