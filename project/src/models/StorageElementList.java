package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

public class StorageElementList extends ElementList {

    private List<StorageElement> elementList;
    private String dirPath;
    private double count = 0;
    private int latestLft;
    private long lastModified;
    private long listCreatedAt;

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

    public void setListCreatedAt(long listCreatedAt) {
        this.listCreatedAt = listCreatedAt;
    }

    public long getListCreatedAt(){
        return this.listCreatedAt;
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

    public static boolean isBetween(StorageElement x, List<Position> positions) {

        boolean returnValue = false;

        for (Position position : positions) {
            returnValue = StorageElementList.isBetween(x, position);
            if (returnValue) break;
        }

        return returnValue;
    }

    public static boolean isBetween(StorageElement x, Position pos) {

        if (pos.lft < x.getLft() && x.getRgt() < pos.rgt) {
            return true;
        }
        return false;

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

    public StorageElementList sortAndGet() {
        this.sort();
        return this;
    }

    @Override
    public void saveAsJSON(String path) {
        this.setListCreatedAt(System.currentTimeMillis());
        super.saveAsJSON(path);
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
