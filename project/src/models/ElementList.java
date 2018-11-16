package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ElementList {

    public void saveAsJSON(String path) {

        //        Gson gson = new GsonBuilder().create();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();   // Formatted Output

        String json = gson.toJson(this);
//        System.out.println(json);


        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ElementList readJSON(String path) {

        Gson gson = new GsonBuilder().create();
        ElementList stlist = new ElementList();

        try (Reader reader = new FileReader(path)) {
            stlist = gson.fromJson(reader, ElementList.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stlist;
    }

    // JSON Creator for Objects
    public void createJSON(String path, Object object) {

//        Gson gson = new GsonBuilder().create();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();   // Formatted Output

        String json = gson.toJson(object);
//        System.out.println(json);


        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
