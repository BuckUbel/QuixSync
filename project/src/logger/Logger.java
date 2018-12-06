package logger;

import controller.LanguageController;
import controller.SettingsController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.util.ArrayUtils;
import views.mainView;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.*;
import java.util.List;

public abstract class Logger {

    private static Boolean system = false;

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
        Logger.write(s, false);
    }

    private static void write(String s, boolean isError) {
        if (!isError) {
            log.info(s);
        } else {
            log.error(s);
        }
    }

    public static void mergeLogFiles() throws IOException {

        String absolutePath = new File("").getAbsolutePath();
        File currentFile = new File(absolutePath + "\\" + SettingsController.getLogFileDir() + "\\logfile.json");
        File archivedir = new File(absolutePath + "\\" + SettingsController.getLogArchiveDir());

        File[] listOfArchiveFiles = archivedir.listFiles();
        String mergedFilePath = absolutePath + "\\" + SettingsController.getMergeLoggerFilePath();
        File mergefile = new File(mergedFilePath);

        if (mergefile.exists()) {
            if (!mergefile.delete()) {
                throw new IOException(LanguageController.getLang().FILE + mergedFilePath + LanguageController.getLang().COULDNT_BE_DELETED);
            }
        }

        FileWriter fwriter = new FileWriter(mergedFilePath, true);
        BufferedWriter bfout = new BufferedWriter(fwriter);

        bfout.write("["); // For the whole array

        // archive Files
        if (listOfArchiveFiles != null) {
            for (File file : listOfArchiveFiles) {
                Logger.saveLogFile(file, bfout, true);
            }
        }
        // Current File
        Logger.saveLogFile(currentFile, bfout, false);

        bfout.write("]"); // Closing Bracket for currentFile
//        bfout.write("]"); // For the whole array

        bfout.close();
    }


    private static void saveLogFile(File currentFile, BufferedWriter bfout, boolean withComma) throws IOException {
        FileInputStream fis = new FileInputStream(currentFile);
        BufferedReader bfin = new BufferedReader(new InputStreamReader(fis));

        String line;
        boolean containsCloseBracket = false;
        while ((line = bfin.readLine()) != null) {
            containsCloseBracket = line.contains("]");
            bfout.write(line);
            bfout.newLine();
        }

        if (!containsCloseBracket) {
            bfout.write("]"); // Closing Bracket
        }
        if (withComma) {
            bfout.write(","); // Closing Bracket
        }
        bfout.newLine();
        bfin.close();
    }

    public static LogInformation[] mergeMultipleLogArrays(LogInformation[][] logs) {

        List<LogInformation> returnLog = new ArrayList<>();

        for (LogInformation[] log1 : logs) {
            returnLog.addAll(Arrays.asList(log1));
        }

        LogInformation[] returnLogArray = new LogInformation[returnLog.size()];
        returnLog.toArray(returnLogArray);

        return returnLogArray;
    }
}
