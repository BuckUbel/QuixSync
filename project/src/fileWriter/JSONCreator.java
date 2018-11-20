package fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.SettingsController;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class JSONCreator {

    public static void save(String path, Object object) {
        Gson gson;
        if (SettingsController.getIsPrettyLogging()) {
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
