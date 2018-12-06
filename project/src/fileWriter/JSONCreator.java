package fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.SettingsController;
import logger.Logger;

import java.io.*;

public abstract class JSONCreator {

    public static void save(String path, Object object) {
        JSONCreator.save(path, object, SettingsController.getIsPrettyLogging());
    }

    public static void save(String path, Object object, boolean prettyLogging) {

        boolean allowToCreate = false;

        try {

            File dir = new File(path).getParentFile();
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    allowToCreate = true;
                } else {
                    throw new FileNotFoundException(LanguageController.getLang().DIR_TO_FILE + path + LanguageController.getLang().COULDNT_BE_CREATED);
                }
            } else {
                allowToCreate = true;
            }
        } catch (Exception e) {
            Logger.printErr(e.toString());
        }
        if (allowToCreate) {

            Gson gson;
            if (prettyLogging) {
                gson = new GsonBuilder().setPrettyPrinting().create();
            } else {
                gson = new GsonBuilder().create();
            }
            String json = gson.toJson(object);
            try (FileWriter writer = new FileWriter(path)) {
                gson.toJson(object, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static <T> Object read(String path, Class<T> c) {

        Gson gson = new GsonBuilder().create();
        Object stlist = new Object();

        try (Reader reader = new FileReader(path)) {
            stlist = gson.fromJson(reader, c);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stlist;
    }
}
