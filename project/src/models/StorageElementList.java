package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class StorageElementList {

    private List<StorageElement> elementList;
    private FileSize size = new FileSize(0);

    public StorageElementList(List<StorageElement> elementList) {
        this.elementList = elementList;
    }

    public List<StorageElement> getElements() {
        return elementList;
    }

    public void setSize(double size) {
        this.size.set(size);
    }


    public void saveJSON(String path) {  // Methodenaufruf mit (path) für aktuelles Objekt

//        Gson gson = new GsonBuilder().create();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();   // Formatiere Ausgabe

        String json = gson.toJson(this);
//        System.out.println(json);


        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public StorageElementList readJSON(String path) {

        Gson gson = new GsonBuilder().create();
        StorageElementList stlist = new StorageElementList(null);

        try (Reader reader = new FileReader(path)) {
            stlist = gson.fromJson(reader, StorageElementList.class);
            System.out.println(stlist.elementList.get(0).getFileSize());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stlist;
    }

}
