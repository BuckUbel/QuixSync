package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

public class StorageElementList extends models.List {

    private List<StorageElement> elementList;
    private String dirPath;
    private double count = 0;
    private int latestLft;
    private long lastModified;

    public StorageElementList() {
    }

    public StorageElementList(List<StorageElement> elementList, String dirPath) {

        this.elementList = elementList;
        this.dirPath = dirPath;
    }


    public StorageElementList(List<StorageElement> elementList, String dirPath, int latestLft, long lastModified) {

        this.elementList = elementList;
        this.dirPath = dirPath;
        this.latestLft = latestLft;
        this.lastModified = lastModified;
    }

    public List<StorageElement> getElements() {
        return elementList;
    }

    public StorageElement[] getElementsArray() {
        StorageElement[] returnArray = new StorageElement[elementList.size()];
        return elementList.toArray(returnArray);
    }

    public int getLatestLft() {
        return this.latestLft;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public String getDirPath() {
        return this.dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public void setSize(double size) {
        this.count = size;
    }

    public void sort() {
        this.elementList.sort(Collections.reverseOrder(StorageElement::compareTo));
    }

    public StorageElementList sortAndGet(){
        this.sort();
        return this;
    }
    public StorageElementList readJSON(String path) {

        Gson gson = new GsonBuilder().create();
        StorageElementList stlist = new StorageElementList();

        try (Reader reader = new FileReader(path)) {
            stlist = gson.fromJson(reader, StorageElementList.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stlist;
    }

}
