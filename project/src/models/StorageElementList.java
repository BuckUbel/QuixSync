package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class StorageElementList extends models.List {

    private List<StorageElement> elementList;
    private String dirPath;
    private FileSize size = new FileSize(0);

    public StorageElementList() {
    }


    public StorageElementList(List<StorageElement> elementList, String dirPath) {

        this.elementList = elementList;
        this.dirPath = dirPath;
    }

    public List<StorageElement> getElements() {
        return elementList;
    }

    public StorageElement[] getElementsArray(){
        StorageElement[] returnArray = new StorageElement[elementList.size()];
        return elementList.toArray(returnArray);
    }

    public String getDirPath() {
        return this.dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public void setSize(double size) {
        this.size.set(size);
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
