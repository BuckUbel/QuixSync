package logger;

import controller.SettingsController;
import org.apache.logging.log4j.LogManager;
import views.mainView;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.*;

public class Logger {

    private static mainView window;
    private static Boolean system = false;

    public static void setWindow(mainView window) {
        Logger.window = window;
    }

    // Log4J
    private static org.apache.logging.log4j.Logger log = LogManager.getLogger("RollingFileJSONLogger");
//    private static org.apache.logging.log4j.Logger log = LogManager.getLogger("RollingFilePatternLogger");

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

    public static void mergeLogFiles(){
        String absolutePath = new File("").getAbsolutePath();
        File currentFile = new File(absolutePath+"\\logs\\logfile.json");

        File archivedir = new File(absolutePath+"\\logs\\archive");

        File[] listOfArchiveFiles = archivedir.listFiles();

        File mergefile = new File(absolutePath+"\\logs\\mergedlogfile.json");

        if (mergefile.exists()) {
            mergefile.delete();
        }

        String mergedFilePath = absolutePath+"\\logs\\mergedlogfile.json";

        FileWriter fwriter = null;
        BufferedWriter bfout = null;

        try {
                fwriter = new FileWriter(mergedFilePath, true);
                bfout = new BufferedWriter(fwriter);
        } catch (IOException e1){
            e1.printStackTrace();
        }

        // archive Files
        for (File file : listOfArchiveFiles){
            FileInputStream fis1;

            try{
                fis1 = new FileInputStream(file);
                BufferedReader bfin = new BufferedReader(new InputStreamReader(fis1));

                String line;
                while((line = bfin.readLine()) != null){
                    bfout.write(line);
                    bfout.newLine();
                }

                bfin.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        // Current File

            FileInputStream fis;

            try{
                fis = new FileInputStream(currentFile);
                BufferedReader bfin = new BufferedReader(new InputStreamReader(fis));

                String line;
                while((line = bfin.readLine()) != null){
                    bfout.write(line);
                    bfout.newLine();
                }

                bfin.close();
            } catch (IOException e){
                e.printStackTrace();
            }


        try {
            bfout.write("]"); // Closing Bracket
            bfout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
