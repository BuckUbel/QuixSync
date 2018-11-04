package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ProcessingElementList extends List{

    public ProcessingElementList(){};

    public ProcessingElementList(StorageElementList copyList, StorageElementList deleteList) {
        this.copyList = copyList;
        this.deleteList = deleteList;
    }

    public StorageElementList copyList;
    public StorageElementList deleteList;

    public String sourceDirPath;
    public String targetDirPath;


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
