package models;

import java.util.List;

public class StorageElementList {

    private List<StorageElement> elementList;
    private FileSize size = new FileSize(0);

    public StorageElementList(List<StorageElement> elementList){
        this.elementList = elementList;
    }

    public List<StorageElement> getElements (){
        return elementList;
    }

    public void setSize(double size) {
        this.size.set(size);
    }

    // TODO: create load and save methods with JSON-Files
    // @ChrisSembritzki assigned

}
