package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Stream;

public class ProcessingElementList extends ElementList {

    public minimizedStorageElement[] copyList;
    public minimizedStorageElement[] deleteList;
    public minimizedStorageElement[] newTargetList;

    public String sourceDirPath;
    public String targetDirPath;

    public ProcessingElementList() {
    }

    public ProcessingElementList(minimizedStorageElement[] copyList, minimizedStorageElement[] deleteList, minimizedStorageElement[] newTargetList) {
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
    public void setCopyList(List<StorageElement> copyList){
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

        Gson gson = new GsonBuilder().create();
        ProcessingElementList stlist = new ProcessingElementList();

        try (Reader reader = new FileReader(path)) {
            stlist = gson.fromJson(reader, ProcessingElementList.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stlist;
    }
}
