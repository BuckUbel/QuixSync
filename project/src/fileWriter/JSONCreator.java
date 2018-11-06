package fileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class JSONCreator {

    public JSONCreator(String path, Object object) {

//        Gson gson = new GsonBuilder().create();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();   // Formatiere Ausgabe

        String json = gson.toJson(object);
//        System.out.println(json);


        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
