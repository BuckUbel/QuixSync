package models;

import fileWriter.JSONCreator;

import java.util.List;
import java.util.stream.Stream;

public class ProcessingElementList extends ElementList {

    private minimizedStorageElement[] copyList;
    private minimizedStorageElement[] deleteList;
    private minimizedStorageElement[] newTargetList;

    public String sourceDirPath;
    public String targetDirPath;

    public ProcessingElementList() {
    }

    private ProcessingElementList(minimizedStorageElement[] copyList, minimizedStorageElement[] deleteList, minimizedStorageElement[] newTargetList) {
        this.copyList = copyList;
        this.deleteList = deleteList;
        this.newTargetList = newTargetList;
    }

    public minimizedStorageElement[] getCopyList() {
        return copyList;
    }

    public minimizedStorageElement[] getDeleteList() {
        return deleteList;
    }

    public minimizedStorageElement[] getNewTargetList() {
        return newTargetList;
    }

    public void setCopyList(List<StorageElement> copyList) {
        StorageElement[] tmpSe = new StorageElement[copyList.size()];
        copyList.toArray(tmpSe);
        this.copyList = Stream.of(tmpSe).map(StorageElement::minify).toArray(minimizedStorageElement[]::new);
    }

    public void setDeleteList(List<StorageElement> deleteList) {
        StorageElement[] tmpSe = new StorageElement[deleteList.size()];
        deleteList.toArray(tmpSe);
        this.deleteList = Stream.of(tmpSe).map(StorageElement::minify).toArray(minimizedStorageElement[]::new);
    }

    public void setNewTargetList(List<StorageElement> newTargetList) {
        StorageElement[] tmpSe = new StorageElement[newTargetList.size()];
        newTargetList.toArray(tmpSe);
        this.newTargetList = Stream.of(tmpSe).map(StorageElement::minify).toArray(minimizedStorageElement[]::new);
    }

    public ProcessingElementList readJSON(String path) {
        return (ProcessingElementList) JSONCreator.read(path, ProcessingElementList.class);
    }
}
