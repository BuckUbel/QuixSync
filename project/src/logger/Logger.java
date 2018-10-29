package logger;

import views.View;

public class Logger {

    private static View window;
    private static Boolean system = true;

    public static void setWindow(View window) {
        Logger.window = window;
    }

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
        // TODO: create log-JSON-File
        // @ChrisSembritzki assigned
    }
}
