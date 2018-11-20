package models;

import fileWriter.JSONCreator;

import java.util.Collections;
import java.util.List;

public class StorageElementList extends ElementList {

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

    private static boolean isBetween(StorageElement x, Position pos) {
        return pos.lft < x.getLft() && x.getRgt() < pos.rgt;
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

    private void sort() {
        this.elementList.sort(Collections.reverseOrder(StorageElement::compareTo));
    }

    public StorageElementList sortAndGet() {
        this.sort();
        return this;
    }

    public StorageElementList readJSON(String path) {

        return (StorageElementList) JSONCreator.read(path, StorageElementList.class);
    }
}
