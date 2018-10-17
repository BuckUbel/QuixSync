package models;

import java.util.List;

public class StorageElementList {

    public List<StorageElement> elementList;
    public FileSize size = new FileSize(0);

    public StorageElementList(List<StorageElement> elementList){
        this.elementList = elementList;
    }

    public void setSize(double size) {
        this.size.set(size);
    }
}
